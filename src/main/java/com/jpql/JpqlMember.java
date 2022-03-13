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

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private JpqlTeam team;

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
