package login.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import login.service.MemberService;
import login.service.MemberServiceInter;

//@WebServlet("/login/*")
public class LoginController extends HttpServlet{
	MemberServiceInter service=
			new MemberService();
	int timeout=2*60;
@Override
protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String[] uris=req.getRequestURI().split("/");
	String page="login/";
	
	//localhost:8080/login
	if(uris.length==2) {
		req.setAttribute("page","login/login.jsp");	
		req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
	}else{
		//localhost:8080/login/login
		//로그인페이지
		if(uris[2].equals("login")) {
			page+="login.jsp";
			req.setAttribute("page",page);	
			req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
		//회원가입페이지
		}else if(uris[2].equals("createmember")) {
			page+="createmember.jsp";
			req.setAttribute("page",page);	
			req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
		}else if(uris[2].equals("loginProc")) {
			boolean result=service.login(req,resp);
			if(result==true) {
				//로그인이 성공했을 때 세션 발급
				req.getSession().
				setAttribute("id", req.getParameter("id"));
				//세션시간 설정
				//req.getSession().setMaxInactiveInterval(10);
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
				//page+="loginsucess.jsp";
				page="/home/main.jsp";
				req.setAttribute("page",page);
			}else {
				//로그인이 실패했을 경우
				//page+="loginfail.jsp";
				page+="login.jsp";
				req.setAttribute("page",page);
			}
				
			req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
		}else if(uris[2].equals("logout")) {
			req.getSession().invalidate();
			//로그아웃시간
			System.out.println("로그아웃 시간:"+req.getSession().getLastAccessedTime());
			SimpleDateFormat fmt=
					new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					System.out.println("로그아웃 시간:"+
					fmt.format(req.getSession().getLastAccessedTime()));
			//service.logout(req,resp);
			page="/home/main.jsp";
			req.setAttribute("page",page);	
			req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
		}else if(uris[2].equals("addlogin")) {
			//req.getSession().setMaxInactiveInterval(10);
			req.getSession().setMaxInactiveInterval(timeout);
			req.setAttribute
			("sessiontime",req.getSession().getMaxInactiveInterval());
			page="/home/main.jsp";
			req.setAttribute("page",page);	
			req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
		}
		
		
	}
}
}
