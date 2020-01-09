package com.jason.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jason.crud.bean.Employee;
import com.jason.crud.service.EmployeeService;
import com.jason.crud.util.Msg;
import com.sun.xml.internal.ws.wsdl.writer.document.Service;

/**
 * 
 * 处理员工的增删改请求
 * @author jason
 *
 */
@Controller
public class EmployeeController {
	@Autowired
	EmployeeService service;
	
	@ResponseBody
	@RequestMapping("/saveEmp")
	public Msg saveEmployee(Employee emp) {
		//对新增的用户进行校验
		String email_validaye_exp = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
		String name_validate_exp = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5}$)";
		
		if(!emp.getEmpName().matches(name_validate_exp)) {
			return Msg.fail().add("error_name","用户名可以是6-16位数字字母组合或者2-6位中文");
		}
		if(!emp.getEmail().matches(email_validaye_exp)) {
			System.out.println("emp.getEmail()====" + emp.getEmail());
			return Msg.fail().add("error_email","邮箱格式不正确");
		}
		
		boolean isSave = service.saveEmployee(emp);
		if(isSave) {
			return Msg.success().add("data", "新增成功");
		}else {
			return Msg.fail();
		}
	}
	
	@ResponseBody
	@RequestMapping("deleteEmps")
	public Msg deleteEmps(String empIds) {
		int deleteEmployees = service.deleteEmployees(empIds);
		
		return Msg.success();
	}
	
	@ResponseBody
	@RequestMapping("/checkUserName")
	public Msg checkUserName(String empName) {
		boolean flag = service.checkEmployeeUserName(empName);
		
		String name_validate_exp = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5}$)";
		if(!empName.matches(name_validate_exp)) {
			return Msg.fail().add("error_name","用户名可以是6-16位数字字母组合或者2-6位中文");
		}
		
		if(flag) {
			return Msg.success();
		}
		return Msg.fail();
	}
	
	@ResponseBody
	@RequestMapping("/deleteEmp")
	public Msg deleteEmployee(int empId) {
		boolean hasDele = service.deleteEmployeeById(empId);
		if(hasDele) {
			return Msg.success();
		}
		return Msg.fail();
	}
	
	@ResponseBody
	@RequestMapping("/emp")
	public Msg getEmpById(int empId) {
		Employee employee = service.getEmployeeById(empId);
		return Msg.success().add("data", employee);
	}
	
	@ResponseBody
	@RequestMapping("/uodataEmp")
	public Msg updataEmployee(Employee employee) {
		
		String email_validaye_exp = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
		if(!employee.getEmail().matches(email_validaye_exp)) {
			return Msg.fail().add("error_email","邮箱格式不正确");
		}
		
		boolean hasUp = service.uodataEmployee(employee);
		if(hasUp) {
			return Msg.success();
		}
		
		return Msg.fail();
	}
	
	@RequestMapping("/emps")
	@ResponseBody
	public Msg getEmps(@RequestParam(value = "pn",defaultValue = "1")Integer pn) {
		//引入pagehelper插件
		//在查询之前调用下面的方法
		PageHelper.startPage(pn,5);
		//startPage后面跟着的查询就会是一个分页查询	
		List<Employee> emps = service.getAll();
		//使用pageinfo包装查询后的结果,只需要将pageinfo交给页面就行了额，
		//pageinfo封装了详细的分页信息，以及查询出来的信息，传入连续显示的页数5
		PageInfo page = new PageInfo(emps,5);
		return Msg.success().add("pageinfo", page);
	}
	
//	@RequestMapping(value = "/emps")
	public String getEmps(@RequestParam(value = "pn",defaultValue = "1")Integer pn,
			Model model){
		
		//引入pagehelper插件
		//在查询之前调用下面的方法
		PageHelper.startPage(pn,5);
		//startPage后面跟着的查询就会是一个分页查询	
		List<Employee> emps = service.getAll();
		//使用pageinfo包装查询后的结果,只需要将pageinfo交给页面就行了额，
		//pageinfo封装了详细的分页信息，以及查询出来的信息，传入连续显示的页数5
		PageInfo page = new PageInfo(emps,5);
		
		model.addAttribute("pageinfo",page);
		
		return "empList";
	}

}
