package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.HR;
import Util.SqlDB;

public class HRDaoJDBC implements IHRDao {
	/*
	 * 人力资源信息增加
	 * */
	@Override
	public void add(HR hp, int wpId) {
		try {
			String sql = "insert into t_hr values (null,'"+ hp.getName() + "','" + hp.getSex() + "','" + hp.getSalary() + "','" + wpId + "','" + hp.getGraduated() + "','" + hp.getPhone() + "','" + hp.getIntroduce() + "')";
			SqlDB.execUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlDB.closeDB();
		}
	}

	/*
	 * 人力资源信息删除
	 * */
	@Override
	public void delete(int id) {
		try {
			String sql = "delete from t_hr where id=" + id;
			SqlDB.execUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlDB.closeDB();
		}

	}
	
	/*
	 * 人力资源信息加载
	 * */
	@Override
	public HR load(int id) {
		ResultSet rs = null;
		HR hr = null;
		try {
			String sql = "select * from t_hr where id=" + id;
			rs = SqlDB.execQuery(sql);
			while(rs.next()) {
				hr = new HR();
				hr.setId(rs.getInt("id"));
				hr.setWpId(rs.getInt("wp_id"));
				hr.setName(rs.getString("name"));
				hr.setSalary(rs.getDouble("salary"));
				hr.setSex(rs.getString("sex"));
				hr.setGraduated(rs.getString("graduated"));
				hr.setPhone(rs.getString("phone"));
				hr.setIntroduce(rs.getString("introduce"));
				//System.out.println(hr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlDB.closeDB();
		}
		return hr;
	}
	
	/*
	 * 人力资源信息更新
	 * */
	@Override
	public void update(HR hr, int wpId) {
		try {
			//System.out.println(hr.getId());
			String sql = "update t_hr set name='" + hr.getName() + "',sex='" + hr.getSex() + "',salary=" + hr.getSalary() + ",wp_id=" + wpId  + ",graduated='" + hr.getGraduated() + "',phone='" + hr.getPhone() + "',introduce='" + hr.getIntroduce() + "' where id=" + hr.getId();			
			System.out.println(sql);
			SqlDB.execUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlDB.closeDB();
		}
	}

	/*
	 * 人力资源信息筛选所有
	 * */
	@Override
	public List<HR> list() {
		ResultSet rs = null;
		List<HR> hrs = new ArrayList<HR>();
		try {
			String sql = "select * from t_hr";
			rs = SqlDB.execQuery(sql);
			HR hr = null;
			while(rs.next()) {
				hr = new HR();
				hr.setWpId(rs.getInt("wp_id"));
				hr.setId(rs.getInt("id"));
				hr.setName(rs.getString("name"));
				hr.setSalary(rs.getDouble("salary"));
				hr.setSex(rs.getString("sex"));
				hr.setGraduated(rs.getString("graduated"));
				hr.setPhone(rs.getString("phone"));
				hr.setIntroduce(rs.getString("introduce"));
				hrs.add(hr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlDB.closeDB();
		}
		return hrs;
	}
	
	/*
	 * 人力资源信息通过职场id和名称模糊筛选所有符合条件的人力资源
	 * */
	@Override
	public List<HR> list(int wpId, String name) {
		ResultSet rs = null;
		List<HR> hrs = new ArrayList<HR>();
		try {
			String sql = "select * from t_hr where 1=1";
			if(wpId>0) {
				sql+=" and wp_id="+wpId;
			}
			if(name!=null&&!"".equals(name.trim())) {
				sql+= " and name like '%"+name+"%'";
			}
			rs = SqlDB.execQuery(sql);
			HR hr = null;
			while(rs.next()) {
				hr = new HR();
				hr.setWpId(rs.getInt("wp_id"));
				hr.setId(rs.getInt("id"));
				hr.setName(rs.getString("name"));
				hr.setSalary(rs.getDouble("salary"));
				hr.setSex(rs.getString("sex"));
				hr.setGraduated(rs.getString("graduated"));
				hr.setPhone(rs.getString("phone"));
				hr.setIntroduce(rs.getString("introduce"));
				hrs.add(hr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlDB.closeDB();
		}
		return hrs;
	}

	
}
