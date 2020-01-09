package com.jason.crud.bean;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.EAN;
/**
 * 此处我们不使用jsr303校验，使用代码校验
 * @author Administrator
 *
 */

public class Employee {
    private Integer empId;

    /**
     * 自定义验证规则
     * 详情可以看jsr303文档
     */
//    @Pattern(regexp = "",message = "")
    private String empName;

    private String gender;
    
    /**
     * 加上此注释之后可以进行校验，只需要在封装的地方加上 @validate就可以了
     */
    @Email
    private String email;

    private Integer dId;
    
    //保存部门信息
    private Department department;
    
    public Employee() {
		super();
	}

	public Employee(Integer empId, String empName, String gender, String email, Integer dId) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.gender = gender;
		this.email = email;
		this.dId = dId;
	}

    public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getdId() {
        return dId;
    }

    public void setdId(Integer dId) {
        this.dId = dId;
    }
}