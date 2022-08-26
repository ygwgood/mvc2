package board.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import board.dao.BoardDAO;
import board.dao.BoardDAOv2;
import board.dao.BoardDaoInter;
import board.vo.BoardVO;
import board.vo.PageList;
import login.dao.MemberDAO;
import login.dao.MemberDAOInter;
import login.vo.MemberVO;

public class BoardService implements BoardServiceInter{
	BoardDaoInter dao=new BoardDAO();
	MemberDAOInter memberdao=new MemberDAO();
		
	public PageList pageList(HttpServletRequest req, HttpServletResponse resp) {
		//전체게시물 수
		int totalCount=0;
		//페이지당 글의 수
		int countPerPage=10;
		//전체페이지수
		int totalPage=0;
		//시작페이지
		int startPage=0;
		//끝페이지
		int endPage=0;
		//글의 시작번호
		int startRow=0;
		//글의 끝번호
		int endRow=0;
		//현재페이지
		int currentPage=1;
		List<BoardVO> list=null;
		
		//위의 모든 정보는 pagelist에 모두 저장되어 cotroller에게 전달
		PageList plist=new PageList();
		
		//전체게시물수 check
		totalCount=dao.totalCount();
		
		//전체페이지수 check
		totalPage=(totalCount/countPerPage)+1;
		if((totalCount%countPerPage)==0) 
			totalPage=(totalCount/countPerPage);

		//전달되어지는 현재페이지를 확인
		String _currentPage=req.getParameter("currentPage");
		if(_currentPage==null) currentPage=1;
		else if(!_currentPage.equals(""))
		currentPage=Integer.parseInt(_currentPage);//check
		
		//현재페이지에 대한 글의 시작번호 끝번호
		startRow=(currentPage-1)*countPerPage+1;
		endRow=startRow+countPerPage-1;
		
		
		//위에서 확인정보를 이용하여 페이지의 dao로부터 정보를 가져옴
		list=dao.pageList
		(startRow, endRow, totalPage, currentPage, totalCount);
		
		//표시할 시작페이지
		if(currentPage<=5){
			startPage=1;	
		}else{
			if(currentPage%5==0){
				startPage=(currentPage/5)*5;	
			}else{
				startPage=(currentPage/5)*5+1; //--처리 10페이지일 때 문제 발생
			}
		}

		//표시할 끝페이지
		endPage=startPage+4;
		
		//전체페이지가 표현하려는 페이지보다 클 경우 전체페이지수까지 표시 
		if(endPage>totalPage) endPage=totalPage;

		//findAll.jsp에 view를 하기위해 필요한 데이터 입력(MODEL)
		plist.setList(list);
		plist.setStartPage(startPage);
		plist.setEndPage(endPage);
		plist.setCurrentPage(currentPage);
		plist.setCountPerPage(countPerPage);
		plist.setTotalCount(totalCount);
		plist.setTotalPage(totalPage);
		
		
		//한글파일 다운로드 헤드설정
		//String filename = java.net.URLEncoder.encode(plist.getList().get, "UTF-8");
		//resp.setContentType("application/octer-stream");
		//resp.setHeader("Content-Transper-Encoding", "binary");
		//resp.setHeader("Content-Disposition", "attachment;filename="+filename+ext);
		
		return plist;
	}

	@Override
	public BoardVO findOne(HttpServletRequest req, HttpServletResponse resp) {
		//게시판에서 글을 클릭하면 idx가 전달되며 이값을 저장하고 dao.findOne의 입력값으로 사용
		int idx=Integer.parseInt(req.getParameter("idx"));
		//idx에 해당하는 값을 조회하기 전 글을 읽었으므로 조회수 1이 증가
		dao.readCountUp(idx);
		//findOne.jsp에서 사용할 데이터 주세요라고 요청
		BoardVO board=dao.findOne(idx);
		return board;
	}

	@Override
	public MemberVO findOne(String id) {
		return memberdao.findOne(id);
	}

