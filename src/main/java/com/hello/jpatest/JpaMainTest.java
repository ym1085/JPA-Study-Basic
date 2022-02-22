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
            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent); // cascade -> 한 방에 날라갈거고
            em.persist(child1);
            em.persist(child2);

            em.flush(); // db에 날리고
            em.clear(); // 영속성 비우고 -> 안 비우면 영속성 컨텍스트 안에서 가져온다

            Parent findParent = em.find(Parent.class, parent.getId());
            em.remove(findParent);

//            findParent.getChildList().remove(0); // call delete query

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
