package com.example.capstone.item.service;

import com.example.capstone.apiPayload.code.status.ErrorStatus;
import com.example.capstone.aws.s3.AmazonS3Util;
import com.example.capstone.common.QueryService;
import com.example.capstone.exception.GeneralException;
import com.example.capstone.exception.handler.ExceptionHandler;
import com.example.capstone.item.Category;
import com.example.capstone.item.GroupPurchaseItem;
import com.example.capstone.item.Item;
import com.example.capstone.item.ItemImage;
import com.example.capstone.item.converter.ItemConverter;
import com.example.capstone.item.dto.ItemRequestDTO;
import com.example.capstone.item.dto.ItemResponseDTO;
import com.example.capstone.item.repository.CategoryRepository;
import com.example.capstone.item.repository.GroupPurchaseItemRepository;
import com.example.capstone.item.repository.ItemImageRepository;
import com.example.capstone.item.repository.ItemRepository;
import com.example.capstone.member.Member;
import com.example.capstone.member.repository.MemberRepository;
import com.example.capstone.seller.Seller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.example.capstone.apiPayload.code.status.ErrorStatus.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final QueryService queryService;
    private final AmazonS3Util amazonS3Util;
    private final ItemImageRepository itemImageRepository;
    private final GroupPurchaseItemRepository groupPurchaseItemRepository;

    public ItemResponseDTO.ItemList getItemList(Long categoryId, Integer page, Integer size) {
        Category category = categoryValid(categoryId);
        Page<Item> itemPage = itemRepository.findByCategoryId(category.getId(), PageRequest.of(page, size));
        return ItemConverter.toItemList(itemPage);
    }

    private Category categoryValid(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ITEM_CATEGORY_NOT_FOUND));
    }

    public ItemResponseDTO.ItemList getImminentItemList(Integer page, Integer size) {

        LocalDateTime imminentDate = LocalDateTime.now().plusDays(7);
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());

        Page<Item> imminentItemPage = itemRepository.searchImminentItem(imminentDate, pageable);

        return ItemConverter.toItemList(imminentItemPage);
    }

    public ItemResponseDTO.ItemList getPopularItemList(Integer page, Integer size) {

        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<Item> popularItemPage = itemRepository.searchPopularItem(today, today.plusDays(1), pageable);
        return ItemConverter.toItemList(popularItemPage);
    }

    public ItemResponseDTO.ItemList getSubscribedItemList(Long fromMember, Integer page, Integer size) {

        Long memberId = validateMember(fromMember).getId();

        Page<Item> subscribedItemPage = itemRepository.searchSubscribedItem(memberId, PageRequest.of(page, size));

        return ItemConverter.toItemList(subscribedItemPage);
    }

    private Member validateMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ExceptionHandler(MEMBER_NOT_FOUND));
    }

    public ItemResponseDTO.ItemList getAllItemList(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<Item> allItemPage = itemRepository.findAllBy(pageable);

        return ItemConverter.toItemList(allItemPage);
    }

    public ItemResponseDTO.DetailsOfItem getDetailOfItem(Long ItemId) {
        Item item = itemRepository.findItemById(ItemId).orElseThrow(() -> new ExceptionHandler(ErrorStatus.ITEM_NOT_FOUND));

        return ItemConverter.toDetailsOfItemResponseDTO(item);
    }

    @Override
    public ItemResponseDTO.ItemUpload uploadItem(Long memberId, ItemRequestDTO.ItemUpload request,
                                                 List<MultipartFile> itemImages, MultipartFile itemDetailsImage) {
        Seller seller = queryService.isSeller(memberId);
        Category category = queryService.findCategory(request.getCategoryId());
        Item item = ItemConverter.toItemEntity(seller, request, category);

        isGroupPurchase(item, request);
        itemImageUpload(item, itemImages);
        itemDetailsImageUpload(item, itemDetailsImage);

        item = itemRepository.save(item);

        return ItemConverter.toItemUpload(item);
    }

    private ItemImage itemImageBuilder(Item item, MultipartFile file) {
        UUID uuid = UUID.randomUUID();
        String path = amazonS3Util.generateItemImagePath(uuid, file);
        String url = amazonS3Util.uploadFile(path, file);

        ItemImage itemImage = ItemImage.builder()
                .item(item)
                .imageUrl(url)
                .uuid(uuid)
                .originFileName(file.getOriginalFilename())
                .build();

        return itemImageRepository.save(itemImage);
    }
    private void itemDetailsImageUpload(Item item, MultipartFile file) {
        if (file == null || file.getOriginalFilename().equals("")) {
            return;
        }
        ItemImage itemImage = itemImageBuilder(item, file);
        item.addItemDetailsImageUrl(itemImage.getImageUrl());
    }

    private void itemImageUpload(Item item, List<MultipartFile> itemImages) {
        if (itemImages == null) {
            return;
        }

        for (MultipartFile file : itemImages) {
            if(file.getOriginalFilename().equals("")) {
                return;
            }
            ItemImage itemImage = itemImageBuilder(item, file);
            item.addItemImage(itemImage);
        }
    }

    /**
     * item 이 공동구매 상품인지 확인 후, 공동 구매 상품이면 groupPurchaseItem에 기록한다.
     *
     * @param item    상품 entity
     * @param request 상풍 등록 요청 객체
     */
    private void isGroupPurchase(Item item, ItemRequestDTO.ItemUpload request) {
        if (request.getIsGroupPurchase()) {
            if (request.getGroupPurchasePrice() == null || request.getTargetQuantity() == null) {
                throw new GeneralException(ErrorStatus.ITEM_UPLOAD_BAD_REQUEST);
            }

            GroupPurchaseItem groupPurchaseItem = GroupPurchaseItem.builder()
                    .targetQuantity(request.getTargetQuantity())
                    .discountPrice(request.getGroupPurchasePrice())
                    .build();

            groupPurchaseItem = groupPurchaseItemRepository.save(groupPurchaseItem);

            item.addGroupPurchaseItem(groupPurchaseItem);
        }
    }
}
