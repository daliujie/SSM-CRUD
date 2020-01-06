package com.jason.crud.test;

import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jason.crud.bean.Department;
import com.jason.crud.bean.Employee;
import com.jason.crud.dao.DepartmentMapper;
import com.jason.crud.dao.EmployeeMapper;

/**
 * 测试dao层的工作
 * @author jasonr
 * 推荐spring的项目使用spring的单元测试，可以自动注入我们需要的组件
 * 	导入springTest模块
 * 	指定spring配置文件的位置
 * 	直接autowired我们要使用的对象即可
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MapperTest {
	@Autowired
	DepartmentMapper departmentMapper;
	@Autowired
	EmployeeMapper employeeMapper;
	@Autowired
	SqlSession sqlSession;
	/**
	 * 测试DepartmentMapper.xml
	 */
	@Test
	public void testCRUD() {
//		//1、创建springIOC容器
//		ApplicationContext ioc = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//		//2、从容器中获取mapper
//		DepartmentMapper departmentMapper =ioc.getBean(DepartmentMapper.class);
		
//		1、插入几个部门
//		departmentMapper.insertSelective(new Department(null, "大数据研发组"));
//		departmentMapper.insertSelective(new Department(null, "数字化研发组"));
//		departmentMapper.insertSelective(new  Department(null, "数字化测试组"));
//		departmentMapper.insertSelective(new Department(null, "大数据测试组"));
		
		//2.生成员工数据
//		employeeMapper.insertSelective(new Employee(null,"jason","F","jason@qq.com",10018));
		//3.批量插入员工数据:批量  使用批量操作的sqlsession
		EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
		for(int i = 0; i < 1000;i ++) {
			String str = UUID.randomUUID().toString().substring(0,5);
			mapper.insert(new Employee(null,str,"M",str+"@qq.com",10018));
		}
		
	}

}
