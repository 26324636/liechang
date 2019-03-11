package dao;

import java.util.List;

import model.HR;

/*
 * 人力资源信息接口
 * */
public interface IHRDao {
		public void add(HR hp, int wpId);
		public void delete(int id);
		public void update(HR hp, int wpId);
		public HR load(int id);
		public List<HR> list();
		public List<HR> list(int wpId, String name);
}
