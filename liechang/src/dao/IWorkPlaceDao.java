package dao;


import java.util.List;

import model.WorkPlace;

/*
 * 职场信息接口
 * */
public interface IWorkPlaceDao {
	public void add(WorkPlace wp);
	public void delete(int id);
	public void update(WorkPlace wp);
	public WorkPlace load(int id);
	public List<WorkPlace> list();
	public int getWpHRNums(int WpId);
}
