package com.jason.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jason.crud.bean.Employee;
import com.jason.crud.dao.EmployeeMapper;

@Service
public class EmployeeService {
	
	@Autowired
	EmployeeMapper mapper;

	/**
	 * ��ѯ����Ա��
	 * @return Ա���б�
	 */
	public List<Employee> getAll() {
		//�ⲻ��һ����ҳ��ѯ
		
		return mapper.selectByExampleWithDepartment(null);
	}
		
}
