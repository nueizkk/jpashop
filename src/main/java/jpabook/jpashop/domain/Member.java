package jpabook.jpashop.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") // 맵핑 되어짐
    private List<Order> orders = new ArrayList<>();
    // opt + enter => create class
    /**
     * 컬렉션만 초기화 해주는 이유
     * 1. NPE (Null pointer Exception) 방지
     * 2. 하이버네이트가 엔티티를 영속화 할 때 내부에서 컬렉션이 있으면 하이버네이트가 특별하게 조작한 컬렉션으로 변경합니다.(컬렉션의 데이터가 추가 되었는지 등등을 인식할 수 있어야 하니까요) 따라서 실제 참조가 변경될 수 있습니다.
     * 그런데 개발자가 임의로 나중에 new ArrayList로 초기화를 하게 되면 이 부분이 하이버네이트가 관리하는 컬렉션에서 개발자가 직접 만든 컬렉션으로 변경될 수 있습니다. 그러면 하이버네이트가 정상 동작하지 않습니다.
     * 이런 문제를 방지하기 위해 필드에서 빠르게 컬렉션을 초기화 하고, 해당 컬렉션을 바꾸는 행위를 막도록 코드를 작성하는 것이 좋습니다.
     * */
}

