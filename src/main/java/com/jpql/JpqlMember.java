package com.jpql;

import javax.persistence.*;

@Entity
@Table(name = "JPQL_MEMBER")
public class JpqlMember {

    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private JpqlTeam team;

    @Enumerated(EnumType.STRING)
    private MemberType type; // ADMIN, USER

    // 연관관계 편의 메서드
    public void changeTeam(JpqlTeam team) {
        this.team = team;
        team.getMemberList().add(this);
    }

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public JpqlTeam getTeam() {
        return team;
    }

    public void setTeam(JpqlTeam team) {
        this.team = team;
    }

    public MemberType getType() {
        return type;
    }

    public void setType(MemberType type) {
        this.type = type;
    }

    // toString 생성 시 양쪽에서 생성 안되도록 주의 하여야 한다.
    @Override
    public String toString() {
        return "JpqlMember{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
