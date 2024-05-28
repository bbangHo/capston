package com.example.capstone.seller.service;

import com.example.capstone.common.QueryService;
import com.example.capstone.item.Item;
import com.example.capstone.item.repository.ItemRepository;
import com.example.capstone.order.OrderItem;
import com.example.capstone.order.common.DateType;
import com.example.capstone.order.dto.MonthlySalesVolumeDTO;
import com.example.capstone.order.repository.OrderItemRepository;
import com.example.capstone.seller.converter.SellerManagementConverter;
import com.example.capstone.seller.dto.SellerResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SellerManagementServiceImpl implements SellerManagementService {
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;
    private final QueryService queryService;

    @Override
    public SellerResponseDTO.OrderStatusList getSellerOrderItemStatus(Long memberId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Long sellerId = queryService.isSeller(memberId).getId();
        Page<OrderItem> sellerOrderItemStatusPage = orderItemRepository.getSellerOrderItemStatus(sellerId, pageable);
        return SellerManagementConverter.toOrderStatusList(sellerOrderItemStatusPage);
    }

    @Override
    public SellerResponseDTO.SalesItemList getSalesItems(Long memberId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Long sellerId = queryService.isSeller(memberId).getId();
        Page<Item> itemPage = itemRepository.findBySellerId(sellerId, pageable);
        return SellerManagementConverter.toSalesItemList(itemPage);
    }

    @Override
    public SellerResponseDTO.Dashboard getDashBoard(Long memberId) {
        Long sellerId = queryService.isSeller(memberId).getId();
        Integer today = getSalesVolume(sellerId, DateType.DAY);
        Integer dayBefore = getSalesVolume(sellerId, DateType.DAY_BEFORE);
        Integer month = getSalesVolume(sellerId, DateType.MONTH);
        Integer lastMonth = getSalesVolume(sellerId, DateType.LAST_MONTH);
        Long orderStatusNumber = orderItemRepository.getOrderStatusNumber(sellerId);
        List<MonthlySalesVolumeDTO> monthlySalesVolumeDTOList = orderItemRepository.getMonthlySalesVolume1Year(sellerId);

        return SellerManagementConverter.toDashboard(today, dayBefore, month , lastMonth, orderStatusNumber, monthlySalesVolumeDTOList);
    }

    private Integer getSalesVolume(Long sellerId, DateType dateType) {
        return orderItemRepository.getSalesVolume(sellerId, dateType).stream()
                .mapToInt(i -> i)
                .sum();
    }
}
