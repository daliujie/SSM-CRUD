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
	 * ����Ա������
	 * @param emp ������Ա��
	 * @return
	 */
	public Boolean saveEmployee(Employee emp) {
		return mapper.insertSelective(emp) == 1;
	}
	
	/**
	 * �����û���Ϣ
	 * @param emp ��Ҫ���µ��û�
 	 * @return
	 */
	public Boolean uodataEmployee(Employee emp) {
		return mapper.updateByPrimaryKeySelective(emp) == 1;
	}
	
	/**
	 * ��ȡԱ����ϸ��Ϣ
	 * @param empId Ա��id
	 * @return Ա����Ϣ
	 */
	public Employee getEmployeeById(int empId) {
		return mapper.selectByPrimaryKey(empId);
	}

	/**
	 * ��ѯ����Ա��
	 * @return Ա���б�
	 */
	public List<Employee> getAll() {
		//�ⲻ��һ����ҳ��ѯ
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
	 * У���û����Ƿ����
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
	 * ɾ��Ա��
	 */
	public boolean deleteEmployeeById(int empId) {
		return mapper.deleteByPrimaryKey(empId) == 1;
	}
		
}
