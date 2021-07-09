<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
       	 <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">홍보게시판</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            전체글보기
                            <button id='regBtn' type='button' class='btn btn-xs pull-right' onclick="location.href='register'">새글쓰기</button>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                    <tr>
                                        <th>번 호</th>
                                        <th>제 목</th>
                                        <th>작성자</th>
                                        <th>작성일</th>
                                        <th>수정일</th>
                                    </tr>									
                                </thead>
								<tbody>
								<!-- 게시판 리스트 반복문 -->
								<c:forEach var="vo" items="${list }">
									<tr>
									 	<td>${vo.bno }</td>
									 	<td><a href="${vo.bno }" class="move">${vo.title }</a> <strong>[${vo.replycnt }]</strong></td>
									 	<td>${vo.writer }</td>
									 	<td><fmt:formatDate pattern="yyy-MM-dd HH:mm" value="${vo.regdate }"/></td>
									 	<td><fmt:formatDate pattern="yyy-MM-dd HH:mm" value="${vo.updatedate }"/></td>
									</tr>
								</c:forEach>
								</tbody>
                            </table>
							<div class="row"> <!-- start search -->
                            	<div class="col-md-12">
                            	  <div class="col-md-8"><!--search Form-->
                            		<form action="" id="searchForm">
                            			<select name="type" id="" class="form-control">
                            				<option value="">-----------</option>
                            				<option value="T" <c:out value="${pageVO.cri.type=='T'?'selected':'' }" />>제목</option>
                            				<option value="C" <c:out value="${pageVO.cri.type=='C'?'selected':'' }" />>내용</option>
                            				<option value="W" <c:out value="${pageVO.cri.type=='W'?'selected':'' }" />>작성자</option>
                            				<option value="TC" <c:out value="${pageVO.cri.type=='TC'?'selected':'' }" />>제목 or 내용</option>
                            				<option value="TCW" <c:out value="${pageVO.cri.type=='TCW'?'selected':'' }" />>제목 or 내용 or 작성자</option>
                            			</select>
                            			<input type="text" name="keyword" value="${pageVO.cri.keyword }" />
                            			<!-- 검색시에도 페이지당 게시물 수와 현재 페이지에 대한 정보가 따라가야함 -->
                            			<input type="hidden" name="pageNum" value="${pageVO.cri.pageNum }" />
                            			<input type="hidden" name="amount" value="${pageVO.cri.amount }"/>
                            			<button type="button" class="btn btn-default">검색</button>
                            		</form>
                            	   </div>
                            	   <div class="col-md-2 col-md-offset-2">
                            	   	<!--페이지 목록 갯수 지정하는 폼-->
                            	   	<select name="" id="amount" class="form-control">
                            	   		<option value="10" <c:out value="${pageVO.cri.amount==10?'selected':'' }" />>10</option>
                            	   		<option value="20" <c:out value="${pageVO.cri.amount==20?'selected':'' }" />>20</option>
                            	   		<option value="30" <c:out value="${pageVO.cri.amount==30?'selected':'' }" />>30</option>
                            	   		<option value="40" <c:out value="${pageVO.cri.amount==40?'selected':'' }" />>40</option>
                            	   	</select>
								  </div>
                             	 </div>                             	 
                      		 </div><!-- end search -->
                            <!-- start Pagination -->
                            <div class="text-center">
                            	<ul class="pagination">
                            		<c:if test="${pageVO.prev }">
                            			<li class="paginate_button previous"><a href="${pageVO.startPage-1 }">Previous</a></li>
                            		</c:if>
                            		
                            		<c:forEach var="idx" begin="${pageVO.startPage }" end="${pageVO.endPage }">
                            			<li class="paginate_button ${pageVO.cri.pageNum==idx?'active':'' }">
                            				<a href="${idx }">${idx }</a>
                            			</li>
                            		</c:forEach>
                            		
                            		<c:if test="${pageVO.next }">
                            			<li class="paginate_button next"><a href="${pageVO.endPage+1 }">Next</a></li>
                            		</c:if>
                            	</ul>
                            </div>
                            <!-- end Pagination -->   
                            </div>
                            <!-- end panel-body -->
                        </div>
                        <!-- end panel -->
                    </div>                   
                </div>
</body>
</html>