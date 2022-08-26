package test;

import java.util.HashMap;

public class MapTest {

	public static void main(String[] args) {
		/*
		HashMap<String,String> map=new HashMap<String,String>();
		map.put("사과","apple");
		map.put("오렌지","orange");
		map.put("망고","manggo");
		map.put("바나나","banana");
		
		System.out.println(map.get("오렌지"));
		*/
		
		/*
		HashMap<String,String> map=new HashMap<String,String>();
		map.put("/index","/WEB-INF/index.jsp");
		map.put("/login/login","/WEB-INF/login/login.jsp");
		map.put("/login/createmember","/WEB-INF/login/createmember.jsp");
		map.put("/login/addlogin","/WEB-INF/home/main.jsp");
		System.out.println(map.get("/index"));
		*/
		
		String page="/home/login.jsp";
		System.out.println(page.substring(1));
		
	}

}
