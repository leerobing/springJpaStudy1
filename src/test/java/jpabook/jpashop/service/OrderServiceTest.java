package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager  em;
    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문 () {
        Member member = new Member();
        member.setName("호빈");
        member.setAddress(new Address("서울시","광진구","123-123"));
        em.persist(member);

        Book book = new Book();
        book.setName("시골 JPA");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);
        int count = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), count);

        Order getOrder = orderRepository.findOne(orderId);
        Assertions.assertEquals(OrderStatus.ORDER,getOrder.getStatus());
        Assertions.assertEquals(10000*count,getOrder.getTotalPrice());
        Assertions.assertEquals(8,book.getStockQuantity());
    }

    @Test
    public void 재고수량초과주문 () {
        Member member = new Member();
        member.setName("호빈");
        member.setAddress(new Address("서울시","광진구","123-123"));
        em.persist(member);

        Book book = new Book();
        book.setName("시골 JPA");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);
        int count = 11;

        Assertions.assertThrows(NotEnoughStockException.class,
                ()->orderService.order(member.getId(),book.getId(),count));
    }

    @Test
    public void 주문취소 () {
        Member member = new Member();
        member.setName("호빈");
        member.setAddress(new Address("서울시","광진구","123-123"));
        em.persist(member);

        Book book = new Book();
        book.setName("시골 JPA");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);
        int count = 5;

        Long orderId = orderService.order(member.getId(), book.getId(), count);
        orderService.cancelOrder(orderId);

        Order order = orderRepository.findOne(orderId);

        Assertions.assertEquals(OrderStatus.CANCEL,order.getStatus());
        Assertions.assertEquals(10,book.getStockQuantity());
    }

}