<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<link href="./index.css" rel="stylesheet" />
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
			<div class="col-md-6" style="height: 69px;line-height: 69px;">
				<button class="btn btn-success" id="add_employee_btn">新增</button>
				<button class="btn btn-danger">删除</button>
			</div>
		</div>
		<!-- 表格部分 -->
		<div class="row">
		  <div class="col-md-12">
			<table class="table table-hover" id="emp_table">
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
			  
			  </tbody>
			</table>
		</div>
		</div>
		<!-- 分页信息部分 -->
		<div class="row">
			
		  <div class="col-md-6" id="page_info">
		  </div>
		  <div class="col-md-6">
		  	<nav aria-label="Page navigation">
			  <ul class="pagination" id="page_nav">
			    
			  </ul>
			</nav>
		  </div>
		</div>
	</div>
	
	<!-- 新增员工弹窗 -->
	<div class="modal fade" id="add_employee_modal" tabindex="-1" role="dialog">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">新增员工</h4>
	      </div>
	      <div class="modal-body">
	        <form class="form-horizontal" id="addEmployeeForm">
			  <div class="form-group">
			    <label for="empName" class="col-sm-2 control-label">LastName</label>
			    <div class="col-sm-10">
			      <input type="text" name="empName" class="form-control" placeholder="empName">
			       <span class="help-block"></span>
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="email" class="col-sm-2 control-label">Email</label>
			    <div class="col-sm-10">
			      <input type="email" name="email" class="form-control" placeholder="Email">
			      <span class="help-block"></span>
			    </div>
			  </div>
			  <div class="form-group">
			  	<label for="gender" class="col-sm-2 control-label">Gender</label>
		       	<div class="col-sm-10">
			        <label class="radio-inline">
					  <input type="radio" name="gender"  value="M" checked> 男
					</label>
					<label class="radio-inline">
					  <input type="radio" name="gender"  value="F"> 女
					</label>
				</div>
			  </div>
			  <div class="form-group">
			    <label for="dId" class="col-sm-2 control-label">Department</label>
			    <div class="col-sm-10">
				    <select class="form-control" name="dId">
					</select>
				</div>
			  </div>
			</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	        <button type="button" class="btn btn-primary" id="save_add_emp">保存</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	<!-- 修改员工弹窗 -->
	<div class="modal fade" id="edit_employee_modal" tabindex="-1" role="dialog">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">修改员工</h4>
	      </div>
	      <div class="modal-body">
	        <form class="form-horizontal" id="editEmployeeForm">
			  <div class="form-group">
			    <label for="empName" class="col-sm-2 control-label">LastName</label>
			    <div class="col-sm-10">
			      <input type="text" name="empName" class="form-control" placeholder="empName">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="email" class="col-sm-2 control-label">Email</label>
			    <div class="col-sm-10">
			      <input type="email" name="email" class="form-control" placeholder="Email">
			       <span class="help-block"></span>
			    </div>
			  </div>
			  <div class="form-group">
			  	<label for="gender" class="col-sm-2 control-label">Gender</label>
		       	<div class="col-sm-10">
			        <label class="radio-inline">
					  <input type="radio" name="gender"  value="M"> 男
					</label>
					<label class="radio-inline">
					  <input type="radio" name="gender"  value="F"> 女
					</label>
				</div>
			  </div>
			  <div class="form-group">
			    <label for="dId" class="col-sm-2 control-label">Department</label>
			    <div class="col-sm-10">
				    <select class="form-control" name="dId">
					</select>
				</div>
			  </div>
			</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	        <button type="button" class="btn btn-primary" id="sure_edit_emp">保存</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	<!-- 删除员工弹窗 -->
	<div class="modal fade" id="delete_employee_modal" tabindex="-1" role="dialog">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">删除员工</h4>
	      </div>
	      <div class="modal-body">
	        	确认删除员工&nbsp;&nbsp;<span id="deleteEmpTip"></span>&nbsp;吗？
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	        <button type="button" class="btn btn-primary" id="sure_dele_emp">确认</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</body>
<script type="text/javascript" src="./index.js"></script>
</html>