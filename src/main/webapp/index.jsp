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
	
	<div class="modal fade" id="add_employee_modal" tabindex="-1" role="dialog">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">新增员工</h4>
	      </div>
	      <div class="modal-body">
	        <form class="form-horizontal">
			  <div class="form-group">
			    <label for="empName" class="col-sm-2 control-label">LastName</label>
			    <div class="col-sm-10">
			      <input type="text" name="empName" class="form-control" placeholder="empName">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="email" class="col-sm-2 control-label">Email</label>
			    <div class="col-sm-10">
			      <input type="email" name="email" class="form-control" placeholder="LastNameEmail">
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
					  <option>1</option>
					  <option>2</option>
					  <option>3</option>
					  <option>4</option>
					  <option>5</option>
					</select>
				</div>
			  </div>
			</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        <button type="button" class="btn btn-primary">Save changes</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</body>
<script type="text/javascript">
	$(function(){
		getDataByPagenum(1);
		$("#page_nav").on('click','li',function(){
			getDataByPagenum($(this).attr('data-pn'));
		})
		
		$("#add_employee_btn").on('click',function(){
			$("#add_employee_modal").modal({
				backdrop: "static",
				show: true
			})
		})
		
		//获取那一页的数据
		function getDataByPagenum(pagenum){
			$('#emp_table tbody').empty();
			$("#page_info").empty();
			$("#page_nav").empty();
			$.ajax({
				url:"${APP_PATH}/emps",
				data:"pn="+pagenum,
				type:"post",
				success:function(data){
					console.log(data.result.pageinfo)
					build_empTables(data.result.pageinfo);
					build_pageInfo(data.result.pageinfo);
					build_page_nav(data.result.pageinfo);
				}
			})
		}
		
		//解析员工数据
		function build_empTables(data){
			var emps = data.list;
			$.each(emps,function(index,item){
				var indexD = $('<td></td>').append(index+1);
				var nameD = $('<td></td>').append(item.empName);
				var genderD = $('<td></td>').append(item.gender == "M" ? "男":"女");
				var emailD = $('<td></td>').append(item.email);
				var deptNameD = $('<td></td>').append(item.department.deptName);
				var handleD = $('<td></td>').append(
						'<button class="btn btn-success btn-sm"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>编辑</button>'+
						'<button class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span>删除</button>');
				var empTR = $('<tr></tr>').append(indexD)
								.append(nameD)
								.append(genderD)
								.append(emailD)
								.append(deptNameD)
								.append(handleD);
				$('#emp_table tbody').append(empTR);
			})
		}
		//解析分页信息
		function build_pageInfo(data){
			$("#page_info").append(
			'  共'+data.pages+'页, '+
		    '当前第'+data.pageNum+'页 ,'+ 
		    '共'+data.total+'条数据, '+
		    '当前展示第'+data.startRow+'到'+data.endRow+'条数据'		
			)
		}
		
		//解析分页条
		function build_page_nav(data){
			var firstPage = $('<li data-pn="1"><a>首页</a></li>');
			var lastPage = $('<li  data-pn="'+data.pages+'"><a>末页</a></li>');
			var prePage = $('<li  data-pn="'+(data.pageNum - 1)+'"><a ><span aria-hidden="true">&laquo;</span></a></li>');
			var nextPage = $('<li  data-pn="'+(data.pageNum + 1)+'"><a><span aria-hidden="true">&raquo;</span></a></li>');
			$("#page_nav").append(firstPage);
			data.hasPreviousPage ? $("#page_nav").append(prePage): "";
			
			$.each(data.navigatepageNums,function(index,item){
				if(data.pageNum == item){
					$("#page_nav").append('<li data-pn="'+item+'" class="active"><a>'+item+'</a></li>');
				}else{
					$("#page_nav").append('<li data-pn="'+item+'"><a>'+item+'</a></li>')
				}
			})
			
			data.hasNextPage ? $("#page_nav").append(nextPage): "";
			$("#page_nav").append(lastPage);
		}
	})
</script>
</html>