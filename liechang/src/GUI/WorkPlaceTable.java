package GUI;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import model.WorkPlace;
import dao.IWorkPlaceDao;
import dao.WorkPlaceDaoJDBC;
/*
 * 职场信息表格
 * */
public class WorkPlaceTable extends AbstractTableModel  {
	private static final long serialVersionUID = 1L;
	private IWorkPlaceDao wpDao;
	private Vector<String> columnNames;
	private Vector<Vector<String>> rowData;
	
	public IWorkPlaceDao getWpDao() {
		return wpDao;
	}

	public void setDepDao(IWorkPlaceDao wpDao) {
		this.wpDao = wpDao;
	}

	public Vector<Vector<String>> getRowData() {
		return rowData;
	}

	public void setRowData(Vector<Vector<String>> rowData) {
		this.rowData = rowData;
	}


	public WorkPlaceTable() {
		wpDao = new WorkPlaceDaoJDBC();
		initData();
	}
	/*
	 * 表格添加数据类型
	 * */
	public void initData() {
		columnNames = new Vector<String>();
		columnNames.add("职场标识");
		columnNames.add("职场名称");
		columnNames.add("职场人数");
		List<WorkPlace> wps = wpDao.list();
		Vector<String> vw = null;
		rowData = new Vector<Vector<String>>();
		for(WorkPlace wp:wps) {
			vw = new Vector<String>();
			vw.add(String.valueOf(wp.getId()));
			vw.add(wp.getName());
			vw.add(String.valueOf(wpDao.getWpHRNums(wp.getId())));
			rowData.add(vw);
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
