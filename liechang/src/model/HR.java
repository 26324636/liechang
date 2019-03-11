package model;
/*
 * 人力资源方法
 * */
public class HR {
	private int id;
	private String name;
	private String sex;
	private double salary;
	private int wpId;
	private String graduated;
	private String phone;
	private String introduce;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public int getWpId() {
		return wpId;
	}
	public void setWpId(int wpId) {
		this.wpId = wpId;
	}	
	public String getGraduated() {
		return graduated;
	}
	public void setGraduated(String graduated) {
		this.graduated = graduated;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	
	@Override
	public String toString() {
		return "HR [id=" + id + ", name=" + name + ", sex=" + sex + ", salary="
				+ salary + ", wpId=" + wpId + ", graduated=" + graduated
				+ ", phone=" + phone + ", introduce=" + introduce + "]";
	}

	
}
