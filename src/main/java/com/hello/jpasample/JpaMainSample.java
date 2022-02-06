package com.hello.jpasample;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMainSample {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            /* MemberSample */
            MemberSample member = new MemberSample();
            member.setUsername("테스트유저2");

            em.persist(member); // member 등록

            /* Product */
            Product product = new Product();
            product.setName("냉장고");

            em.persist(product); // product 등록

            /* MemberProduct */
            MemberProduct memberProduct = new MemberProduct();
            memberProduct.setCount(2);  // 수량
            memberProduct.setPrice(100000);  // 가격
            memberProduct.setMember(member);    // member 셋팅
            memberProduct.setProduct(product);  // product 셋팅

            em.persist(memberProduct);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
