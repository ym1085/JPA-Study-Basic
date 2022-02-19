package com.hello.jpatest;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMainTest {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            MemberTest member1 = new MemberTest();
            member1.setUsername("member1");
            em.persist(member1);

            em.flush();
            em.clear();

            MemberTest refMember = em.getReference(MemberTest.class, member1.getId());
            System.out.println("refMember = " + refMember.getClass());
//            refMember.getUsername();
            Hibernate.initialize(refMember);

            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(refMember)); // false 나와야함

            // 일반적인 조회
            /*MemberTest findMember = em.find(MemberTest.class, member.getId());
            System.out.println("findMember = " + findMember.getId());
            System.out.println("findMember = " + findMember.getUsername());*/

            /*System.out.println("================================================");
            System.out.println("member.getUserName = " + member.getId());
            System.out.println("member.getUserName = " + member.getUsername());
            System.out.println("================================================");*/

            // 프록시를 통한 조회
            /*MemberTest findMember = em.getReference(MemberTest.class, member.getId());
            System.out.println("findMember : getClass() = " + findMember.getClass());
            System.out.println("findMember : getId() = " + findMember.getId());
            System.out.println("findMember = " + findMember.getUsername());*/

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

    /**
     * 회원명만 가져오는 메서드
     *
     * @param member
     */
    private static void printMember(MemberTest member) {
        System.out.println("member = " + member.getUsername());
    }

    /**
     * 팀명과 회원 정보를 가져오는 메서드
     *
     * @param member
     */
    private static void printMemberAndTeam(MemberTest member) {
        String username = member.getUsername();
        System.out.println("username = " + username);

        Team team = member.getTeam();
        System.out.println("team = " + team.getName());
    }
}
