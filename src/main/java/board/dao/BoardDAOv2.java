package board.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.junit.Test;

import board.vo.BoardVO;
import board.vo.PageList;

public class BoardDAOv2 implements BoardDaoInter {
	private static Connection conn=null;
	PreparedStatement pstmt;

	public BoardDAOv2(){
		conn=getConnection();
		/*
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "test", "1111");
		}catch(Exception e) {
			
		}
		*/
	}
	
public static Connection getConnection() {
		
		if(conn!=null) {
			return conn;
		}else {
			Context initContext=null;
			
			try {
				initContext=new InitialContext();
				Context envContext=
						(Context) initContext.lookup("java:/comp/env");
				
				DataSource ds=(DataSource) envContext.lookup("jdbc/oracle");
				conn=ds.getConnection();
				//System.out.println(conn);
				
				
			} catch (NamingException | SQLException e) {
				e.printStackTrace();
			}
			return conn;
		}
	}
	
	

	public int insert(BoardVO board){
		try {
		String sql="insert into board(idx,title,content, readcount,"
				+ "groupid, depth, groupseq,"
				+ "writeid,writename, writeday,"
				+ "filename,isdel,kind)"
				+ "values(board_idx_seq.nextval,?,?,0,"
				+ "0,0, 1,"
				+ "?,?,sysdate,"
				+ "?,0,'일반게시판')";
	
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1,board.getTitle());
		pstmt.setString(2,board.getContent());
		pstmt.setString(3,board.getWriteId());
		pstmt.setString(4,board.getWriteName());
		
		pstmt.setString(5,board.getFilename());
		
		int result=pstmt.executeUpdate();
		return result;
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	//@Test
	public void insertTest() {
		BoardVO board=new BoardVO();
		board.setTitle("게시판 테스트");
		board.setContent("게시판 테스트 내용");
		board.setWriteId("admin");
		board.setWriteName("김관리");
		board.setFilename("");
		insert(board);
		
	}
	
	public int replyInsert(BoardVO board){
		try {
		String sql="insert into board(idx,title,content, readcount,"
				+ "groupid, depth, groupseq,"
				+ "writeid,writename, writeday,"
				+ "filename,isdel,kind)"
				+ "values(board_idx_seq.nextval,?,?,0,"
				+ "?,?, 1,"
				+ "?,?,sysdate,"
				+ "?,0,'일반게시판')";
	
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1,board.getTitle());
		pstmt.setString(2,board.getContent());
		pstmt.setInt(3, board.getGroupId());
		pstmt.setInt(4, board.getDepth());
		pstmt.setString(5,board.getWriteId());
		pstmt.setString(6,board.getWriteName());
		
		pstmt.setString(7,board.getFilename());
		
		int result=pstmt.executeUpdate();
		return result;
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	//@Test
	public void replyInsertTest() {
			BoardVO board=new BoardVO();
			board.setTitle("게시판 테스트 64댓글");
			board.setContent("게시판 테스트 내용 64댓글");
			board.setGroupId(64);
			board.setDepth(1);
			board.setWriteId("admin");
			board.setWriteName("김관리");
			board.setFilename("");
			replyInsert(board);
			
		}
	
//////////////글의 전체 갯수/////////////////
public int totalCount() {
int totalCount=0;
String sql="select count(*) from Board";

try {
		pstmt=conn.prepareStatement(sql);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			totalCount=rs.getInt("count(*)");
			//System.out.println("전체게시물수:"+totalCount);
		}
}catch(Exception e) {e.printStackTrace(); }

return totalCount;
}

//@Test
public void totalCountTest() {
		System.out.println(totalCount());
}

public List pageList(int startRow, int endRow, int totalPage, int currentPage, int totalCount) {
///////////글의 시작범위와 끝범위의 데이터를 가지오는 작업//////////
String sql="select rownum,b.* from ";
sql+="(select rownum rn,a.* from ";
sql+="(select * from board start with groupid=0 ";
sql+="connect by prior idx=groupid ";
sql+="order siblings by idx desc)a ";
sql+="where rownum <=? ";
sql+="order by rownum desc) b ";
sql+="where rownum between 1 and ? ";
sql+="order by b.rn asc";

List<BoardVO> list=new ArrayList();

try {
pstmt=conn.prepareStatement(sql);

//pstmt.setInt(1, endRow);
pstmt.setInt(1, currentPage*10);
//마지막 페이지가 10개의 게시물이 아닌 경우 나머지 값을 활용하여 범위확정 
if(totalPage==currentPage){
	pstmt.setInt(2,totalCount%10);
}else{
	pstmt.setInt(2,10);
}

ResultSet rs=pstmt.executeQuery();

while(rs.next()){
	BoardVO board=new BoardVO();
	board.setIdx(rs.getInt("idx"));
	board.setTitle(rs.getString("title"));
	board.setWriteName(rs.getString("writename"));
	board.setWriteDay(rs.getDate("writeday"));
	board.setReadcount(rs.getInt("readcount"));
	board.setDepth(rs.getInt("depth"));
	list.add(board);
}
}catch(Exception e) { e.printStackTrace();}
return list;
}

@Test
public void pageListTest() {
	
	//pageList(int startRow, int endRow, int totalPage, int currentPage, int totalCount)
	List<BoardVO> list
	=pageList(31, 40, totalCount()/10, 4, totalCount());
	for(BoardVO board:list) {
		System.out.println(board);
	}
}

public BoardVO findOne(int _idx){
	try {
	String sql="select * from Board where idx=?";
	pstmt=conn.prepareStatement(sql);
	pstmt.setInt(1, _idx);
	ResultSet rs=pstmt.executeQuery();
	if(rs.next()) {
		BoardVO board=new BoardVO();
		board.setIdx(rs.getInt("idx"));
		board.setTitle(rs.getString("title"));
		board.setContent(rs.getString("content"));
		board.setWriteName(rs.getString("writename"));
		board.setWriteDay(rs.getDate("writeday"));
		board.setReadcount(rs.getInt("readcount"));
		board.setFilename(rs.getString("filename"));
		return board;
	}
	return null;
	}catch(Exception e) {
		return null;
	}
}

//@Test
public void findOneTest() {
	System.out.println(findOne(1));
}

public void readCountUp(int idx) {
	try {
		String sql=
		"update Board set readcount=readcount+1 where idx=?";
		pstmt=conn.prepareStatement(sql);
		pstmt.setInt(1,idx);
		int result=pstmt.executeUpdate();
		
		}catch(Exception e) {
			e.printStackTrace();
		}
}
	/*
	public List<BoardVO> findAll(){
		try {
		String sql="select * from Board";
		pstmt=conn.prepareStatement(sql);
		ResultSet rs=pstmt.executeQuery();
		List<BoardVO> list=new ArrayList();
		while(rs.next()) {
			int idx=rs.getInt("idx");
			String name=rs.getString("name");
			String hp=rs.getString("hp");
			String memo=rs.getString("memo");
			BoardVO imsi=
					new BoardVO(idx, name, hp, memo);
			list.add(imsi);
		}
		return list;
		}catch(Exception e) {
			return null;
		}
	}

	

	public int update(BoardVO pb){
		try {
		if(exist(pb.getIdx())) {
		String sql=
		"update Board set name=?,hp=? ,memo=? where idx=?";
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, pb.getName());
		pstmt.setString(2, pb.getHp());
		pstmt.setString(3, pb.getMemo());
		pstmt.setInt(4, pb.getIdx());
		int result=pstmt.executeUpdate();
		return result;
		}
		return 0;
		}catch(Exception e) {
			System.out.println("������Ʈ �����߻�");
			e.printStackTrace();
			return 0;
		}
	}

	public int delete(int idx){
		try {
		if(exist(idx)) {
		String sql="delete from Board where idx=?";
		pstmt=conn.prepareStatement(sql);
		pstmt.setInt(1, idx);
		int result=pstmt.executeUpdate();
		return result;
		}
		return 0;
		}catch(Exception e) {
			return 0;
		}
	}
	public boolean exist(int idx){
		try {
		String sql="select * from Board where idx=?";
		pstmt=conn.prepareStatement(sql);
		pstmt.setInt(1, idx);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()) {
			return true;
		}
		return false;
		}catch(Exception e) {
			return false;
		}
	}

	
*/

@Override
public void close() {
	// TODO Auto-generated method stub
	
}

@Override
public int update(BoardVO board) {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public int delete(int idx) {
	// TODO Auto-generated method stub
	return 0;
}
}
