package com.hello.jpasample;

import javax.persistence.*;

@Entity
public class MemberSample {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "name", nullable = false)
    private String username;

    /*@Column(name = "TEAM_ID")
    private Long teamId;*/

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
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

    @Override
    public String toString() {
        return "MemberSample{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", team=" + team +
                '}';
    }

    /*public void changeTeam(Team team) {
        this.team = team;
        team.getMember().add(this); // 양방향 매핑시 연관관계 편의 메서드를 사용하여 양쪽에 값을 다 넣어준다
    }*/
}
