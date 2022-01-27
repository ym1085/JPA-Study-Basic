package com.hello.jpasample;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMainSample {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // Team 객체 생성 후 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);                    // 1차 캐시에 team: name => TeamA

            // Member 객체 생성 후 저장
            MemberSample member = new MemberSample();
            member.setUsername("member1");
//            member.changeTeam(team);           // 연관관계의 주인이 Member.team 이기에 team을 셋팅해준다
            em.persist(member);                  // 1차 캐시에 member: username => member1

            team.addMember(member);

//            team.getMember().add(member);    // 양방향 매핑시 반드시 양쪽에(Entity) 값을 셋팅 해준다

//            em.flush();
//            em.clear();

            System.out.println("team.getId => " + team.getId());
            Team findTeam = em.find(Team.class, team.getId()); //** 1차 캐시, 아까 값이 나오지 않았던 이유는 Team 순수 객체에서만 값을 가져오기 때문이다
            List<MemberSample> members = findTeam.getMember();

            System.out.println("============");
            for (MemberSample m : members) {
                System.out.println("m = " + findTeam);
            }
            System.out.println("============");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
