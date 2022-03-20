package com.jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Collection;
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
            JpqlTeam team = new JpqlTeam();
            team.setName("경영관리부");
            em.persist(team);

            JpqlMember member1 = new JpqlMember();
            member1.setUsername("유저1");
            member1.setAge(10);
            member1.setType(MemberType.USER);
            member1.setTeam(team);
            em.persist(member1);

            JpqlMember member2 = new JpqlMember();
            member2.setUsername("유저2");
            member2.setAge(20);
            member2.setType(MemberType.ADMIN);
            member2.setTeam(team);
            em.persist(member2);

            em.flush();
            em.clear();

            // 01. 상태 필드 m.username.aa '.' 을 찍어서 경로 탐색이 불가능
            String query = "select m.username From JpqlTeam t join t.memberList m";

            List<JpqlMember> resultList = em.createQuery(query, JpqlMember.class)
                                            .getResultList();

            for (JpqlMember member : resultList) {
                System.out.println("member = " + member);
            }

            System.out.println("========================================================================");

            // 02. 단일 값 연관 관계
            //      - 묵시적 내부 조인(INNER JOIN)이 발생
            //      - 객체 입장에서의 그래프 탐색은 상관이 없지만, DB에서는 JOIN을 통해 값을 가져오게 됨
            String query2 = "select m.team From JpqlMember m";

            List<JpqlTeam> resultList2 = em.createQuery(query2, JpqlTeam.class)
                                           .getResultList();

            for (JpqlTeam team2 : resultList2) {
                System.out.println("team2 = " + team2);
            }

            System.out.println("========================================================================");

            // 03. 컬렉션 값 연관 관계
            //      - 묵시적 내부 조인이 발생
            //      - 탐색은 불가능하게 설계가 되어 있음
            //      - FROM 절에 별칭을 사용하게 되면 탐색 역시 가능함
            //      - select t.memberList.name <- 객체 그래프 탐색이 불가능
            String query3 = "select t.memberList From JpqlTeam t";
            Collection resultList3 = em.createQuery(query3, Collection.class)
                                       .getResultList();

            System.out.println("resultList3 = " + resultList3);

            System.out.println("========================================================================");

            String query4 = "select t.memberList From JpqlTeam t";
            Collection singleResult = em.createQuery(query4, Collection.class)
                                        .getResultList();

            System.out.println("singleResult = " + singleResult);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
