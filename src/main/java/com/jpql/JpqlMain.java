package com.jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

/**
 * JPQL Main Class
 *
 * @author ymkim
 * @since 2022.03.13 Sun 10:33
 */
public class JpqlMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); // META-INF 파일을 읽는다
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            for (int i = 0; i < 100; i++) {
                JpqlMember member = new JpqlMember();
                member.setUsername("member" + i);
                member.setAge(i);
                em.persist(member);
            }
            em.flush();
            em.clear();

            // 01. 페이징 처리
            List<JpqlMember> resultList = em.createQuery("select m From JpqlMember m order by m.age desc", JpqlMember.class)
//                    .setFirstResult(0) // idx
//                    .setFirstResult(0)
//                    .setMaxResults(10)
                    .setFirstResult(35)
                    .setMaxResults(10) // 최대 갯수 지정 35 ~ 45
                    .getResultList();

            System.out.println("memberList.size = " + resultList.size());
            for (JpqlMember result : resultList) {
                System.out.println("result = " + result);
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
