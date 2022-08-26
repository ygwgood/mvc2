<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<main>
<div class="container mt-3">
<h2 id="headertitle">글 상세보기[수정/삭제/댓글달기]-조회수[${board.readcount}]</h2>
<form action="/board/update" method="post" enctype="multipart/form-data">

<lable for="idx">글번호:</lable>
<input type="text" id="idx" name="idx" class="form-control" readonly value="${board.idx}">

<lable for="writeName">작성자</lable>
<input type="text" id="writeName" name="writeName" class="form-control" readonly value="${board.writeName}">

<lable for="title">제목</lable>
<input type="text" id="title" name="title" class="form-control" readonly value="${board.title}">

<lable for="content">내용</lable>
<input type="text" id="content" name="content" class="form-control" readonly value="${board.content}">

<lable for="file">첨부파일</lable>
<input type="file" id="filename" name="filename" class="form-control" readonly value="${board.filename}">

<!-- 고려사항 -->
<!-- 수정,삭제표시: 글쓴사람 id와 현재 로그인 한 id가 일치할 때 -->
<!-- 
수정버튼을 클릭했을 때 : title,content,첨부파일(덮어쓰기 가능) 
수정모드(readonly 제거 후 수정하기 submit버튼을 만들어져야함) 
만약 제목이 있을 경우 제목을 변경하고 form action을 미리 정의해야함
-->
<c:if test="${id eq board.writeId}">
<input type="button" class="btn btn-primary" value="수정">
<input type="button" class="btn btn-primary" value="삭제">
</c:if>

<!-- 댓글달기는 로그인만 되면 가능(무조건 표시) -->
<input type="button" class="btn btn-primary" value="댓글쓰기">
<!-- 취소버튼을 누르면 게시판으로 이동(/board/list) -->
<input type="button" class="btn btn-primary" value="취소">

</form>
</div>
</main>

<script>
var state="read";
        $(function(){
            $("input:button").click(function(){
                if($(this).val()=="댓글쓰기"){
                	location.href="/board/replyForm?idx=${board.idx}";
                }else if($(this).val()=="취소"){
                	location.href="/board/list";
                }else if($(this).val()=="수정"){
                	if(state=="read"){
                	$("#headertitle").html("글 수정하기");
                	$("#title").removeAttr("readonly");
                	$("#content").removeAttr("readonly");
                	$("#filename").removeAttr("readonly");
                	state="update";
                	//$(this).prop("type","submit");//수정버튼 button->submit
                	}else{
                		$(this).prop("type","submit");
                	}
                }else if($(this).val()=="삭제"){
                	var okis=confirm("정말 삭제하겠습니까?");
                	if(okis)
            		location.href="/board/delete?idx=${board.idx}";
            		else
            		location.href="/board/list";	
                }
            });
        });
       
        
</script>