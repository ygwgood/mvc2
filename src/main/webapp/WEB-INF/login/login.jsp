<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<main>
<div class="container">
<form action="/login/loginProc" method="post">
    <div class="form-group">
      <label for="id">ID:</label>
      <input type="text" class="form-control" id="id" name="id" placeholder="아이디">
    </div>
    <div class="form-group">
      <label for="password">Password:</label>
      <input type="password" class="form-control" id="password" name="password"placeholder="패스워드">
    </div>
    <a href="/login/createmember">회원가입</a>
    /<a href="#">아이디</a>
    <a href="#">비밀번호찾기</a>
    <button type="submit" class="btn btn-primary">로그인</button>
  </form>
  </div>
</main>