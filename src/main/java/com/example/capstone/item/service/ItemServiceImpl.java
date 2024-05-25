package com.example.capstone.item.service;

import com.example.capstone.apiPayload.code.status.ErrorStatus;
import com.example.capstone.exception.GeneralException;
import com.example.capstone.exception.handler.ExceptionHandler;
import com.example.capstone.item.Category;
import com.example.capstone.item.Item;
import com.example.capstone.item.converter.ItemConverter;
import com.example.capstone.item.dto.ItemResponseDTO;
import com.example.capstone.item.repository.CategoryRepository;
import com.example.capstone.item.repository.ItemRepository;
import com.example.capstone.member.Member;
import com.example.capstone.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.example.capstone.apiPayload.code.status.ErrorStatus.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

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
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());

        Page<Item> popularItemPage = itemRepository.searchPopularItem(today, today.plusDays(1), pageable);
        return ItemConverter.toItemList(popularItemPage);
    }

    public ItemResponseDTO.ItemList getSubscribedItemList(Long fromMember,Integer page, Integer size) {

        Long memberId = validateMember(fromMember).getId();

        Page<Item> subscribedItemPage = itemRepository.searchSubscribedItem(memberId, PageRequest.of(page,size));

        return ItemConverter.toItemList(subscribedItemPage);
    }

    private Member validateMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ExceptionHandler(MEMBER_NOT_FOUND));
    }

    public ItemResponseDTO.ItemList getAllItemList(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page,size,Sort.by("createdAt").descending());

        Page<Item> allItemPage = itemRepository.findAllBy(pageable);

        return ItemConverter.toItemList(allItemPage);
    }

   public ItemResponseDTO.DetailsOfItem getDetailOfItem(Long ItemId){
        Item item = itemRepository.findItemById(ItemId).orElseThrow(() -> new ExceptionHandler(ErrorStatus.ITEM_NOT_FOUND));

        return ItemConverter.toDetailsOfItemResponseDTO(item);
    }

}
