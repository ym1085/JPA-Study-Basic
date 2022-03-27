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
            JpqlTeam teamA = new JpqlTeam();
            teamA.setName("팀A");
            em.persist(teamA);

            JpqlTeam teamB = new JpqlTeam();
            teamB.setName("팀B");
            em.persist(teamB);

            JpqlMember member1 = new JpqlMember();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            JpqlMember member2 = new JpqlMember();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            em.persist(member2);

            JpqlMember member3 = new JpqlMember();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            // Named query 실습
            List<JpqlMember> memberList = em.createNamedQuery("JpqlMember.findByUserName", JpqlMember.class)
                                            .setParameter("username", member1.getUsername())
                                            .getResultList();

            for (JpqlMember member : memberList) {
                System.out.println("member = " + member);
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
