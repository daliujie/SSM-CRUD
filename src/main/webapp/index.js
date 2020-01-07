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
			renderDeptSelect();
		})
		
		$("#save_add_emp").on('click',function(){
			saveAddEmp();
		})
		
		
		//保存新增员工方法
		function saveAddEmp(){
			///
			$.ajax({
				url:"http://localhost:8088/ssm-crud/saveEmp",
				type: "get",
				data:$("#addEmployeeForm").serialize(),
				success: function(data){
					data = data.result.data;
					$.each(data,function(index,item){
						$('select[name="dId"]').append(`<option value="${item.deptId}">${item.deptName}</option>`)
					})
				}
			})
		}
		//获取所有部门数据并渲染
		function renderDeptSelect(){
			$('select[name="dId"]').empty();
			$.ajax({
				url:"http://localhost:8088/ssm-crud/depts",
				type: "get",
				success: function(data){
					data = data.result.data;
					$.each(data,function(index,item){
						$('select[name="dId"]').append(`<option value="${item.deptId}">${item.deptName}</option>`)
					})
				}
			})
		}
		
		//获取那一页的数据
		function getDataByPagenum(pagenum){
			$('#emp_table tbody').empty();
			$("#page_info").empty();
			$("#page_nav").empty();
			$.ajax({
				url:"http://localhost:8088/ssm-crud/emps",
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