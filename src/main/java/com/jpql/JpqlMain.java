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
            // member.setUsername("teamA");
            // member.setUsername(null); // coalesce 테스트
            // member.setUsername("관리자"); // NULLIF 테스트 => null 반환 해야함
            member.setUsername("졸리다"); // NULLIF 테스트 => null 반환 해야함
            member.setAge(10);
            member.setType(MemberType.USER);

            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            // 01. case 식 사용
            /*String query =
                    "select " +
                            "case when m.age <= 10 then '학생요금' " +
                            "     when m.age >= 60 then '경로요금' " +
                            "     else '일반요금' " +
                            "end " +
                            "from JpqlMember m";*/

            // 02. coalesce :  하나씩 조회해서 null이 아니면 반환
            // String query = "select coalesce(m.username, '이름 없는 회원') from JpqlMember m";

            // 03. NULLIF : 두 값이 같으면 null 반환, 다르면 첫 번째 값 반환
            String query = "select NULLIF(m.username, '관리자') from JpqlMember m";
            List<String> resultList = em.createQuery(query, String.class).getResultList();

            for (String s : resultList) {
                System.out.println("s  = " + s);
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
