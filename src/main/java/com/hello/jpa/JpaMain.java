package com.hello.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); // META-INF 파일을 읽는다

        // Create and close EntityMangerFactory
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 비영속 상태
            Member member = new Member();
            member.setId(100L);
            member.setName("HelloJPA");

            // 영속 상태, 영속성 컨텍스트 안에 member 객체가 들어가 관리된다
            // BEFORE, AFTER 사이에 쿼리 질의문이 나오지 않는 상황
            System.out.println("==== BEFORE ====");
            em.persist(member);
//            em.detach(member); // 영속 상태 제거
            System.out.println("==== AFTER ====");

            // tx.commit 시 영속성 컨텍스트의 내용이 DB에 날라간다
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
