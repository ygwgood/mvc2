<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<main>
<div class="container mt-3">
<h2>글쓰기</h2>
<form action="/board/writeFormProc" method="post" enctype="multipart/form-data">

<lable for="idx" hidden>글번호:</lable>
<input type="text" id="idx" name="idx" class="form-control" hidden>

<lable for="writeName">작성자</lable>
<input type="text" id="writeName" name="writeName" class="form-control" readonly value="${id}">
<%-- ${board.writeName} --%>
<%-- ${id}<br> --%>
<lable for="title">제목</lable>
<input type="text" id="title" name="title" class="form-control">

<lable for="content">내용</lable>
<input type="text" id="content" name="content" class="form-control">

<lable for="file">첨부파일</lable>
<input type="file" id="file" name="file" class="form-control">

<button type="submit" class="btn btn-primary">글쓰기</button>
<button type="button" class="btn btn-primary">취소</button>
</form>
</div>
</main>