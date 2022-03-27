package com.jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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
            JpqlTeam teamA = new JpqlTeam();
            teamA.setName("팀A");
            em.persist(teamA);

            JpqlTeam teamB = new JpqlTeam();
            teamB.setName("팀B");
            em.persist(teamB);

            JpqlMember member1 = new JpqlMember();
            member1.setUsername("회원1");
            member1.setAge(0);
            member1.setTeam(teamA);
            em.persist(member1);

            JpqlMember member2 = new JpqlMember();
            member2.setUsername("회원2");
            member1.setAge(0);
            member2.setTeam(teamA);
            em.persist(member2);

            JpqlMember member3 = new JpqlMember();
            member3.setUsername("회원3");
            member1.setAge(0);
            member3.setTeam(teamB);
            em.persist(member3);

            // FLUSH 자동 호출 commit, query, flush
            int resultCount = em.createQuery("update JpqlMember m set m.age = 20")
                                .executeUpdate();
            em.clear();

            JpqlMember jpqlMember = em.find(JpqlMember.class, member1.getId());
            System.out.println("jpqlMember = " + jpqlMember.getAge());

            System.out.println("resultCount = " + resultCount);

            System.out.println("member1.getAge() = " + member1.getAge());
            System.out.println("member1.getAge() = " + member2.getAge());
            System.out.println("member1.getAge() = " + member3.getAge());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
