package com.hello.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); // META-INF 파일을 읽는다
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // ⚡ : JPQL 쿼리 작성
//            String qlString = "select m From Member m where m.name like '%kim%'";
//            List<Member> resultList = em.createQuery(
//                    qlString,
//                    Member.class
//            ).getResultList();

            // ⚡ : criteriaQuery 사용
            // JPA Criteria는 실무에서 사용이 잘 안된다.
//            CriteriaBuilder cb = em.getCriteriaBuilder();
//            CriteriaQuery<Member> query = cb.createQuery(Member.class);
//
//            Root<Member> m = query.from(Member.class);
//            CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("name"), "kim"));
//            List<Member> resultList = em.createQuery(cq).getResultList();

            // ⚡ : Native SQL 사용
            em.createNativeQuery("select MEMBER_ID, city, street, zipcode, USERNAME FROM MEMBER").getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
