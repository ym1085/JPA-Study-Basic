package com.hello.jpasample;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team")
    private List<MemberSample> members = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MemberSample> getMember() {
        return members;
    }

    public void setMember(List<MemberSample> member) {
        this.members = member;
    }

    public void addMember(MemberSample member) {
        member.setTeam(this); // 파라미터 인자로 들어온 member에 team을 셋팅
        members.add(member);
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", members=" + members +
                '}';
    }
}
