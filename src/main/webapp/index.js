$(function(){
	let baseURL = "http://localhost:8088/ssm-crud/";
	let currPage = 1;
	let lastPage = 2;
	getDataByPagenum(currPage);
	
	//分页条点击事件
	$("#page_nav").on('click','li',function(){
		getDataByPagenum($(this).attr('data-pn'));
	})
	
	//新增按钮点击事件
	$("#add_employee_btn").on('click',function(){
		initFrom("#add_employee_modal");
		$("#add_employee_modal").modal({
			backdrop: "static",
			show: true
		})
		renderDeptSelect();
	})
	
	//删除按钮点击事件
	$("#emp_table tbody").on('click','button.dele-emp-btn',function(){
		var rowData = $(this).parent().parent().data("rowData");
		$("#delete_employee_modal").modal({
			backdrop: "ststic",
			show: true
		})
		$("#delete_employee_modal #deleteEmpTip").text(rowData.empName);
		$("#sure_dele_emp").data('rowData',rowData);
	})
	
	//编辑按钮点击事件
	$("#emp_table tbody").on('click','button.edit-emp-btn',function(){
		var rowData = $(this).parent().parent().data("rowData");
		$("#sure_edit_emp").data('rowData',rowData);
		$("#edit_employee_modal").modal({
			backdrop: "ststic",
			show: true
		})
		getEmployeeById(rowData);
	})
	
	//确认保存新增员工点击事件
	$("#save_add_emp").on('click',function(){
		saveAddEmp();
	})
	
	//确认修改员工按钮
	$("#sure_edit_emp").on('click',function(){
		var rowData = $(this).data('rowData');
		$.ajax({
			url:baseURL+"uodataEmp",
			type: "post",
			data:$("#editEmployeeForm").serialize() + '&empId='+rowData.empId,
			success: function(data){
				if(data.code == 100){
					$("#edit_employee_modal").modal('hide');
					getDataByPagenum(currPage);
				}
			}
		})
		
	})
	
	//确认删除员工按钮点击事件
	$("#sure_dele_emp").on('click',function(){
		var rowData = $(this).data("rowData");
		$.ajax({
			url: baseURL+"deleteEmp",
			data: "empId="+rowData.empId,
			success: function(data){
				if(data.code == 100){
					$("#delete_employee_modal").modal('hide');
					getDataByPagenum(currPage);
				}
			}
		})
	})
	
	//保存新增员工方法
	function saveAddEmp(){
		
		if(!validate_addemp_from()){
			return ;
		}
		
		$.ajax({
			url:baseURL+"saveEmp",
			type: "post",
			data:$("#addEmployeeForm").serialize(),
			success: function(data){
				if(data.code == 100){
					$("#add_employee_modal").modal('hide');
					getDataByPagenum(lastPage);
				}
			}
		})
	}
	
	//校验新增员工表单数据
	function validate_addemp_from(){
		//拿到需要校验的数据
		var name_validate_exp = /(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5}$)/;
		var email_validaye_exp = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
		var empName = $('#add_employee_modal input[name="empName"]').val();
		var empEmail = $('#add_employee_modal input[name="email"]').val();
		if(!name_validate_exp.test(empName)){
			show_validate_msg($('#add_employee_modal input[name="empName"]'),"error","用户名可以是6-16位数字字母组合或者2-6位中文");
			return false;
		}else{
			show_validate_msg($('#add_employee_modal input[name="empName"]'),"success","");
		}
		
		if(!email_validaye_exp.test(empEmail)){
			show_validate_msg($('#add_employee_modal input[name="email"]'),"error","邮箱格式不正确");
			return false;
		}else{
			show_validate_msg($('#add_employee_modal input[name="email"]'),"success","");
		}
		return true;
	}	
	
	
	//校验表单显示
	function show_validate_msg(ele,status,msg){
		$(ele).parent().removeClass('has-success has-error')
		if(status == "success"){
			$(ele).parent().addClass('has-success');
			$(ele).next("span").text("");
		}else{
			$(ele).parent().addClass('has-error');
			$(ele).next("span").text(msg); 
		}
	}
	
	//初始化表单
	function initFrom(ele){
		$(ele)[0].reset;
		var inputArr = $(ele).find('input');
		inputArr.push($(ele).find('textarea'));
		$.each(inputArr,function(index,item){
			$(item).parent().removeClass('has-success has-error');
			$(item).val('');
			$(item).next('span').text("");
		})
	}
	
	
	//获取员工信息详情
	function getEmployeeById(rowData){
		$.ajax({
			url: baseURL+"emp",
			data: "empId="+rowData.empId,
			success: function(data){
				data = data.result.data;
				
				renderDeptSelect(data.dId);
				
				//回显员工信息
				var $this = $('#edit_employee_modal');
				$('input[name="empName"]',$this).val(data.empName).attr('readonly','readonly');
				$('input[name="email"]',$this).val(data.email);
				$('input[name="gender"][value='+data.gender+']',$this).attr('checked','true');
				
			}
		})
	}
	
	
	//获取所有部门数据并渲染
	function renderDeptSelect(id){
		$('select[name="dId"]').empty();
		$.ajax({
			url:baseURL+"depts",
			type: "get",
			success: function(data){
				data = data.result.data;
				$.each(data,function(index,item){
					$('select[name="dId"]').append(`<option ${item.deptId == id ? 'selected' : ''} value="${item.deptId}">${item.deptName}</option>`)
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
			url:baseURL+"emps",
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
					'<button class="btn btn-success btn-sm edit-emp-btn" data-id="'+item.empId+'"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>编辑</button>'+
					'<button class="btn btn-danger btn-sm dele-emp-btn" data-id="'+item.empId+'"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span>删除</button>');
			var empTR = $('<tr></tr>').append(indexD)
							.append(nameD)
							.append(genderD)
							.append(emailD)
							.append(deptNameD)
							.append(handleD).data('rowData',item);
			$('#emp_table tbody').append(empTR);
		})
	}
	//解析分页信息
	function build_pageInfo(data){
		lastPage = parseInt(data.total/data.pageSize) + 1;
		currPage = data.pageNum,
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