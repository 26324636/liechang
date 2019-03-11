package model;

/*
 * 职场方法
 * */
public class WorkPlace {
	/**
	 * id是一个唯一标识，并且是自动递增的
	 */
	private int id;
	private String name;
	
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
	@Override
	public String toString() {
		return name+"("+id+")";
	}
	
	
}
