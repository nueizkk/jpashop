package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 상품 주문
     */
    public Long order(Long memberId, int count, Long itemId) {
        // 엔티티 조회
        Member member = memberRepository.findOne(memberId).get();
        Item item = itemRepository.findOne(itemId);

        // 배송지 설정
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 인스턴스 생성
        Order order = Order.createOrder(member, delivery,orderItem);

        orderRepository.save(order);
        return order.getId();
    }
    /**
     * 주문 취소
     */
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    public Order findOne(Long orderId) {
        return orderRepository.findOne(orderId);
    }

    /**
     * 주문 내역 전체 조회 & 주문 검색
     */
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    // 상품 검색
}
