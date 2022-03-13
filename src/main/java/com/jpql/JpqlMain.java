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
            JpqlTeam team = new JpqlTeam();
            team.setName("teamA");
            em.persist(team);

            JpqlMember member = new JpqlMember();
//            member.setUsername("member1");
            member.setUsername("teamA"); // seta 비교용
            member.setAge(10);

            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            // 01. [inner] join
            String query1 = "select m from JpqlMember m join m.team t";
            List<JpqlMember> resultList1 = em.createQuery(query1, JpqlMember.class)
                    .getResultList();

            // 02. left [outer] join
            String query2 = "select m from JpqlMember m left outer join m.team t";
            List<JpqlMember> resultList2 = em.createQuery(query2, JpqlMember.class)
                    .getResultList();

            // 03. seta join, cross join
            String query3 = "select m from JpqlMember m, JpqlTeam t where m.username = t.name";
            List<JpqlMember> resultList3 = em.createQuery(query3, JpqlMember.class)
                    .getResultList();

            // 04. 조인 대상 필터링
            String query4 = "select m from JpqlMember m left join m.team t on t.name = 'teamA'";
            List<JpqlMember> resultList4 = em.createQuery(query4, JpqlMember.class)
                    .getResultList();

            // 05. 연관관계가 없는 엔티티 외부 조인
            String query5 = "select m from JpqlMember m left join Team t on m.username = t.name"; // 막 조인
            List<JpqlMember> resultList5 = em.createQuery(query5, JpqlMember.class)
                    .getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
