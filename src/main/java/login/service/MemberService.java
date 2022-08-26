package login.service;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import login.dao.MemberDAO;
import login.dao.MemberDAOInter;
import login.vo.MemberVO;

public class MemberService implements MemberServiceInter{
	MemberDAOInter dao=new MemberDAO();
	int timeout=2*60;
	@Override
	public boolean login(HttpServletRequest req, HttpServletResponse resp) {
		String id=req.getParameter("id");
		String password=req.getParameter("password");
		MemberVO member=new MemberVO(id,password);
		boolean result=dao.login(member);
		if(result==true) {
			req.getSession().
			setAttribute("id", req.getParameter("id"));
			req.getSession().setMaxInactiveInterval(timeout);
			//어떤 ip, 로그인 시간
			System.out.println("로그인 ip:"+req.getRemoteAddr());
			System.out.println("로그인 시간:"+req.getSession().getCreationTime());
			SimpleDateFormat fmt=
			new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			System.out.println("로그인 시간:"+
			fmt.format(req.getSession().getCreationTime()));
			//세션시간 전달
			req.setAttribute
			("sessiontime",req.getSession().getMaxInactiveInterval());
		}
		
		return result;
		
	}

	@Override
	public void logout(HttpServletRequest req, HttpServletResponse resp) {
		//세션해제
		req.getSession().invalidate();
		//로그아웃시간
		System.out.println("로그아웃 시간:"+req.getSession().getLastAccessedTime());
		SimpleDateFormat fmt=
				new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				System.out.println("로그아웃 시간:"+
				fmt.format(req.getSession().getLastAccessedTime()));
		
	}

	@Override
	public void addlogin(HttpServletRequest req, HttpServletResponse resp) {
		req.getSession().setMaxInactiveInterval(timeout);
		req.setAttribute
		("sessiontime",req.getSession().getMaxInactiveInterval());
		
	}
	
	
}
