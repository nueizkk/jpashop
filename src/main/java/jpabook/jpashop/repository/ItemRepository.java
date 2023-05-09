package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
//@RequiredArgsConstructor
public class ItemRepository {
    @PersistenceContext
    private final EntityManager em;

    public ItemRepository(EntityManager em) {
        this.em = em;
    }
    public void save (Item item) {
        if (item.getId() == null) {
            // 완전히 새로생성된 객체일 경우 신규 등록
            em.persist(item);
        } else {
            em.merge(item); // 업데이트 의미
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }

}
