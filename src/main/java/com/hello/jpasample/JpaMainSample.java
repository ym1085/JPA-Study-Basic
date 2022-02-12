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
            /* movie 등록 */
            Movie movie = new Movie();
            movie.setDirector("영화감독");
            movie.setActor("이준기");
            movie.setName("바람과 함께 사라지다");
            movie.setPrice(10000);

            em.persist(movie);

            /* 영속성 컨텍스트에 존재하는 데이터 DB 전송 */
            em.flush();

            /* 1차 캐시 비우기 */
            em.clear();

            /* 영화 내역 조회 */
            Movie findMovie = em.find(Movie.class, movie.getId());
            System.out.println("findMovie = " + findMovie);

            /* 구현 클래스마다 테이블 전략 : 다형성을 사용하여 조회 */
            /*ItemSample item = em.find(ItemSample.class, movie.getId());
            System.out.println("item = " + item);*/

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
