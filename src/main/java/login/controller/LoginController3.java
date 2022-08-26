package login.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import login.service.MemberService;
import login.service.MemberServiceInter;

//@WebServlet("/login/*")
public class LoginController3 extends HttpServlet{
	MemberServiceInter service=
			new MemberService();

	HashMap<String,String> map=new HashMap<String,String>();

	int timeout=2*60;
@Override
public void init() throws ServletException {
	map.put("login","/login/login.jsp");
	map.put("createmember","/login/createmember.jsp");
	map.put("addlogin","/home/main.jsp");
	map.put("loginProc","/home/main.jsp");
	map.put("logout","/home/main.jsp");

}

@Override
protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String[] uris=req.getRequestURI().split("/");
	String page="/home/main.jsp";
	
	//localhost:8080/login
	if(uris.length==2) {
		page=map.get("login");
	}else{
		page=map.get(uris[2]);
		
		if(uris[2].equals("loginProc")) {
			boolean result=service.login(req,resp);
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
			}else {
				page="/login/login.jsp";
			}
				
		}else if(uris[2].equals("logout")) {
			req.getSession().invalidate();
			//로그아웃시간
			System.out.println("로그아웃 시간:"+req.getSession().getLastAccessedTime());
			SimpleDateFormat fmt=
					new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					System.out.println("로그아웃 시간:"+
					fmt.format(req.getSession().getLastAccessedTime()));
		}else if(uris[2].equals("addlogin")) {
			req.getSession().setMaxInactiveInterval(timeout);
			req.setAttribute
			("sessiontime",req.getSession().getMaxInactiveInterval());
		}
	
	}
	if(page==null) page="/home/main.jsp";
	req.setAttribute("page",page.substring(1));	
	req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
	
}
}
