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
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            MemberSample member = new MemberSample();
            member.setUsername("member1");
            member.setTeam(team);
            em.persist(member); // 영속성 1차캐시에 저장, select query 안 날라가는 부분 기억

            em.flush();
            em.clear();

            MemberSample findMember = em.find(MemberSample.class, member.getId());
            List<MemberSample> members = findMember.getTeam().getMember();
            for (MemberSample m : members) {
                System.out.println("m = " + m.getUsername());
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
