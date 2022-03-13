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
            JpqlMember member = new JpqlMember();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            // 01. 엔티티 프로젝션을 사용하면 select 구문에 나오는 모든 엔티티가 영속성 컨텍스트에서 모두 관리된다.
            /*List<JpqlMember> result = em.createQuery("select m From JpqlMember m", JpqlMember.class)
                    .getResultList();*/

            // 02. join을 사용하는 경우
            /*em.createQuery("select t From Member m join m.team t", Member.class);*/

            // 03. 임베디드 타입 프로젝션, 값 타입 지정
            /*em.createQuery("select o.address From JpqlOrder o", JpqlAddress.class)
                    .getResultList();*/

            // 04. 스칼라 타입 프로젝션
            /*em.createQuery("select distinct m.username, m.age From JpqlMember m")
                    .getResultList();*/

            // 05. m.username, m.age인데 타입은 어떤걸로 지정해야 하지??
            // 05-1. Query, Object [], new로 조회
            /*List<Object[]> resultList = em.createQuery("select distinct m.username, m.age From JpqlMember m")
                    .getResultList();

            Object[] result = resultList.get(0);
            System.out.println("result = " + result[0]);
            System.out.println("result = " + result[1]);*/

            // 06. DTO로 받음, qlString이 문자기 때문에 패키지명을 모두 적어줘야 한다.
            List<JpqlMemberDTO> result = em.createQuery("select new com.jpql.JpqlMemberDTO(m.username, m.age) From JpqlMember m", JpqlMemberDTO.class)
                    .getResultList();

            JpqlMemberDTO memberDTO = result.get(0);
            System.out.println("memberDTO = " + memberDTO.getUsername());
            System.out.println("memberDTO = " + memberDTO.getAge());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
