package com.example.capstone.item.converter;

import com.example.capstone.item.Category;
import com.example.capstone.item.Item;
import com.example.capstone.item.common.ItemType;
import com.example.capstone.item.dto.ItemRequestDTO;
import com.example.capstone.item.dto.ItemResponseDTO;
import com.example.capstone.seller.Seller;
import org.springframework.data.domain.Page;

import java.util.List;

import static com.example.capstone.item.converter.ItemImageConverter.toItemImageList;

public class ItemConverter {

    public static ItemResponseDTO.Item toItemResponseDTO(Item item) {
        return ItemResponseDTO.Item.builder()
                .id(item.getId())
                .name(item.getName())
                .category(item.getCategory().getName())
                .stock(item.getStock())
                .price(item.getPrice())
                .discountPrice(0)   // TODO: 할인 어떻게?
                .imageUrl(item.getItemImages().isEmpty() ? null : item.getItemImages().get(0).getImageUrl())
                .deadline(item.getDeadline())
                .build();
    }

    public static ItemResponseDTO.ItemList toItemList (Page<Item> itemPage) {
        List<ItemResponseDTO.Item> itemList = itemPage.stream()
                .map(ItemConverter::toItemResponseDTO)
                .toList();

        return ItemResponseDTO.ItemList.builder()
                .listSize(itemPage.getSize())
                .page(itemPage.getTotalPages())
                .totalElement(itemPage.getTotalElements())
                .isFirst(itemPage.isFirst())
                .isLast(itemPage.isLast())
                .itemList(itemList)
                .build();
    }

    public static ItemResponseDTO.DetailsOfItem toDetailsOfItemResponseDTO(Item item) {
        return ItemResponseDTO.DetailsOfItem.builder()
                .id(item.getId())
                .memberId(item.getSeller().getMember().getId())
                .name(item.getName())
                .category(item.getCategory().getName())
                .stock(item.getStock())
                .price(item.getPrice())
                .discountPrice(0)   // TODO: 할인 어떻게?
                .ItemDetailsImageUrl(item.getItemDetailsImageUrl())
                .imageUrl(toItemImageList(item.getItemImages()))
                .deadline(item.getDeadline())
                .build();
    }

    public static ItemResponseDTO.DetailsOfItemWithSeller toDetailsOfItemWithSellerResponseDTO(Item item) {
        return ItemResponseDTO.DetailsOfItemWithSeller.builder()
                .id(item.getId())
                .name(item.getName())
                .sellerId(item.getSeller().getId())
                .category(item.getCategory().getName())
                .price(item.getPrice())
                .discountPrice(0)   // TODO: 할인 어떻게?
                .ItemDetailsImageUrl(item.getItemDetailsImageUrl())
                .imageUrl(toItemImageList(item.getItemImages()))
                .deadline(item.getDeadline())
                .build();
    }

    public static Item toItemEntity(Seller seller, ItemRequestDTO.ItemUpload request, Category category) {
        return Item.builder()
                .seller(seller)
                .category(category)
                .type(ItemType.COMMON)
                .name(request.getItemName())
                .simpleExplanation(request.getSimpleExplanation())
                .price(request.getPrice())
                .deliveryCharge(request.getDeliveryPrice())
                .stock(request.getStock())
                .deadline(request.getDeadLine())
                .build();
    }

    public static ItemResponseDTO.ItemUpload toItemUpload(Item item) {
        ItemResponseDTO.DetailsOfItem itemDTO = toDetailsOfItemResponseDTO(item);

        if (item.getGroupPurchaseItem() == null) {
            return ItemResponseDTO.ItemUpload.builder()
                    .item(itemDTO)
                    .groupPurchaseInfo(null)
                    .build();
        }

        ItemResponseDTO.GroupPurchaseInfo groupPurchaseInfo = ItemResponseDTO.GroupPurchaseInfo.builder()
                .targetQuantity(item.getGroupPurchaseItem().getTargetQuantity())
                .discountPrice(item.getGroupPurchaseItem().getDiscountPrice())
                .build();

        return ItemResponseDTO.ItemUpload.builder()
                .item(itemDTO)
                .groupPurchaseInfo(groupPurchaseInfo)
                .build();
    }
}
