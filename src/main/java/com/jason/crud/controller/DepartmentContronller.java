package com.jason.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jason.crud.service.DepartmentService;
import com.jason.crud.util.Msg;

@Controller
public class DepartmentContronller {

	@Autowired
	private DepartmentService service;
	
	@ResponseBody
	@RequestMapping("/depts")
	public Msg getDepartmentAll() {
		
		return Msg.success().add("data", service.getDepartmentAll());
	}
	
}
