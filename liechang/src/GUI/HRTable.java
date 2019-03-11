package GUI;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import model.HR;
import dao.HRDaoJDBC;
import dao.IHRDao;
import dao.IWorkPlaceDao;
import dao.WorkPlaceDaoJDBC;
/*
 * 人力资源信息表格的操作
 * */
public class HRTable extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	private IWorkPlaceDao wpDao;
	private IHRDao hrDao;
	private Vector<String> columnNames;
	private Vector<Vector<String>> rowData;
	
	public IHRDao getHRDao() {
		return hrDao;
	}

	public void setHRDao(IHRDao hrDao) {
		this.hrDao = hrDao;
	}

	public IWorkPlaceDao getWpDao() {
		return wpDao;
	}

	public void setWpDao(IWorkPlaceDao wpDao) {
		this.wpDao = wpDao;
	}

	public Vector<Vector<String>> getRowData() {
		return rowData;
	}

	public void setRowData(Vector<Vector<String>> rowData) {
		this.rowData = rowData;
	}


	public HRTable() {
		wpDao = new WorkPlaceDaoJDBC();
		hrDao = new HRDaoJDBC();
		initData();
	}
	
	/*
	 * 表格添加数据类型
	 * */
	public void initData() {
		columnNames = new Vector<String>();
		columnNames.add("标识");
		columnNames.add("姓名");
		columnNames.add("性别");
		columnNames.add("期待薪资");
		columnNames.add("所在职场");
		columnNames.add("毕业院校");
		columnNames.add("联系方式");
		columnNames.add("简要备注");
		List<HR> hrs = hrDao.list();
		Vector<String> ue = null;
		rowData = new Vector<Vector<String>>();
		for(HR hr:hrs) {
			ue = new Vector<String>();
			ue.add(String.valueOf(hr.getId()));
			ue.add(hr.getName());
			ue.add(hr.getSex());
			ue.add(String.valueOf(hr.getSalary()));
			ue.add(wpDao.load(hr.getWpId()).getName());
			ue.add(hr.getGraduated());
			ue.add(hr.getPhone());
			ue.add(hr.getIntroduce());
			rowData.add(ue);
		
		}
	}
	
	/*
	 * 通过职场和名字模糊搜索后为表格添加数据类型
	 * */
	public void initData(int wpId,String name) {
		columnNames = new Vector<String>();
		columnNames.add("标识");
		columnNames.add("姓名");
		columnNames.add("性别");
		columnNames.add("薪水");
		columnNames.add("所在职场");
		columnNames.add("毕业院校");
		columnNames.add("联系方式");
		columnNames.add("简要备注");
		List<HR> hrs = hrDao.list(wpId,name);
		Vector<String> ue = null;
		rowData = new Vector<Vector<String>>();
		for(HR hr:hrs) {
			ue = new Vector<String>();
			ue.add(String.valueOf(hr.getId()));
			ue.add(hr.getName());
			ue.add(hr.getSex());
			ue.add(String.valueOf(hr.getSalary()));
			ue.add(wpDao.load(hr.getWpId()).getName());
			ue.add(hr.getGraduated());
			ue.add(hr.getPhone());
			ue.add(hr.getIntroduce());
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
