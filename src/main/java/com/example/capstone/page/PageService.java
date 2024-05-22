package com.example.capstone.page;


import com.example.capstone.item.dto.ItemResponseDTO;
import com.example.capstone.item.service.ItemService;
import com.example.capstone.member.repository.AlarmRepository;
import com.example.capstone.order.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PageService {
    private final static Integer PAGE = 0;
    private final static Integer SIZE = 6;

    private final AlarmRepository alarmRepository;
    private final CartRepository cartRepository;
    private final ItemService itemServiceImpl;

    // TODO: 서비스에 의존성을 가져서 리팩토링 해야할 수도
    public PageResponseDTO.Main getMainPage(Long memberId) {
        Integer numberUnreadAlarms = alarmRepository.countAllNotConfirmedAlarm(memberId);
        Integer numberItems = cartRepository.countAllItemInCart(memberId);

        ItemResponseDTO.ItemList imminentItemList = itemServiceImpl.getImminentItemList(PAGE, SIZE);
        ItemResponseDTO.ItemList popularItemList = itemServiceImpl.getPopularItemList(PAGE, SIZE);
        ItemResponseDTO.ItemList subscribedItemList = itemServiceImpl.getSubscribedItemList(memberId, PAGE, SIZE);

        return PageResponseDTO.Main.builder()
                .numberUnreadAlarms(numberUnreadAlarms)
                .numberItemsInCart(numberItems)
                .imminentItemListPreview(imminentItemList.getItemList())
                .popularItemListPreview(popularItemList.getItemList())
                .subscribedItemListPreview(subscribedItemList.getItemList())
                .build();
    }
}
