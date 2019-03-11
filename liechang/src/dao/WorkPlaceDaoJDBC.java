package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.LieChangException;
import model.WorkPlace;
import Util.SqlDB;

public class WorkPlaceDaoJDBC implements IWorkPlaceDao {

	/*
	 * 职场信息增加
	 * */
	@Override
	public void add(WorkPlace wp) {
		try {
			String sql = "insert into t_wp values(null,'"+wp.getName()+"')";
			//System.out.println(sql);
			SqlDB.execUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlDB.closeDB();
		}
	}

	/*
	 * 职场信息删除
	 * */
	@Override
	public void delete(int id) {
		if(getWpHRNums(id)>0) throw new LieChangException("该职场还存在人员不能删除");
		try {
			String sql = "delete from t_wp where id=" + id;
			SqlDB.execUpdate(sql);
			//System.out.println(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlDB.closeDB();
		}
	}

	/*职场信息更新*/
	@Override
	public void update(WorkPlace wp) {
		try {
			String sql = "update t_wp set name='" + wp.getName() +"' where id=" + wp.getId();		
			SqlDB.execUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlDB.closeDB();
		}
	}

	/*职场信息查询所有*/
	@Override
	public List<WorkPlace> list() {
		ResultSet rs = null;
		List<WorkPlace> wps = new ArrayList<WorkPlace>();
		try {
		
			String sql = "select * from t_wp";
			
			rs = SqlDB.execQuery(sql);
			WorkPlace wp = null;
			while(rs.next()) {
				wp = new WorkPlace();
				wp.setId(rs.getInt("id"));
				wp.setName(rs.getString("name"));
				wps.add(wp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SqlDB.closeDB();
		}
		return wps;
	}

	/*
	 * 职场信息加载
	 * */
	@Override
	public WorkPlace load(int id) {
		ResultSet rs = null;
		WorkPlace wp = null;
		try {
			String sql = "select * from t_wp where id="+id;
			rs = SqlDB.execQuery(sql);
			while(rs.next()) {
				wp = new WorkPlace();
				wp.setId(rs.getInt("id"));
				wp.setName(rs.getString("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlDB.closeDB();
		}
		return wp;
	}

	/*
	 * 关联人力资源中用户所属职场
	 */
	@Override
	public int getWpHRNums(int wpId) {
		ResultSet rs = null;
		int count = 0;
		try {
			String sql = "select count(*) from t_hr where wp_id=" + wpId;
			rs = SqlDB.execQuery(sql);
			while(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlDB.closeDB();
		}
		return count;
	}

}
