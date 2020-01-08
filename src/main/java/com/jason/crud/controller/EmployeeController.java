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
 * ����Ա������ɾ������
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
		boolean isSave = service.saveEmployee(emp);
		if(isSave) {
			return Msg.success().add("data", "�����ɹ�");
		}else {
			return Msg.fail();
		}
	}
	
	@ResponseBody
	@RequestMapping("/checkUserName")
	public Msg checkUserName(String empName) {
		boolean flag = service.checkEmployeeUserName(empName);
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
		boolean hasUp = service.uodataEmployee(employee);
		if(hasUp) {
			return Msg.success();
		}
		
		return Msg.fail();
	}
	
	@RequestMapping("/emps")
	@ResponseBody
	public Msg getEmps(@RequestParam(value = "pn",defaultValue = "1")Integer pn) {
		//����pagehelper���
		//�ڲ�ѯ֮ǰ��������ķ���
		PageHelper.startPage(pn,5);
		//startPage������ŵĲ�ѯ�ͻ���һ����ҳ��ѯ	
		List<Employee> emps = service.getAll();
		//ʹ��pageinfo��װ��ѯ��Ľ��,ֻ��Ҫ��pageinfo����ҳ������˶
		//pageinfo��װ����ϸ�ķ�ҳ��Ϣ���Լ���ѯ��������Ϣ������������ʾ��ҳ��5
		PageInfo page = new PageInfo(emps,5);
		return Msg.success().add("pageinfo", page);
	}
	
//	@RequestMapping(value = "/emps")
	public String getEmps(@RequestParam(value = "pn",defaultValue = "1")Integer pn,
			Model model){
		
		//����pagehelper���
		//�ڲ�ѯ֮ǰ��������ķ���
		PageHelper.startPage(pn,5);
		//startPage������ŵĲ�ѯ�ͻ���һ����ҳ��ѯ	
		List<Employee> emps = service.getAll();
		//ʹ��pageinfo��װ��ѯ��Ľ��,ֻ��Ҫ��pageinfo����ҳ������˶
		//pageinfo��װ����ϸ�ķ�ҳ��Ϣ���Լ���ѯ��������Ϣ������������ʾ��ҳ��5
		PageInfo page = new PageInfo(emps,5);
		
		model.addAttribute("pageinfo",page);
		
		return "empList";
	}

}
