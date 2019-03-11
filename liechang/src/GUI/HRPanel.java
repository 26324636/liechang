package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import model.HR;
import model.LieChangException;
import model.WorkPlace;
import Util.LieChangUtil;

public class HRPanel extends JPanel{

private static final long serialVersionUID = 1L;
	
	private JPanel jp1,jp2;
	private JLabel jl1,jl2,jl3;
	private JTable jt;
	private JScrollPane jsp;
	private JButton jb1,jb2,jb3,jb4,jb5;
	private HRTable hm;
	private AdminAllFrame af;
	private UserAllFrame uf;
	private AddDialog ad;
	private UpdateDialog ud;
	private SeeDialog sd;
	private JComboBox jcb;
	private DefaultComboBoxModel dcbm;
	private JTextField jtf;
	
	/*
	 * 管理员人力资源信息界面
	 * */
	public HRPanel(AdminAllFrame af) {
		this.af = af;
		this.setLayout(new BorderLayout());
		hm = new HRTable();
		jt = new JTable(hm);
		jsp = new JScrollPane(jt);
		ad = new AddDialog();
		ud = new UpdateDialog();
		sd = new SeeDialog();
		jp1 = new JPanel();
		jp2 = new JPanel();
		jl1 = new JLabel("人力资源管理界面");
		jl2 = new JLabel("选择筛选职场:");
		jl3 = new JLabel("输入筛选姓名");
		jb1 = new JButton("添加人员");
		jb2 = new JButton("删除人员");
		jb3 = new JButton("修改人员");
		jb4 = new JButton("筛选");
		jb5 = new JButton("查看人员");
		jb1.addActionListener(new HRClick());
		jb2.addActionListener(new HRClick());
		jb3.addActionListener(new HRClick());
		jb4.addActionListener(new HRClick());
		jb5.addActionListener(new HRClick());
		jp1.add(jl1);
		jp1.add(jl2);
		jcb = new JComboBox();
		initWp();
		jp1.add(jcb);
		jp1.add(jl3);
		jtf = new JTextField(15);
		jp1.add(jtf);
		jp1.add(jb4);
		jp2.add(jb1); jp2.add(jb2); jp2.add(jb3);jp2.add(jb5);
		this.add(jsp);
		this.add(jp1,BorderLayout.NORTH);
		this.add(jp2,BorderLayout.SOUTH);
	}
	
	/*
	 * 普通用户人力资源信息界面
	 * */
	public HRPanel(UserAllFrame uf) {
		this.uf = uf;
		this.setLayout(new BorderLayout());
		hm = new HRTable();
		jt = new JTable(hm);
		jsp = new JScrollPane(jt);
		sd = new SeeDialog();
		jp1 = new JPanel();
		jp2 = new JPanel();
		jl1 = new JLabel("人力资源管理界面");
		jl2 = new JLabel("选择筛选职场:");
		jl3 = new JLabel("输入筛选姓名");
		jb5 = new JButton("查看人员");
		jb4 = new JButton("筛选");
		jb4.addActionListener(new HRClick());
		jb5.addActionListener(new HRClick());
		jp1.add(jl1);
		jp1.add(jl2);
		jcb = new JComboBox();
		initWp();
		jp1.add(jcb);
		jp1.add(jl3);
		jtf = new JTextField(15);
		jp1.add(jtf);
		jp1.add(jb4);
		jp2.add(jb5);
		this.add(jsp);
		this.add(jp1,BorderLayout.NORTH);
		this.add(jp2,BorderLayout.SOUTH);
	}
	
	/*
	 * 为ComboBox添加数据
	 * */
	private void initWp() {
		List<WorkPlace> allDep = hm.getWpDao().list();
		Vector<WorkPlace> vd = new Vector<WorkPlace>();
		WorkPlace wp = new WorkPlace();
		wp.setId(0);
		wp.setName("全部数据");
		vd.add(wp);
		vd.addAll(allDep);
		dcbm = new DefaultComboBoxModel(vd);
		jcb.setModel(dcbm);
	}
	
