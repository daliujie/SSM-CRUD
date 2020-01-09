package com.jason.crud.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jason.crud.bean.Employee;
import com.jason.crud.bean.EmployeeExample;
import com.jason.crud.bean.EmployeeExample.Criteria;
import com.jason.crud.dao.EmployeeMapper;

@Service
public class EmployeeService {
	
	@Autowired
	EmployeeMapper mapper;
	
	/**
	 * 保存员工方法
	 * @param emp 新增的员工
	 * @return
	 */
	public Boolean saveEmployee(Employee emp) {
		return mapper.insertSelective(emp) == 1;
	}
	
	/**
	 * 更新用户信息
	 * @param emp 需要更新的用户
 	 * @return
	 */
	public Boolean uodataEmployee(Employee emp) {
		return mapper.updateByPrimaryKeySelective(emp) == 1;
	}
	
	/**
	 * 获取员工详细信息
	 * @param empId 员工id
	 * @return 员工信息
	 */
	public Employee getEmployeeById(int empId) {
		return mapper.selectByPrimaryKey(empId);
	}

	/**
	 * 查询所有员工
	 * @return 员工列表
	 */
	public List<Employee> getAll() {
		//这不是一个分页查询
		return mapper.selectByExampleWithDepartment(null);
	}
	
	public int deleteEmployees(String empIds) {
		EmployeeExample employeeExample = new EmployeeExample();
		Criteria createCriteria = employeeExample.createCriteria();
		
		String [] ids = empIds.split(",");
		List<Integer> list = new ArrayList<Integer>();
		
		for (String integer : ids) {
			list.add(Integer.parseInt(integer));
		}
		createCriteria.andEmpIdIn(list);
		int deleteByExample = mapper.deleteByExample(employeeExample);
		return deleteByExample;
	}
	
	/**
	 * 校验用户名是否可用
	 * @param empName
	 * @return
	 */
	public boolean checkEmployeeUserName(String empName) {
		EmployeeExample employeeExample = new EmployeeExample();
		Criteria createCriteria = employeeExample.createCriteria();
		createCriteria.andEmpNameEqualTo(empName);
		
		List<Employee> selectByExample = mapper.selectByExample(employeeExample);
		return selectByExample.size() == 0;
	}
	/**
	 * 删除员工
	 */
	public boolean deleteEmployeeById(int empId) {
		return mapper.deleteByPrimaryKey(empId) == 1;
	}
		
}