	@Override
	public int insert(HttpServletRequest req, HttpServletResponse resp) {
		String savePath="C:\\project\\work\\jwork\\mvc2\\src\\main\\webapp\\file\\uploadFold";
		int fileSize=10*1024*1024;
		MultipartRequest multi=null;
		try {
			
			//System.out.println("title:"+req.getParameter("title"));
			//System.out.println("content:"+req.getParameter("content"));
			//폼에서 전달되는 데이터+파일데이터 multi안에 존재
			multi = new MultipartRequest
			(req,savePath,fileSize,"utf-8",new DefaultFileRenamePolicy());
			
			
			Enumeration files=multi.getFileNames();
			
			//아래 함수만으로 파일이 업로드 됨
			String file=(String)files.nextElement();
			System.out.println("file:"+file);
			
			//서버에 저장되는 파일이름, 
			//만약에 같은 이름이 있을 경우 뒤에 숫자가 증가하는 형태
			//실제 저장되는 파일명, 사용
			String filename = multi.getFilesystemName(file);
			System.out.println("filename:"+filename);

			//클라이언트에서 넘어오는 파일명, 사용x
			//String orignfilename = multi.getOriginalFileName(file);
			//System.out.println("originfile:"+orignfilename);
			String title=multi.getParameter("title");
			String content=multi.getParameter("content");
			System.out.println("title:"+multi.getParameter("title"));
			System.out.println("content:"+multi.getParameter("content"));
			//System.out.println("finaname:"+multi.getParameter("filename"));
			
			BoardVO board=new BoardVO();
			//글쓰기 필요한 내용 확인
			board.setTitle(title);
			board.setContent(content);
			board.setWriteId((String)req.getSession().getAttribute("id"));
			board.setWriteName((String)req.getSession().getAttribute("id"));
			board.setFilename(filename);
			return dao.insert(board);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public int update(HttpServletRequest req, HttpServletResponse resp) {
		BoardVO board=new BoardVO();
		int idx=0;
		String title=null;
		String content=null;
		String filename=null;
		
		String savePath="C:\\project\\work\\jwork\\mvc2\\src\\main\\webapp\\file\\uploadFold";
		int fileSize=10*1024*1024;
		MultipartRequest multi=null;
		try {
			
			multi = new MultipartRequest
			(req,savePath,fileSize,"utf-8",new DefaultFileRenamePolicy());
			
			Enumeration files=multi.getFileNames();
			//String file=(String)files.nextElement();
			//String filename = multi.getFilesystemName(file);
			//String orignfilename = multi.getOriginalFileName(file);
			//System.out.println("originfile:"+orignfilename);
			
			idx=Integer.parseInt(multi.getParameter("idx"));
			title=multi.getParameter("title");
			content=multi.getParameter("content");

			board.setIdx(idx);
			board.setTitle(title);
			board.setContent(content);
			//board.setFilename(filename);
			return dao.update(board);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public int delete(HttpServletRequest req, HttpServletResponse resp) {
		int idx=Integer.parseInt(req.getParameter("idx"));
		return dao.delete(idx);
	}

	@Override
	public BoardVO replyInfo(HttpServletRequest req, HttpServletResponse resp) {
		BoardVO board=dao.findOne
				(Integer.parseInt
						(req.getParameter("idx")));
		BoardVO sendboard=new BoardVO();
		// mysql 인 경우
		//부모글의 groupid==0인 경우 그렇지 않은 경우 나누어 생각
		//부모글의 idx, 부모글의 GroupID는 댓글의 grupid결정
		//부모글이 댓글인 아닌경우 부모groupid==0, 자식글의 groupid=부모글의 idx
		//부모글이 댓글인 경우 부모groupid!=0, 자식글의 groupid=부모글의 groupid
		
		//oracle 인 경우
		//grupid는 부모글의 idx된다.
		sendboard.setIdx(board.getIdx());
		sendboard.setTitle(board.getTitle());
		sendboard.setGroupId(board.getGroupId());
		sendboard.setDepth(board.getDepth());
		return sendboard;
	}

	@Override
	public int replyInsert(HttpServletRequest req, HttpServletResponse resp) {
		String title="";
		String content="";
		String writeName="";
		String filename="";
		String groupId="";
		String depth="";
		
		String savePath="C:\\project\\work\\jwork\\mvc2\\src\\main\\webapp\\file\\uploadFold";
		int fileSize=10*1024*1024;
		MultipartRequest multi=null;
		try {
			
			multi = new MultipartRequest
			(req,savePath,fileSize,"utf-8",new DefaultFileRenamePolicy());
			
			//폼에서 전달한 데이터를 먼저 입력받도록 함
			title=multi.getParameter("title");
			content=multi.getParameter("content");
			writeName=multi.getParameter("writeName");
			groupId=multi.getParameter("groupId");
			depth=multi.getParameter("depth");
			
			//file관련 처리
			Enumeration files=multi.getFileNames();
			String file=(String)files.nextElement();
			filename = multi.getFilesystemName(file);
			
			BoardVO board=new BoardVO();
			//글쓰기 필요한 내용 확인
			board.setTitle(title);
			board.setContent(content);
			board.setWriteId((String)req.getSession().getAttribute("id"));
			board.setWriteName((String)req.getSession().getAttribute("id"));
			board.setGroupId(Integer.parseInt(groupId));
			board.setDepth(Integer.parseInt(depth));
			board.setFilename(filename);
			return dao.replyInsert(board);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return 0;
	}
	
	

}
