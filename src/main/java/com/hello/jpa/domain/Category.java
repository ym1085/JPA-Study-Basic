package com.hello.jpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "CATEGORY_ITEM",
        joinColumns = @JoinColumn(name = "CATEGORY_ID"), // CATEGORY 테이블 입장에서 조인해야하는 컬럼명
            inverseJoinColumns = @JoinColumn(name = "ITEM_ID") // 반대쪽 조인해야하는 컬럼명
    )
    private List<Item> items = new ArrayList<>();
}
