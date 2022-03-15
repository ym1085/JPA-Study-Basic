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

            // 01. 스칼라 서브쿼리
            String query1 = "select (select avg(m1.age) from JpqlMember m1) as avgAge from JpqlMember m join m.team t";

            // 02. 인라인 뷰, JPA는 FROM 절에서 사용이 되는 서브 쿼리를 지원하지 않는다.
            // String sql = "select mm.age, mm.username from (select m.age, m.username from Member m) as mm";

            List<JpqlMember> resultList = em.createQuery(query1, JpqlMember.class).getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
