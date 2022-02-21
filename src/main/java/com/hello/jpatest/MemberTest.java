package com.hello.jpatest;

import javax.persistence.*;

@Entity
public class MemberTest extends BaseEntityTest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    // @ManyToOne(fetch = FetchType.EAGER)
    @ManyToOne(fetch = FetchType.LAZY) // 실무에서는 즉시 로딩 사용 지양, 지연 로딩 사용
    @JoinColumn
    private Team team;

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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
