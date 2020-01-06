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
