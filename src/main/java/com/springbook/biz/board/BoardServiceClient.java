package com.springbook.biz.board;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class BoardServiceClient {
	public static void main(String[] args) {
		// EntityManager 생성
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAProject");
		EntityManager em = emf.createEntityManager();
		// Transaction 생성
		EntityTransaction tx = em.getTransaction();
		try {
			// Transaction 시작
			tx.begin();
			
			Board board = new Board();
			board.setTitle("JPA 1");
			board.setWriter("2");				// 한글로 입력하면 hibernate 오류 발생(해결방법 찾기) &amp;가 문제인가..
			board.setContent("JPA 3.");
			
			// 글 등록
			em.persist(board);	// INSERT 엔티티를 영속화한다 
			
			
			// 글 목록 조회
			String jpql = "select b from Board b order by b.seq desc"; 
			List<Board> boardList = em.createQuery(jpql, Board.class).getResultList();
			for(Board brd : boardList) {
				System.out.println("---> " + brd.toString());
			}
			
			// Transaction commit
			tx.commit();
		} catch(Exception e) {
			e.printStackTrace();
			// Transaction rollback
			tx.rollback();
		} finally {
			em.close();
		}
		emf.close();
	}
}
