package com.jason.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jason.crud.bean.Department;
import com.jason.crud.dao.DepartmentMapper;

@Service
public class DepartmentService {
	
	@Autowired
	private DepartmentMapper mapper;
	
	public List<Department> getDepartmentAll(){
		return mapper.selectByExample(null);
	}

}
