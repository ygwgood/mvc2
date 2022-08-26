<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<main>
<div class="container mt-3">
<h2 id="headertitle">댓글쓰기</h2>
<form action="/board/replyFormProc" method="post" enctype="multipart/form-data">

<lable for="writeName">작성자</lable>
<input type="text" id="writeName" name="writeName" class="form-control" readonly value="${id}">

<lable for="title">제목</lable>
<input type="text" id="title" name="title" class="form-control" value="re:${board.title}">

<lable for="content">내용</lable>
<input type="text" id="content" name="content" class="form-control">

<lable for="file">첨부파일</lable>
<input type="file" id="filename" name="filename" class="form-control">

<button type="submit" class="btn btn-primary">댓글쓰기</button>
<button type="button" class="btn btn-primary" onclick="location.href='/board/list'">취소</button>

<!-- 숨겨서 넘겨야하는 정보 -->
<%-- 
<c:if test="${board.groupId eq 0}">
<input hidden type="text" name="groupId" value="${board.idx}">
</c:if>

<c:if test="${board.groupId ne 0}">
<input hidden type="text" name="groupId" value="${board.groupId}">
</c:if>  
--%>

<c:choose>
<c:when test="${board.groupId eq 0}">
<input hidden type="text" name="groupId" value="${board.idx}">
</c:when>

<%--
<c:when test="${board.groupId ne 0}">
<input hidden type="text" name="groupId" value="${board.groupId}">
</c:when>
--%>
</c:choose> 

<input hidden type="text" name="depth" value="${board.depth+1}">
</form>
</div>
</main>

