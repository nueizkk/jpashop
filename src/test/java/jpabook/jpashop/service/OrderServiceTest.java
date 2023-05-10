package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;

    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember();

        Item book = createBook(10, "시골 jpa", 10000);

        int orderCount = 1;
        //when
        Long orderId = orderService.order(member.getId(), orderCount, book.getId());

        //then
        Order order = orderService.findOne(orderId);
        assertThat(orderId).isEqualTo(order.getId());
        assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(1).isEqualTo(order.getOrderItems().size()); // 주문한 상품의 종류 수
        assertThat(order.getTotalPrice()).isEqualTo(orderCount * 10000);// 주문 가격
        assertThat(book.getStockQuantity()).isEqualTo(9); // 주문수량 만큼 재고가 줄어야한다.
    }



    @Test
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = createMember();
        Item item = createBook(2, "책이름", 20000);

        //when
        int orderCount = 3;

        //then
        assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), orderCount, item.getId()));
    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember();
        Item book = createBook(10,"책이다",20000);

        Long orderId = orderService.order(member.getId(), 2, book.getId());
        Order order = orderService.findOne(orderId);

        //when
        orderService.cancelOrder(orderId);
        //then
        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCEL);
    }

    private Item createBook(int stockQuantity, String name, int price) {
        Item book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }
    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "자동차시장길","123-456"));
        em.persist(member);
        return member;
    }
}