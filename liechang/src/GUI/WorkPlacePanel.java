package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import model.LieChangException;
import model.WorkPlace;
import Util.LieChangUtil;
/*
 * 职场信息管理页面
 * */
public class WorkPlacePanel extends JPanel{
private static final long serialVersionUID = 1L;
	
	private JPanel jp1,jp2;
	private JLabel jl1;
	private JTable jt;
	private JScrollPane jsp;
	private JButton jb1,jb2,jb3;
	private WorkPlaceTable wm;
	private AdminAllFrame jf;
	private AddDialog ad;
	private UpdateDialog ud;
	
	
	public JTable getJt() {
		return jt;
	}

	public void setJt(JTable jt) {
		this.jt = jt;
	}

	public WorkPlaceTable getWm() {
		return wm;
	}

	public void setWm(WorkPlaceTable wm) {
		this.wm = wm;
	}
	
	/*
	 * 职场信息管理界面
	 * */
	public WorkPlacePanel(AdminAllFrame jf) {
		this.setLayout(new BorderLayout());
		this.jf = jf;
		jp1 = new JPanel();
		jp2 = new JPanel();
		jl1 = new JLabel("职场管理界面");
		jb1 = new JButton("添加职场");
		jb2 = new JButton("删除职场");
		jb3 = new JButton("修改职场");
		jp1.add(jl1);
		jp2.add(jb1); jp2.add(jb2); jp2.add(jb3);
		jb1.addActionListener(new WpClick());
		jb2.addActionListener(new WpClick());
		jb3.addActionListener(new WpClick());
		wm = new WorkPlaceTable();
		jt = new JTable(wm);
		jsp = new JScrollPane(jt);
		ad = new AddDialog();
		ud = new UpdateDialog();
		this.add(jsp);
		this.add(jp1,BorderLayout.NORTH);
		this.add(jp2,BorderLayout.SOUTH);
	}
	/*
	 * 职场信息重载
	 * */
	public void refreshData(boolean refreshEmp) {
		wm.initData();
		jt.updateUI();
		if(refreshEmp) {
			jf.getHp().refreshData(false);
			
		}
	}
	/*
	 * 职场信息管理删除
	 * */
	private void deleteWp() {
		try {
			int row = jt.getSelectedRow();
			if(row<0) {
				LieChangUtil.showError(jp1, "需要选择要删除的职场");
				return;
			}
			int wpId = Integer.parseInt(wm.getRowData().get(row).get(0));
			wm.getWpDao().delete(wpId);
			refreshData(true);
		} catch (LieChangException e) {
			LieChangUtil.showError(jp1, e.getMessage());
		}
	}
	/*
	 * 职场信息管理更新
	 * */
	private void updateWp() {
		int row = jt.getSelectedRow();
		if(row<0) {
			LieChangUtil.showError(jp1, "需要选择要更新的职场");
			return;
		}
		int wpId = Integer.parseInt(wm.getRowData().get(row).get(0));
		WorkPlace wp = wm.getWpDao().load(wpId);
		ud.show(wp);
	}
	
	/*
	 * 职场信息管理监听事件
	 * */
	private class WpClick implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==jb1) {
				//添加
				ad.setVisible(true);
			} else if(e.getSource()==jb2) {
				//删除
				deleteWp();
			} else if(e.getSource()==jb3) {
				//修改
				updateWp();
			}
		}
	}
	/*
	 * 职场信息管理更新界面及操作
	 * */
	@SuppressWarnings("serial")
	private class UpdateDialog extends JDialog {
		private JPanel jp;
		private JLabel jl;
		private JTextField jtf;
		private JButton jb1,jb2;
		private WorkPlace wp;
		
		private void reset() {
			jtf.setText("");
		}
		
		private void show(WorkPlace wp) {
			this.wp = wp;
			jtf.setText(wp.getName());
			jtf.setSelectionStart(0);
			jtf.setSelectionEnd(wp.getName().length());
			this.setVisible(true);
		}
		public UpdateDialog() {
			ImageIcon i = new ImageIcon(this.getClass().getResource("/img/tubiao.jpg"));
	        this.setIconImage(i.getImage());
			this.setSize(300,150);
			this.setTitle("更新职场");
			this.setLocation(700, 320);
			this.setModal(true);
			jp = new JPanel();
			jl = new JLabel("职场名称:");
			jtf = new JTextField(20);
			jb1 = new JButton("更新职场");
			jb2 = new JButton("重置");
			jb1.addActionListener(new UpdateClick());
			jb2.addActionListener(new UpdateClick());
			jp.add(jl); jp.add(jtf); jp.add(jb1); jp.add(jb2);
			this.add(jp);
		}
		private class UpdateClick implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==jb1) {
					String name = jtf.getText();
					if(name==null||"".equals(name.trim())){ 
						LieChangUtil.showError(jp, "职场名称不能为空");
						return;
					}
					wp.setName(name);
					wm.getWpDao().update(wp);
					reset();
					refreshData(true);
					ud.setVisible(false);
				} else if(e.getSource()==jb2) {
					jtf.setText(wp.getName());
				}
			}
		}
	}
	/*
	 * 职场信息管理增加界面及操作
	 * */
	@SuppressWarnings("serial")
	private class AddDialog extends JDialog {
		private JPanel jp;
		private JLabel jl;
		private JTextField jtf;
		private JButton jb;
		
		private void reset() {
			jtf.setText("");
		}
		public AddDialog() {
			ImageIcon i = new ImageIcon(this.getClass().getResource("/img/tubiao.jpg"));
	        this.setIconImage(i.getImage());
			this.setSize(300,150);
			this.setTitle("添加职场");
			this.setLocation(700, 320);
			this.setModal(true);
			jp = new JPanel();
			jl = new JLabel("职场名称:");
			jtf = new JTextField(20);
			jb = new JButton("添加职场");
			jb.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(e.getSource()==jb) {
						String name = jtf.getText();
						if(name==null||"".equals(name.trim())){ 
							LieChangUtil.showError(jp, "职场名称不能为空");
							return;
						}
						WorkPlace w = new WorkPlace();
						w.setName(name);
						wm.getWpDao().add(w);
						reset();
						refreshData(true);
						ad.setVisible(false);
					}
				}
			});
			jp.add(jl); jp.add(jtf); jp.add(jb);
			this.add(jp);
		}
	}
}
