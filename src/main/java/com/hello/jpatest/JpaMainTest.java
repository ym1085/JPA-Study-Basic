package com.hello.jpatest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMainTest {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("첫번째 팀1");
            em.persist(team);

            Team teamB = new Team();
            teamB.setName("첫번째 팀1");
            em.persist(teamB);

            MemberTest member1 = new MemberTest();
            member1.setUsername("member");
            member1.setTeam(team);
            em.persist(member1);

            MemberTest member2 = new MemberTest();
            member2.setUsername("member");
            member2.setTeam(teamB);
            em.persist(member2);

            em.flush();
            em.clear();

//            MemberTest m = em.find(MemberTest.class, member.getId()); // 일반 조회
            List<MemberTest> members =
                    em.createQuery("select m from MemberTest m join fetch m.team", MemberTest.class)
                            .getResultList(); // JPQL
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
