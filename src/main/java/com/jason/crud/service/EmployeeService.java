package com.jason.crud.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jason.crud.bean.Employee;
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
	 * ��ѯ����Ա��
	 * @return Ա���б�
	 */
	public List<Employee> getAll() {
		//�ⲻ��һ����ҳ��ѯ
		return mapper.selectByExampleWithDepartment(null);
	}
	/**
	 * ɾ��Ա��
	 */
	public boolean deleteEmployeeById(int empId) {
		return mapper.deleteByPrimaryKey(empId) == 1;
	}
		
}
