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
            JpqlMember member1 = new JpqlMember();
            member1.setUsername("유저1");
            member1.setAge(10);
            member1.setType(MemberType.USER);
            em.persist(member1);

            JpqlMember member2 = new JpqlMember();
            member2.setUsername("유저2");
            member2.setAge(20);
            member2.setType(MemberType.ADMIN);
            em.persist(member2);

            em.flush();
            em.clear();

            // 01. concat -> 문자열 붙히기
            String query = "select concat('a', 'b') from JpqlMember m";
            String query2 = "select 'a' || 'b' from JpqlMember m";

            // 02. substring -> 문자열 위치 기반 자르기
            String query3 = "select substring(m.username, 2, 3) from JpqlMember m";

            // 03. trim -> 공백 제거
            String query4 = "select trim(m.username) from JpqlMember m";

            // 04. locate -> indexOf랑 비슷한 듯
            String query5 = "select locate('de', 'abcdefg') from JpqlMember m";

            // 05. size -> collection의 크기를 반환 한다.
            String query6 = "select size(t.memberList) from JpqlTeam t";

            // 06. index -> 사용 안하는게 좋음, 값 타입 컬렉션의 위치값을 반환 할 때 사용
            String query7 = "select index(t.memberList) from JpqlTeam t";

            // 07. 사용자 정의 함수 등록
            String query8 = "select function('group_concat', m.username) from JpqlMember m";
            List<String> resultList = em.createQuery(query8, String.class).getResultList();

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
