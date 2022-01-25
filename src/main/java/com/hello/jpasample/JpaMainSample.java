package com.hello.jpasample;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMainSample {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            MemberSample member = new MemberSample();
            member.setUsername("member1");
            member.setTeam(team);
            em.persist(member); // 영속성 1차캐시에 저장, select query 안 날라가는 부분 기억

            MemberSample memberSample = em.find(MemberSample.class, member.getId());

            Team findTeam = memberSample.getTeam();
            System.out.println("team ==> id ==> " + findTeam.getId());

            Team team1 = em.find(Team.class, 100L); // id가 100번인 Team이 존재 한다는 것을 가정하고 진행
            memberSample.setTeam(team1); // 팀 번호가 변경이 된다

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
