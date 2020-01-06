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
 * ����dao��Ĺ���
 * @author jasonr
 * �Ƽ�spring����Ŀʹ��spring�ĵ�Ԫ���ԣ������Զ�ע��������Ҫ�����
 * 	����springTestģ��
 * 	ָ��spring�����ļ���λ��
 * 	ֱ��autowired����Ҫʹ�õĶ��󼴿�
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
	 * ����DepartmentMapper.xml
	 */
	@Test
	public void testCRUD() {
//		//1������springIOC����
//		ApplicationContext ioc = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//		//2���������л�ȡmapper
//		DepartmentMapper departmentMapper =ioc.getBean(DepartmentMapper.class);
		
//		1�����뼸������
//		departmentMapper.insertSelective(new Department(null, "�������з���"));
//		departmentMapper.insertSelective(new Department(null, "���ֻ��з���"));
//		departmentMapper.insertSelective(new  Department(null, "���ֻ�������"));
//		departmentMapper.insertSelective(new Department(null, "�����ݲ�����"));
		
		//2.����Ա������
//		employeeMapper.insertSelective(new Employee(null,"jason","F","jason@qq.com",10018));
		//3.��������Ա������:����  ʹ������������sqlsession
		EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
		for(int i = 0; i < 1000;i ++) {
			String str = UUID.randomUUID().toString().substring(0,5);
			mapper.insert(new Employee(null,str,"M",str+"@qq.com",10018));
		}
		
	}

}
