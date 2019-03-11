package GUI;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import model.User;
import dao.IUserDao;
import dao.UserDaoJDBC;

/*
 * 用户信息表格
 * */
public class UserTable extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private IUserDao userDao;
	private Vector<String> columnNames;
	private Vector<Vector<String>> rowData;
	
	public Vector<Vector<String>> getRowData() {
		return rowData;
	}

	public void setRowData(Vector<Vector<String>> rowData) {
		this.rowData = rowData;
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDaoJDBC userDao) {
		this.userDao = userDao;
	}

	public UserTable() {
		userDao = new UserDaoJDBC();
		initData();
	}
	/*
	 * 表格添加数据类型
	 * */
	public void initData() {
		columnNames = new Vector<String>();
		columnNames.add("用户昵称");
		columnNames.add("用户名");
		columnNames.add("用户密码");
		columnNames.add("用户身份");
		List<User> users = userDao.list();
		Vector<String> ue = null;
		rowData = new Vector<Vector<String>>();
		for(User u:users) {
			ue = new Vector<String>();
			ue.add(u.getNickname());
			ue.add(u.getUsername());
			ue.add(u.getPassword());		
			if("0".equals(u.getPass())){
				ue.add("管理员");
			}else{
				ue.add("普通用户");
			}
			
			rowData.add(ue);
		}
	}

	@Override
	public int getColumnCount() {
		return columnNames.size();
	}

	@Override
	public int getRowCount() {
		return rowData.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return rowData.get(rowIndex).get(columnIndex);
	}

	@Override
	public String getColumnName(int column) {
		return columnNames.get(column);
	}
	
	
}
