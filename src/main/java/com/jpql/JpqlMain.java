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

            // collection fetch join
            String query = "select t From JpqlTeam t join fetch t.memberList m";

            // 해결 방안 01 : 방향으로 뒤짚어서 조회하고 페이징 처리, 회원에서 팀으로 가도록 방향을 수정
            String query2 = "select m From JpqlMember m join fetch m.team t";

            // 해결 방안 02 : join fetch를 과감하게 제거 한다
            String query3 = "select t From JpqlTeam t";
            List<JpqlTeam> teamList = em.createQuery(query3, JpqlTeam.class)
                                        .setFirstResult(0)
                                        .setMaxResults(2)
                                        .getResultList();

            System.out.println("teamList.size = " + teamList.size());
            for (JpqlTeam team : teamList) {
                System.out.println("team = " + team.getName() + ", " + team.getMemberList()
                                                                           .size());
                for (JpqlMember member : team.getMemberList()) {
                    System.out.println("-> Member = " + member);
                }
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
