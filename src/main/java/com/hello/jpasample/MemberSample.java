package com.hello.jpasample;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MemberSample {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)
    private Team team;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID") // 연관관계의 주인 (FK)
    private Locker locker;

//    @ManyToMany
//    @JoinTable(name = "MEMBERSAMPLE_PRODUCT")
//    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProducts = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
