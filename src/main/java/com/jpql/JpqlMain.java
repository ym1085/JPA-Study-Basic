package com.jpql;

import javax.persistence.*;
import java.util.List;

/**
 * JPQL Main Class
 *
 * @author ymkim
 * @since 2022.03.10 Thurs 23:42
 */
public class JpqlMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); // META-INF 파일을 읽는다
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            JpqlMember member = new JpqlMember();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            // ⚡ : commit or flush or query 발생하면 db에 날라가는 부분 깜빡했음
            TypedQuery<JpqlMember> query = em.createQuery("select m from JpqlMember m", JpqlMember.class);

//            TypedQuery<JpqlMember> query2 = em.createQuery("select m.username from JpqlMember m", String.class);
//            TypedQuery<JpqlMember> query3 = em.createQuery("select m.username, m.age from JpqlMember m");

            List<JpqlMember> memberList = query.getResultList();
            for (JpqlMember jpqlMember : memberList) {
                System.out.println("jpqlMember ==> " + jpqlMember.toString());
                System.out.println("jpqlMember ====> " + jpqlMember.getUsername());
                System.out.println("jpqlMember ====> " + jpqlMember.getAge());
            }

            // ⚡ : getSingleResult()는 반드시 값이 한 개인 경우에만 사용을 해야 한다.
            // 위치 기반은 사용하지 말자, 이름 기준으로 사용하자.
            TypedQuery<JpqlMember> query2 = em.createQuery("select m from JpqlMember m where m.username = :username", JpqlMember.class)
                    .setParameter("username", "member1");
            JpqlMember result = query2.getSingleResult();

            // Spring Data JPA -> return Optional or Null
            System.out.println("result ==> " + result.getUsername());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
