package login.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import login.vo.MemberVO;

public class MemberDAO implements MemberDAOInter{
Connection conn;
PreparedStatement pstmt;

public MemberDAO(){
	try {
	Class.forName("oracle.jdbc.driver.OracleDriver");
	conn=DriverManager.getConnection(
			"jdbc:oracle:thin:@localhost:1521:xe",
			"test", "1111");
	}catch(Exception e) {
		System.out.println("데이터베이스 접속관련 오류");
		e.printStackTrace();
	}
}

public int insert(MemberVO member){
	String sql=null;
	int result=0;
	
	try {
	sql="insert into member values(member_idx_seq.nextval,?,?)";
	pstmt=conn.prepareStatement(sql);
	pstmt.setString(1,member.getId());
	pstmt.setString(2,member.getPassword());
	result=pstmt.executeUpdate();
	}catch(Exception e) {
		System.out.println("데이터베이스 입력 오류");
		e.printStackTrace();
	}
	return result;
	
}

public List<MemberVO> findAll(){
	String sql=null;
	ResultSet rs=null;
	List<MemberVO> list=null;
	
	try {
	sql="select * from member";
	pstmt=conn.prepareStatement(sql);
	rs=pstmt.executeQuery(sql);
	
	list=new ArrayList();
	while(rs.next()) {
		MemberVO m=new MemberVO();
		m.setIdx(rs.getInt("idx"));
		m.setId(rs.getString("id"));
		m.setPassword(rs.getString("password"));
		list.add(m);	
	}
	}catch(Exception e) {
		System.out.println("입력데이터베이스 오류");
		e.printStackTrace();
	}
	return list;
}

public MemberVO findOne(int idx){
	String sql=null;
	ResultSet rs=null;
	MemberVO m=null;
	try {
	sql="select * from member where idx=?";
	pstmt=conn.prepareStatement(sql);
	pstmt.setInt(1,idx);
	rs=pstmt.executeQuery();
	
	if(rs.next()) {
	m=new MemberVO
	(rs.getInt("idx"),rs.getString("id"),rs.getString("password"));	
	}
	
	}catch(Exception e) {
		System.out.println("findOne오류");
		e.printStackTrace();
	}
	return m;
}

public int update(MemberVO member){
	String sql=null;
	int result=0;
	try {
	sql="update member set password=? where idx=?";
	pstmt=conn.prepareStatement(sql);
	pstmt.setString(1,member.getPassword());
	pstmt.setInt(2,member.getIdx());
	result=pstmt.executeUpdate();
	}catch(Exception e) {
		System.out.println("업데이트 데이터베이스 오류");
		e.printStackTrace();
	}
	return result;
}

public int delete(int idx){
		String sql=null;
		int result=0;
		try {
		sql="delete from member where idx=?";
		pstmt=conn.prepareStatement(sql);
		pstmt.setInt(1, idx);
		result=pstmt.executeUpdate();
		}catch(Exception e) {
			System.out.println("데이터베이스 삭제 오류");
			e.printStackTrace();
		}
		return result;
	
}

public boolean exist(int idx){
	if(findOne(idx)!=null) {
		return true;
	}else {
		return false;
	}
}

public MemberVO findOne(String id){
	String sql=null;
	ResultSet rs=null;
	MemberVO m=null;
	try {
	sql="select * from member where id=?";
	pstmt=conn.prepareStatement(sql);
	pstmt.setString(1,id);
	rs=pstmt.executeQuery();
	
	if(rs.next()) {
	m=new MemberVO
	(rs.getInt("idx"),rs.getString("id"),rs.getString("password"));	
	}
	
	}catch(Exception e) {
		System.out.println("findOne오류");
		e.printStackTrace();
	}
	return m;
}

public int updateById(MemberVO member){
	String sql=null;
	int result=0;
	try {
	sql="update member set password=? where id=?";
	pstmt=conn.prepareStatement(sql);
	pstmt.setString(1,member.getPassword());
	pstmt.setInt(2,member.getIdx());
	result=pstmt.executeUpdate();
	}catch(Exception e) {
		System.out.println("업데이트 데이터베이스 오류");
		e.printStackTrace();
	}
	return result;
}

public int delete(String id){
		String sql=null;
		int result=0;
		try {
		sql="delete from member where id=?";
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, id);
		result=pstmt.executeUpdate();
		}catch(Exception e) {
			System.out.println("데이터베이스 삭제 오류");
			e.printStackTrace();
		}
		return result;
	
}

public boolean exist(String id){
	if(findOne(id)!=null) {
		return true;
	}else {
		return false;
	}
}

@Override
public boolean login(MemberVO member) {
	String sql=null;
	ResultSet rs=null;
	try {
	sql="select * from member where id=? and password=?";
	pstmt=conn.prepareStatement(sql);
	pstmt.setString(1,member.getId());
	pstmt.setString(2,member.getPassword());
	rs=pstmt.executeQuery();
	
	if(rs.next()) {
	return true;	
	}
	
	}catch(Exception e) {
		System.out.println("로그인 오류");
		e.printStackTrace();
	}
	return false;
	
}

}
