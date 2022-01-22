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
            Member member = em.find(Member.class, 150L);
            member.setName("AAAAA");

//            em.detach(member); // 준 영속 상태 (JPA에서 관리가 되지 않는다)
            em.clear(); // 영속성 컨텍스트 (1차 캐시)를 전부 제거한다
//            em.close(); // 영속성 컨텍스트를 닫는다, 데이터 변경사항이 적용되지 않는다

            // 영속성 컨텍스트가 전부 제거되면, 1차 캐시에 값이 없기 때문에 쿼리가 두번 나간다
            Member member2 = em.find(Member.class, 150L);

            System.out.println("=========================");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
