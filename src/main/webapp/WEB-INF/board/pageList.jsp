<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- pagelist라는 객체의 데이터만 표현(VIEW) -->    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일반 게시판</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
  <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
  <script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>
  
  <script>
  
   $(document).ready( function() {
		$('tr').click(function(){
			var idx=$(this).find("td").eq(0).text();
			//alert(idx);
			if(idx!=""){
			location.href="/board/findOne?idx="+idx;
			}
		})
		
		$('.dropdown-item').click(function(){
			//alert('test');
			//#kind=버튼, $(this)=a태그
			$('#kind').html($(this).text());
		});
		
		$('#searchbtn').click(function(){
			//alert('test');
			
			var kind=$('#kind').text();
			var search=$('#search').val();
			//alert(kind+ " "+search);
			//alert(`${kind}`);
			//var url=`/select/searchList?kind=${kind}&search=${search}`;
			var url="/board/searchList?kind="+kind+"&search="+search;
			//alert(url);
			
			location.href=url;
			});
		
	});
	</script>
	
<style>
thead tr:first-child{background-color:lightGray;}
tr:hover{background-color:yellow;
		/* font-size:20px; font-weight: bold; */
		}
</style>

</head>
<body>
<main>
<div class="container mt-3">
<h2>일반게시판</h2>
 <div class="input-group mt-3 mb-3"> 
  <button name="kind" id="kind" type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
    검색선택
  </button>
  <div class="dropdown-menu">
  	
    <a class="dropdown-item" href="#">제목</a>
    <a class="dropdown-item" href="#">글쓴이</a>
    <a class="dropdown-item" href="#">날짜</a>
  
  </div>
<input type="text" class="form-control" id="search" name="search">
<input id="searchbtn" type="button" class="btn btn-info" value="검색">
</div>

<table class="table">
<thead><tr>
<th>글번호</th>
<th>제목</th>
<th>글쓴이</th>
<th>날짜</th>
<th>조회수</th>
</tr></thead>
<tbody>
<c:forEach var="board" items="${pagelist.list}">
<c:if test="${board.isdel eq 0 }">
<tr>
<td>${board.idx}</td>

<td style="text-align:left">
<c:forEach begin="1" end="${board.depth}">
<img src="/img/reply_icon.gif" style="width:42px;height=15px">
</c:forEach> 
${board.title}
<!-- 파일이미지 표시여부 확인 -->
<c:if test="${not empty board.filename }">
&nbsp;&nbsp;
<a href="/file/uploadFold/${board.filename}" download>
<img src="/img/file.png" style="width:20px;height:20px" 
onclick="event.cancelBubble=true">
</a>
</c:if>

</td>
<td>${board.writeName}</td>
<td>${board.writeDay}</td>
<td>${board.readcount}</td>
</tr>
</c:if>

<c:if test="${board.isdel eq 1}">
<tr onclick="event.cancelBubble=true">
<td></td><td>글이 삭제 되었습니다.</td><td></td><td></td>
</tr>
</c:if>

</c:forEach>
</tbody>
</table>

<ul class="pagination">
   <c:if test="${pagelist.startPage>1}">	
  <li class="page-item"><a class="page-link" href="/board/list?currentPage=${pagelist.startPage-5}">이전</a></li>
  </c:if>
  <c:forEach var="pno" begin="${pagelist.startPage}" 
  end="${pagelist.endPage}" step="1">
  <li class="page-item"><a class="page-link" href="/board/list?currentPage=${pno}">${pno}</a></li>
  </c:forEach>
  <c:if test="${pagelist.endPage<pagelist.totalPage}">
  <li class="page-item"><a class="page-link" href="/board/list?currentPage=${pagelist.startPage+5}">다음</a></li>
  </c:if>
</ul>


<button type="button" class="btn btn-success" onclick="location.href='/board/writeForm'">글쓰기</button>
</div>
</main>
</body>
</html>