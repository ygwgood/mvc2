package test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import login.dao.MemberDAO;
import login.vo.MemberVO;

public class MemberDAOTest {

	static MemberDAO dao;
			
	public MemberDAOTest() {
		dao=new MemberDAO();
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//dao=new MemberDAO();
	}
	
	//@Test
	public void insertTest() {
		assertEquals
		(1, dao.insert(new MemberVO("aaaa","11111")));
	}

	//@Test
	public void deleteTest() {
		assertEquals(1,dao.delete("aaaa"));
	}
	//@Test
	public void existTest() {
		assertTrue(dao.exist("admin"));
	}
	
	@Test
		public void findOneTest() {
			assertNotNull(dao.findOne("admin"));
		}
}
