package com.hello.jpatest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMainTest {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Address address = new Address("서울", "강남구", "14554"); // Address object is embedded type

            MemberTest member = new MemberTest();
            member.setUsername("테스트유저1");
            member.setHomeAddress(address);
            em.persist(member);

            // 임베디드 타입인 address 참조 변수를 인수로 등록, 별로 문제가 안된다
            MemberTest member2 = new MemberTest();
            member2.setUsername("김정남");
            member2.setHomeAddress(address);
            em.persist(member2);

            // member가 가진 Address 객체만 바꾸려는 의도는 알겠다만, 이 부분이 문제가 됨
            // Address를 불변객체로 만들어서 사용해야 한다.
            //      1. Setter를 없앤다.
            //      2. Setter를 private로 선언한다.
            // member.getHomeAddress().setCity("새로운 서울");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }
}
