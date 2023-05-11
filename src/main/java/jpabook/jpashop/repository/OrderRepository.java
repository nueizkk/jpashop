package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderRepository {
    @PersistenceContext
    private final EntityManager em;
    public OrderRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Order order) {
        em.persist(order);
    }
    public Order findOne(Long orderId) {
        return em.find(Order.class, orderId);
    }
    public List<Order> findAll () {
       return em.createQuery("select o from Order o").getResultList();
    }
//    public List<Order> findAll (OrderSearch orderSearch) {
//        return em.createQuery("select o from Order o join o.member m" +
//                        "where o.status = :status" +
//                        "and m.name like :name", Order.class)
//                .setParameter("status", orderSearch.getOrderStatus())
//                .setParameter("name", orderSearch.getMemberName())
//                .setMaxResults(1000) // 최대 1000건
//                .getResultList();
//    }

}
