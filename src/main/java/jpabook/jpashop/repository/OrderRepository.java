package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
//@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    @Autowired
    public OrderRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Order order) {
        em.persist(order);
    }
    public Order findOne(Long orderId){
       return em.find(Order.class, orderId);
    }
//    public List<Order> findAll(OrderSearch orderSearch) {
//        return em.createQuery("select o from Order o where o.status = :orderSearch", Order.class)
//                .setParameter("orderSearch", orderSearch)
//                .getResultList();
//    }
}
