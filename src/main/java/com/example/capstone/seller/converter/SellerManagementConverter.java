package com.example.capstone.seller.converter;

import com.example.capstone.item.Item;
import com.example.capstone.order.OrderItem;
import com.example.capstone.seller.dto.SellerResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class SellerManagementConverter {

    public static SellerResponseDTO.OrderItemStatus toOrderStatus(OrderItem orderItem) {
        Item item = orderItem.getItem();

        return SellerResponseDTO.OrderItemStatus.builder()
                .orderNumber(orderItem.getId())
                .itemName(item.getName())
                .itemPreviewImageUrl(null)
                .orderedDate(orderItem.getCreatedAt())
                .consumerName(orderItem.getOrder().getMember().getName())
                .price(item.getPrice())
                .quantity(orderItem.getQuantity())
                .status(orderItem.getStatus())
                .build();
    }

    public static SellerResponseDTO.OrderStatusList toOrderStatusList(Page<OrderItem> orderItemPage) {
        List<SellerResponseDTO.OrderItemStatus> orderItemStatusList = orderItemPage.stream()
                .map(SellerManagementConverter::toOrderStatus)
                .collect(Collectors.toList());

        return SellerResponseDTO.OrderStatusList.builder()
                .listSize(orderItemPage.getSize())
                .page(orderItemPage.getTotalPages())
                .totalElement(orderItemPage.getTotalElements())
                .isFirst(orderItemPage.isFirst())
                .isLast(orderItemPage.isLast())
                .orderItemStatusList(orderItemStatusList)
                .build();
    }

    public static SellerResponseDTO.SalesItem toSalesItem(Item item) {
        return SellerResponseDTO.SalesItem.builder()
                .itemName(item.getName())
                .itemType(item.getType())
                .itemPreviewImageUrl(null)      // TODO: 어떤 이미지를 보여줘야할지
                .categoryName(item.getCategory().getName())
                .price(item.getPrice())
                .stock(item.getStock())
                .deadLine(item.getDeadline())
                .build();
    }

    public static SellerResponseDTO.SalesItemList toSalesItemList(Page<Item> ItemPage) {
        List<SellerResponseDTO.SalesItem> salesItemsList = ItemPage.getContent().stream()
                .map(SellerManagementConverter::toSalesItem)
                .collect(Collectors.toList());

        return SellerResponseDTO.SalesItemList.builder()
                .listSize(ItemPage.getSize())
                .page(ItemPage.getTotalPages())
                .totalElement(ItemPage.getTotalElements())
                .isFirst(ItemPage.isFirst())
                .isLast(ItemPage.isLast())
                .orderItemStatusList(salesItemsList)
                .build();
    }

    public static SellerResponseDTO.Dashboard toDashboard(Integer today, Integer dayBefore, Integer month, Integer lastMonth) {
        return SellerResponseDTO.Dashboard
                .builder()
                .todaySalesVolume(today)
                .todaySalesVolumePercent(calcPercent(today, dayBefore))
                .dayBeforeSalesVolume(dayBefore)
                .monthSalesVolume(month)
                .monthSalesVolumePercent(calcPercent(month, lastMonth))
                .orderStatusNumber(2)   //TODO: 구체화 필요 이슈 51
                .build();
    }

    private static String calcPercent(Integer now, Integer before) {
        double p = 0;

        if (now == 0 && before == 0) {
            p = 0;
        } else if (before == 0) {
            p = 100;
        } else {
            p = (double) ((now - before) / before) * 100;
        }

        return p + "%";
    }
}