	/*
	 * 人力资源信息删除
	 * */
	private void deleteHR() {
		try {
			int confirm = JOptionPane.showConfirmDialog(jp1, "该操作不可逆，确定删除吗?");
			if(confirm==JOptionPane.YES_OPTION) {
				int row = jt.getSelectedRow();
				if(row<0) {
					LieChangUtil.showError(jp1, "需要选择要删除的人员");
					return;
				}
				int hrId = Integer.parseInt(hm.getRowData().get(row).get(0));
				hm.getHRDao().delete(hrId);
				refreshData(true);
			}
		} catch (LieChangException e) {
			LieChangUtil.showError(jp1, e.getMessage());
		}
	}
	
	/*
	 * 人力资源信息更新
	 * */
	private void updateHR() {
		int row = jt.getSelectedRow();
		if(row<0) {
			LieChangUtil.showError(jp1, "需要选择要更新的人员");
			return;
		}
		int hrId = Integer.parseInt(hm.getRowData().get(row).get(0));
		HR hr = hm.getHRDao().load(hrId);
		//System.out.println(hr);
		ud.show(hr);
	}
	/*
	 * 人力资源信息查看
	 * */
	private void seeHR(){
		int row = jt.getSelectedRow();
		if(row<0) {
			LieChangUtil.showError(jp1, "需要选择要更新的人员");
			return;
		}
		int hrId = Integer.parseInt(hm.getRowData().get(row).get(0));
		HR hr = hm.getHRDao().load(hrId);
		//System.out.println(hr);
		sd.showmess(hr);
		
	}
	/*
	 * 人力资源信息筛选
	 * */
	private void filter() {
		int wpId = ((WorkPlace)jcb.getSelectedItem()).getId();
		String name = jtf.getText();
		hm.initData(wpId, name);
		jt.updateUI();
	}
	/*
	 * 人力资源信息事件监听
	 * */
	private class HRClick implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==jb1) {
				//添加
				ad.refreshWp();
				ad.setVisible(true);
			} else if(e.getSource()==jb2) {
				//删除
				deleteHR();
			} else if(e.getSource()==jb3) {
				//更新
				updateHR();
			} else if(e.getSource()==jb4) {
				//筛选
				filter();
			}else if(e.getSource() == jb5){
				//查看
				seeHR();
			}
		}

		
	}
	/*
	 * 人力资源信息数据重载
	 * */
	public void refreshData(boolean refreshWp) {
		hm.initData();
		jt.updateUI();
		initWp();
		ud.refreshWp();
		if(refreshWp)
			af.getWp().refreshData(false);
	}
	
	public UserAllFrame getUf() {
		return uf;
	}

	public void setUf(UserAllFrame uf) {
		this.uf = uf;
	}

	/*
	 * 人力资源信息更新界面及操作
	 * */
	@SuppressWarnings("serial")
	private class UpdateDialog extends JDialog {
		private JPanel jp1,jp2,jp3,jp4,jp5,jp6,jp7,jp8,jp9;
		private JLabel jl1,jl2,jl3,jl4,jl5,jl6,jl7,jl8;
		private JTextField jtf1,jtf2,jtf3,jtf4,jtf5,jtf6;
		private JComboBox jcb;
		private DefaultComboBoxModel lm;
		private JRadioButton jrb1,jrb2;
		private ButtonGroup bg;
		private JButton jb1,jb2;
		private HR hr;
		
		public void refreshWp() {
			List<WorkPlace> allWp = hm.getWpDao().list();
			Vector<WorkPlace> vw = new Vector<WorkPlace>(allWp);
			lm = new DefaultComboBoxModel(vw);
			jcb.setModel(lm);
		}
		
		public UpdateDialog() {
			ImageIcon i = new ImageIcon(this.getClass().getResource("/img/tubiao.jpg"));
	        this.setIconImage(i.getImage());
			this.setSize(350, 510);
			this.setModal(true);
			this.setTitle("修改人员");
			this.setLocation(700, 320);
			jp1 = new JPanel();
			jp2 = new JPanel();
			jp3 = new JPanel();
			jp4 = new JPanel();
			jp5 = new JPanel();
			jp6 = new JPanel();
			jp7 = new JPanel();
			jp8 = new JPanel();
			jp9 = new JPanel();
			jl1 = new JLabel(" 标  识 :");
			jl2 = new JLabel("人员名称:");
			jl3 = new JLabel("人员性别:");
			jl4 = new JLabel("期待薪资:");
			jl5 = new JLabel("所在职场:");
			jl6 = new JLabel("毕业院校:");
			jl7 = new JLabel("联系方式:");
			jl8 = new JLabel("简要备注:");
			jtf1 = new JTextField(20);
			jtf2 = new JTextField(20);
			jtf3 = new JTextField(20);
			jtf4 = new JTextField(20);
			jtf5 = new JTextField(20);
			jtf6 = new JTextField(20);
			jcb = new JComboBox();
			refreshWp();
			jcb.setModel(lm);
			jrb1 = new JRadioButton("男",true); jrb2 = new JRadioButton("女");
			bg = new ButtonGroup();
			bg.add(jrb1); bg.add(jrb2);
			jb1 = new JButton("修改人员");
			jb2 = new JButton("重置数据");
			this.setLayout(new GridLayout(9,1));
			jp1.add(jl1); jp1.add(jtf1);
			jp2.add(jl2); jp2.add(jtf2);
			jp3.add(jl3); jp3.add(jrb1); jp3.add(jrb2);
			jp4.add(jl4); jp4.add(jtf3);
			jp5.add(jl5); jp5.add(jcb);
			jp6.add(jl6); jp6.add(jtf4);
			jp7.add(jl7); jp7.add(jtf5);
			jp8.add(jl8); jp8.add(jtf6);
			jp9.add(jb1); jp9.add(jb2);
			jtf1.setEditable(false);
			jb1.addActionListener(new UpdateClick());
			jb2.addActionListener(new UpdateClick());
			
			this.add(jp1); this.add(jp2); 
			this.add(jp3); this.add(jp4); 
			this.add(jp5);this.add(jp6);
			this.add(jp7);this.add(jp8);
			this.add(jp9);
		}
		
		public void show(HR hr) {
			this.hr = hr;
			reset();
			this.setVisible(true);
		}
		
		private class UpdateClick implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==jb1) {
					//添加
					String Id = jtf1.getText();
					String name = jtf2.getText();
					if(name==null||"".equals(name.trim())) {
						LieChangUtil.showError(jp1, "人员名称不能为空");
						return;
					}
					String salary = jtf3.getText();
					String reg = "\\d+\\.?+\\d+";
					if(!salary.matches(reg)) {
						LieChangUtil.showError(jp1, "期待薪资必须是数字类型");
						return;
					}
					int wpId = ((WorkPlace)jcb.getSelectedItem()).getId();
					String graduated = jtf4.getText();
					if(graduated==null||"".equals(graduated.trim())){
						LieChangUtil.showError(jp1, "毕业院校不能为空");
						return;
					}
					String phone = jtf5.getText();
					if(phone==null||"".equals(phone.trim())){
						LieChangUtil.showError(jp1, "联系方式不能为空");
						return;
					}
					if(phone.length()!=11){
						LieChangUtil.showError(jp1, "请输入11位的联系方式");
						return;
					}
					String introduce = jtf6.getText();
					if(introduce==null || "".equals(introduce.trim())){
						LieChangUtil.showError(jp1, "简要备注不能为空");
						return;
					}
					HR hr = new HR();
					hr.setId(Integer.valueOf(Id));
					String sex = "男";
					if(jrb2.isSelected()) sex = "女";
					hr.setName(name);
					hr.setSalary(Double.valueOf(salary));
					hr.setSex(sex);
					hr.setGraduated(graduated);
					hr.setPhone(phone);
					hr.setIntroduce(introduce);
					//System.out.println(hr);
					hm.getHRDao().update(hr, wpId);
					refreshData(true);
					reset();
					ud.setVisible(false);
				} else if(e.getSource()==jb2) {
					//重置
					reset();
				}
			}
		}
		
		private void reset() {
			int i;
			jtf1.setText(String.valueOf(hr.getId()) );
			jtf2.setText(hr.getName());
			jtf3.setText(hr.getSalary()+"");
			jtf4.setText(hr.getGraduated());
			jtf5.setText(hr.getPhone());
			jtf6.setText(hr.getIntroduce());
			if(hr.getSex().equals("男"))
				jrb1.setSelected(true);
			else
				jrb2.setSelected(true);
			WorkPlace wp = hm.getWpDao().load(hr.getWpId());
			//System.out.println(wp);
			
			String item = String.valueOf(wp);
			//System.out.println(item);
			for(i = 0 ; i < jcb.getItemCount();i++){
				if(item.equals(jcb.getItemAt(i).toString())){
					//System.out.println(i);
					break;
				}
			}
			jcb.setSelectedIndex(i);
			//System.out.println(jcb.getSelectedItem());
		}
	}
	
	/*
	 * 人力资源信息查看界面及操作
	 * */
	@SuppressWarnings("serial")
	private class SeeDialog extends JDialog {
		private JPanel jp1,jp2,jp3,jp4,jp5,jp6,jp7,jp8;
		private JLabel jl1,jl2,jl3,jl4,jl5,jl6,jl7,jl8;
		private JTextField jtf1,jtf2,jtf3,jtf4,jtf5,jtf6,jtf7,jtf8;
		
		public SeeDialog() {
			ImageIcon i = new ImageIcon(this.getClass().getResource("/img/tubiao.jpg"));
	        this.setIconImage(i.getImage());
			this.setSize(350, 510);
			this.setModal(true);
			this.setTitle("查看人员");
			this.setLocation(700, 320);
			jp1 = new JPanel();
			jp2 = new JPanel();
			jp3 = new JPanel();
			jp4 = new JPanel();
			jp5 = new JPanel();
			jp6 = new JPanel();
			jp7 = new JPanel();
			jp8 = new JPanel();
			jl1 = new JLabel(" 标  识 :");
			jl2 = new JLabel("人员名称:");
			jl3 = new JLabel("人员性别:");
			jl4 = new JLabel("期待薪资:");
			jl5 = new JLabel("所在职场:");
			jl6 = new JLabel("毕业院校:");
			jl7 = new JLabel("联系方式:");
			jl8 = new JLabel("简要备注:");
			jtf1 = new JTextField(20);
			jtf2 = new JTextField(20);
			jtf3 = new JTextField(20);
			jtf4 = new JTextField(20);
			jtf5 = new JTextField(20);
			jtf6 = new JTextField(20);
			jtf7 = new JTextField(20);
			jtf8 = new JTextField(20);
			this.setLayout(new GridLayout(8,1));
			jp1.add(jl1); jp1.add(jtf1);
			jp2.add(jl2); jp2.add(jtf2);
			jp3.add(jl3); jp3.add(jtf3);
			jp4.add(jl4); jp4.add(jtf4);
			jp5.add(jl5); jp5.add(jtf5);
			jp6.add(jl6); jp6.add(jtf6);
			jp7.add(jl7); jp7.add(jtf7);
			jp8.add(jl8); jp8.add(jtf8);
			jtf1.setEditable(false);
			jtf2.setEditable(false);
			jtf3.setEditable(false);
			jtf4.setEditable(false);
			jtf5.setEditable(false);
			jtf6.setEditable(false);
			jtf7.setEditable(false);
			jtf8.setEditable(false);
			this.add(jp1); this.add(jp2); 
			this.add(jp3); this.add(jp4); 
			this.add(jp5); this.add(jp6);
			this.add(jp7); this.add(jp8);
		}
		
		public void showmess(HR hr) {
			jtf1.setText(String.valueOf(hr.getId()) );
			jtf2.setText(hr.getName());
			jtf3.setText(hr.getSex());
			jtf4.setText(hr.getSalary()+"");
			WorkPlace wp = hm.getWpDao().load(hr.getWpId());
			jtf5.setText(String.valueOf(wp));
			jtf6.setText(hr.getGraduated());
			jtf7.setText(hr.getPhone());
			jtf8.setText(hr.getIntroduce());
			this.setVisible(true);
		}
	}
	
	/*
	 * 人力资源信息增加界面及操作
	 * */
	@SuppressWarnings("serial")
	private class AddDialog extends JDialog {
		private JPanel jp1,jp2,jp3,jp4,jp5,jp6,jp7,jp8;
		private JLabel jl1,jl2,jl3,jl4,jl5,jl6,jl7;
		private JTextField jtf1,jtf2,jtf3,jtf4,jtf5;
		private JComboBox jcb;
		private DefaultComboBoxModel lm;
		private JRadioButton jrb1,jrb2;
		private ButtonGroup bg;
		private JButton jb1,jb2;
		
		public void refreshWp() {
			List<WorkPlace> allWp = hm.getWpDao().list();
			Vector<WorkPlace> vw = new Vector<WorkPlace>(allWp);
			lm = new DefaultComboBoxModel(vw);			
			jcb.setModel(lm);
		}
		
		public AddDialog() {
			ImageIcon i = new ImageIcon(this.getClass().getResource("/img/tubiao.jpg"));
	        this.setIconImage(i.getImage());
			this.setSize(350, 410);
			this.setModal(true);
			this.setTitle("添加人员");
			this.setLocation(700, 320);
			jp1 = new JPanel();
			jp2 = new JPanel();
			jp3 = new JPanel();
			jp4 = new JPanel();
			jp5 = new JPanel();
			jp6 = new JPanel();
			jp7 = new JPanel();
			jp8 = new JPanel();
			jl1 = new JLabel("人员名称:");
			jl2 = new JLabel("人员性别:");
			jl3 = new JLabel("期待薪资:");
			jl4 = new JLabel("所在职场:");
			jl5 = new JLabel("毕业院校:");
			jl6 = new JLabel("联系方式:");
			jl7 = new JLabel("简要备注:");
			jtf1 = new JTextField(20);
			jtf2 = new JTextField(20);
			jtf3 = new JTextField(20);
			jtf4 = new JTextField(20);
			jtf5 = new JTextField(20);
			jcb = new JComboBox();
			refreshWp();
			jcb.setModel(lm);
			jrb1 = new JRadioButton("男",true); jrb2 = new JRadioButton("女");
			bg = new ButtonGroup();
			bg.add(jrb1); bg.add(jrb2);
			jb1 = new JButton("添加人员");
			jb2 = new JButton("重置数据");
			this.setLayout(new GridLayout(8,1));
			jp1.add(jl1); jp1.add(jtf1);
			jp2.add(jl2); jp2.add(jrb1); jp2.add(jrb2);
			jp3.add(jl3); jp3.add(jtf2);
			jp4.add(jl4); jp4.add(jcb);
			jp5.add(jl5); jp5.add(jtf3);
			jp6.add(jl6); jp6.add(jtf4);
			jp7.add(jl7); jp7.add(jtf5);
			jp8.add(jb1); jp8.add(jb2);
			jb1.addActionListener(new AddClick());
			jb2.addActionListener(new AddClick());

			this.add(jp1); this.add(jp2); 
			this.add(jp3); this.add(jp4); 
			this.add(jp5); this.add(jp6); 
			this.add(jp7); this.add(jp8); 
		}
		
		private class AddClick implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==jb1) {
					//添加
					String name = jtf1.getText();
					if(name==null||"".equals(name.trim())) {
						LieChangUtil.showError(jp1, "人员名称不能为空");
						return;
					}
					String salary = jtf2.getText();
					String reg = "\\d+\\.?+\\d+";
					if(!salary.matches(reg)) {
						LieChangUtil.showError(jp1, "期待薪资必须是数字类型");
						return;
					}
					
					int wpId = ((WorkPlace)jcb.getSelectedItem()).getId();
					String sex = "男";
					if(jrb2.isSelected()) sex = "女";
					
					String graduated = jtf3.getText();
					if(graduated==null||"".equals(graduated.trim())){
						LieChangUtil.showError(jp1, "毕业院校不能为空");
						return;
					}
					String phone = jtf4.getText();
					if(phone==null||"".equals(phone.trim())){
						LieChangUtil.showError(jp1, "联系方式不能为空");
						return;
					}
					if(phone.length()!=11){
						LieChangUtil.showError(jp1, "请输入11位的联系方式");
						return;
					}
					String introduce = jtf5.getText();
					if(introduce==null || "".equals(introduce.trim())){
						LieChangUtil.showError(jp1, "简要备注不能为空");
						return;
					}
					HR hr = new HR();
					hr.setName(name);
					hr.setSalary(Double.valueOf(salary));
					hr.setSex(sex);
					hr.setGraduated(graduated);
					hr.setPhone(phone);
					hr.setIntroduce(introduce);
					hm.getHRDao().add(hr, wpId);
					refreshData(true);
					reset();
					ad.setVisible(false);
				} else if(e.getSource()==jb2) {
					//重置
					reset();
				}
			}
		}
		
		private void reset() {
			jtf1.setText("");
			jtf2.setText("");
			jtf3.setText("");
			jtf4.setText("");
			jtf5.setText("");
			jrb1.setSelected(true);
			jcb.setSelectedIndex(0);
		}
	}
	
	
}
