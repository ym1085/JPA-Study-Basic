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

            System.out.println("===============================================================");

            // basic join
            String query = "select m From JpqlMember m";

            // fetch join => N : 1 -> entity fetch join
            String query1 = "select m From JpqlMember m join fetch m.team";
            List<JpqlMember> memberList = em.createQuery(query1, JpqlMember.class)
                                            .getResultList();

//            회원1, 팀A(SQL)
//            회원2, 팀A(1차캐시: 영속성 컨텐스트)
//            회원3, 팀B(SQL)
            for (JpqlMember member : memberList) {
                System.out.println("member = " + member.getUsername() + ", "
                        + member.getTeam()
                                .getName());
            }

            System.out.println("\n");

            // 1 : N -> collection fetch join
            String query2 = "select t From JpqlTeam t join fetch t.memberList";

            // 1 : N -> collection fetch join 'distinct'
            String query3 = "select distinct t From JpqlTeam t join fetch t.memberList";

            // 1 : N -> 일반 조인 fetch 제거
            String query4 = "select t From JpqlTeam t join t.memberList m";

            List<JpqlTeam> teamList = em.createQuery(query4, JpqlTeam.class)
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
