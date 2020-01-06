<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>用户列表</title>
	<% 
		pageContext.setAttribute("APP_PATH",request.getContextPath());
	%>
	<!-- 
		不以/开头的都是相对路径，容易出问题
		以/开始的绝地路径，移动文件不会出问题
	 -->
	<!-- 引入bootstrap样式 -->
	<link href="${APP_PATH}/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet" />
	<!-- 引入jQuery.js -->
	<script type="text/javascript" src="${APP_PATH}/static/JQuery/jquery-3.4.1.min.js"></script>
	<!-- 引入bootstrap.js -->
	<script type="text/javascript" src="${APP_PATH}/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<!-- 标题   操作部分 -->
		<div class="row">
		  	<div class="col-md-6">
				<h1>SSM-CRUD</h1>
			</div>
			<div class="clo-md-6">
				<button class="btn btn-success">新增</button>
				<button class="btn btn-danger">删除</button>
			</div>
		</div>
		<!-- 表格部分 -->
		<div class="row">
		  <div class="col-md-12">
			<table class="table table-hover">
			  <thead>
			  	<tr>
			  	  <th>序号</th>
			  	  <th>姓名</th>
			  	  <th>性别</th>
			  	  <th>邮箱</th>
			  	  <th>部门</th>
			  	  <th>操作</th>
			  	</tr>
			  </thead>
			  <tbody>
			  <c:forEach items="${pageinfo.list}" var="emp" varStatus="xh" >
			  	<tr>
			  	  <td>${xh.count}</td>
			  	  <td>${emp.empName}</td>
			  	  <td>${emp.gender == "M" ? "男" : "女"}</td>
			  	  <td>${emp.email}</td>
			  	  <td>${emp.department.deptName}</td>
			  	  <td>
			  	  	<button class="btn btn-success btn-sm">
				  	  	<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
				  	  	编辑
			  	  	</button>
					<button class="btn btn-danger btn-sm">
						<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
						删除
					</button>
			  	  </td>
			  	</tr>
			  </c:forEach>
			  </tbody>
			</table>
		</div>
		</div>
		<!-- 分页信息部分 -->
		<div class="row">
			
		  <div class="col-md-6">
		         共${pageinfo.pages}页
		         当前第${pageinfo.pageNum}页, 
		         共${pageinfo.total}条数据
		         当前展示第${pageinfo.startRow}到${pageinfo.endRow}条数据      
		         
		  </div>
		  <div class="col-md-6">
		  	<nav aria-label="Page navigation">
			  <ul class="pagination">
			    <li><a href="${APP_PATH}/emps?pn=1">首页</a></li>
			    <c:if test="${pageinfo.hasPreviousPage}">
			    	<li>
				      <a href="${APP_PATH}/emps?pn=${pageinfo.pageNum-1}" aria-label="Previous">
				        <span aria-hidden="true">&laquo;</span>
				      </a>
				    </li>
			    </c:if>
			   	<c:forEach items="${pageinfo.navigatepageNums}" var="pagenum">
			   		<c:if test="${pageinfo.pageNum == pagenum}">
			   			<li class="active"><a  href="${APP_PATH}/emps?pn=${pagenum}">${pagenum}</a></li>
			   		</c:if>
			   		<c:if test="${pageinfo.pageNum != pagenum}">
			   			<li><a  href="${APP_PATH}/emps?pn=${pagenum}">${pagenum}</a></li>
			   		</c:if>
			   		
			   	</c:forEach>
			    <c:if test="${pageinfo.hasNextPage}">
			    	<li>
				      <a href="${APP_PATH}/emps?pn=${pageinfo.pageNum+1}" aria-label="Next">
				        <span aria-hidden="true">&raquo;</span>
				      </a>
				    </li>
			    </c:if>
			    <li><a href="${APP_PATH}/emps?pn=${pageinfo.pages}">末页</a></li>
			  </ul>
			</nav>
		  </div>
		</div>
	</div>
	
	
</body>
</html>