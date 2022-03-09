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
            MemberTest member = new MemberTest();
            member.setUsername("youngminkim");
            member.setHomeAddress(new Address("homeCity", "street1", "10000")); // embedded type

            // Set<String> favoriteFoods
            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("피자");
            member.getFavoriteFoods().add("햄버거");

            member.getAddressHistory().add(new AddressEntity("old1", "street1", "14565"));
            member.getAddressHistory().add(new AddressEntity("old2", "street1", "14566"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("=======================START=====================");
            MemberTest findMember = em.find(MemberTest.class, member.getId());

            // MemberTest가 가지고 있는 homeAddress 객체의 city 값을 변경한다.
            // 아래 소소는 불가능하다
            // findMember.getHomeAddress().setCity("newCity");

            // Address oldAddress = findMember.getHomeAddress();
            // findMember.setHomeAddress(new Address("newCity", oldAddress.getStreet(), oldAddress.getZipcode())); // ⚡ 통으로 갈아 끼워야 한다.

            // Collection 내에 존재하는 '치킨'을 '한식'으로 변경한다.
            // findMember.getFavoriteFoods().remove("치킨");
            // findMember.getFavoriteFoods().add("한식");

            // 주소 'old1'을 삭제 후 변경 한다.
            // findMember.getAddressHistory().remove(new Address("old1", "street1", "14565"));
            // findMember.getAddressHistory().add(new Address("newCity1", "street1", "14565"));

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
