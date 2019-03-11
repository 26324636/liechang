package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import model.LieChangException;
import model.User;
import Util.LieChangUtil;

/*
 * 用户信息管理页面
 * */
public class UserPanel extends JPanel{

private static final long serialVersionUID = 1L;
	
	private JPanel jp1,jp2;
	private JLabel jl1;
	private JTable jt;
	private JScrollPane jsp;
	private JButton jb1,jb2,jb3;
	private UserTable um;
	private AddDialog ad;
	private UpdateDialog ud;
	private JFrame jf;
	
	/*
	 * 用户信息管理界面
	 * */
	public UserPanel(JFrame jf) {
		this.setLayout(new BorderLayout());
		this.jf = jf;
		jp1 = new JPanel();
		jp2 = new JPanel();
		jl1 = new JLabel("用户管理界面");
		jb1 = new JButton("添加用户");
		jb2 = new JButton("删除用户");
		jb3 = new JButton("修改用户");
		jb1.addActionListener(new UserManagerClick());
		jb2.addActionListener(new UserManagerClick());
		jb3.addActionListener(new UserManagerClick());
		jp1.add(jl1);
		jp2.add(jb1); jp2.add(jb2); jp2.add(jb3);
		um = new UserTable();
		jt = new JTable(um);
		jsp = new JScrollPane(jt);
		ad = new AddDialog();
		ud = new UpdateDialog();
		this.add(jsp);
		this.add(jp1,BorderLayout.NORTH);
		this.add(jp2,BorderLayout.SOUTH);
	}
	/*
	 * 用户信息管理删除
	 * */
	private void deleteUser() {
		//1、获取选中的数据
		int row = jt.getSelectedRow();
		if(row<0) {
			LieChangUtil.showError(jp1, "必须选择要删除的用户");
			return;
		}
		String username = um.getRowData().get(row).get(1);
		um.getUserDao().delete(username);
		refreshData();
	}
	/*
	 * 用户信息管理更新
	 * */
	private void updateUser() {
		int row = jt.getSelectedRow();
		if(row<0) {
			LieChangUtil.showError(jp1, "必须选择要更新的用户");
			return;
		}
		String username = um.getRowData().get(row).get(1);
		User u = um.getUserDao().load(username);
		ud.show(u);
	}
	
	private class UserManagerClick implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==jb1) {
				//添加
				ad.setVisible(true);
			} else if(e.getSource()==jb2) {
				//删除
				deleteUser();
			} else if(e.getSource()==jb3) {
				//修改
				updateUser();
			}
		}
	}
	/*
	 * 用户信息管理重载数据
	 * */
	private void refreshData() {
		um.initData();
		jt.updateUI();
	}
	/*
	 * 用户信息管理更新界面及操作
	 * */
	private class UpdateDialog extends JDialog {
		private static final long serialVersionUID = 1L;
		private JLabel jl1,jl2,jl3;
		private JButton jb1,jb2;
		private JPanel jp1,jp2,jp3,jp4;
		private JTextField jtf1;
		private JPasswordField jpf;
		private User user;
		private JComboBox jcb;
		
		public void show(User user) {
			this.user = user;
//			jtf1.setText(user.getNickname());
//			jpf.setText(user.getPassword());
			reset();
			this.setTitle("正在更新"+user.getUsername()+"的用户信息");
			this.setVisible(true);
		}
		
		public UpdateDialog() {
			ImageIcon i = new ImageIcon(this.getClass().getResource("/img/tubiao.jpg"));
	        this.setIconImage(i.getImage());
			this.setSize(350, 200);
			this.setLocation(700, 320);
			this.setTitle("更新用户信息");
			//设置为模态对话框
			this.setModal(true);
			this.setLayout(new GridLayout(4, 1));
			jp1 = new JPanel(); jp2 = new JPanel(); jp3 = new JPanel();jp4 = new JPanel();
			jl1 = new JLabel("用户密码:"); jl2 = new JLabel("用户昵称:");jl3 = new JLabel("用户身份:");
			jcb = new JComboBox();
			DefaultComboBoxModel model = new DefaultComboBoxModel();
			model.addElement("管理员");
			model.addElement("普通用户");
			jcb.setModel(model);
			jb1 = new JButton("更新用户"); jb2 = new JButton("重置数据");
			jtf1 = new JTextField(20); 
			jpf = new JPasswordField(20);
			jp1.add(jl1); jp1.add(jpf);
			jp2.add(jl2); jp2.add(jtf1);
			jp3.add(jl3);jp3.add(jcb);
			jp4.add(jb1); jp4.add(jb2);
			jb1.addActionListener(new UpdateDialogClick());
			jb2.addActionListener(new UpdateDialogClick());
			this.add(jp1); this.add(jp2); this.add(jp3);this.add(jp4);
		}
		
		private class UpdateDialogClick implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(e.getSource()==jb1) {
						//更新用户
						String password = new String(jpf.getPassword());
						String nickname = jtf1.getText();
						String pass = (String) jcb.getSelectedItem();
						user.setNickname(nickname);
						user.setPassword(password);
						if("管理员".equals(pass)){
							user.setPass("0");
						}else{
							user.setPass("1");
						}
						//System.out.println(user);
						um.getUserDao().update(user);
						ud.setVisible(false);
						refreshData();
					} else if(e.getSource()==jb2) {
						//重置数据
						reset();
					}
				} catch (LieChangException e1) {
					LieChangUtil.showError(jp1, e1.getMessage());
				}
			}
		}
		
		private void reset() {
			jtf1.setText(user.getNickname());
			jpf.setText(user.getPassword());
			String item;
			int i;
			if("0".equals(user.getPass())){
				item = "管理员";
			}else{
				item = "普通用户";
			}
			
			for(i = 0 ; i < jcb.getItemCount();i++){
				if(item.equals(jcb.getItemAt(i).toString())){
					//System.out.println(i);
					break;
				}
			}
			jcb.setSelectedIndex(i);
		}
	}
	/*
	 * 用户信息管理增加界面及操作
	 * */
	private class AddDialog extends JDialog {
		private static final long serialVersionUID = 1L;
		private JLabel jl1,jl2,jl3,jl4;
		private JButton jb1,jb2;
		private JPanel jp1,jp2,jp3,jp4,jp5;
		private JTextField jtf1,jtf2;
		private JPasswordField jpf;
		private JComboBox jcb1;
		
		public AddDialog() {
			ImageIcon i = new ImageIcon(this.getClass().getResource("/img/tubiao.jpg"));
	        this.setIconImage(i.getImage());
			this.setSize(350, 220);
			this.setLocation(700, 320);
			this.setTitle("添加用户信息");
			//设置为模态对话框
			this.setModal(true);
			this.setLayout(new GridLayout(5, 1));
			jp1 = new JPanel(); jp2 = new JPanel(); jp3 = new JPanel(); jp4 = new JPanel();jp5 = new JPanel();
			jl1 = new JLabel("用  户  名:"); jl2 = new JLabel("用户密码:"); jl3 = new JLabel("用户昵称:");jl4 = new JLabel("用户身份:");
			jcb1 = new JComboBox();
			DefaultComboBoxModel model = new DefaultComboBoxModel();
			model.addElement("管理员");
			model.addElement("普通用户");
			jcb1.setModel(model);
			jb1 = new JButton("添加用户"); jb2 = new JButton("重置数据");
			jtf1 = new JTextField(20); jtf2 = new JTextField(20);
			jpf = new JPasswordField(20);
			jp1.add(jl1); jp1.add(jtf1);
			jp2.add(jl2); jp2.add(jpf);
			jp3.add(jl3); jp3.add(jtf2);
			jp4.add(jl4); jp4.add(jcb1);
			jp5.add(jb1); jp5.add(jb2);
			jb1.addActionListener(new AddDialogClick());
			jb2.addActionListener(new AddDialogClick());
			this.add(jp1); this.add(jp2); this.add(jp3); this.add(jp4);this.add(jp5);
		}
		
		private class AddDialogClick implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(e.getSource()==jb1) {
						//添加用户
						String username = jtf1.getText();
						if(username==null||"".equals(username.trim())) {
							LieChangUtil.showError(jp1, "用户名不能为空");
							return;
						}
						String password = new String(jpf.getPassword());
						String nickname = jtf2.getText();
						String item = (String)jcb1.getSelectedItem();
						//int item = jcb1.getSelectedIndex();
						System.out.println(item);
						User u = new User();
						u.setNickname(nickname);
						u.setPassword(password);
						u.setUsername(username);
						if("管理员".equals(item)){
							u.setPass("0");
						}else{
							u.setPass("1");
						}
						um.getUserDao().add(u);
						ad.setVisible(false);
						reset();
						refreshData();
					} else if(e.getSource()==jb2) {
						//重置数据
						reset();
					}
				} catch (LieChangException e1) {
					LieChangUtil.showError(jp1, e1.getMessage());
				}
			}
		}
		
		private void reset() {
			jtf1.setText("");
			jtf2.setText("");
			jpf.setText("");
		}
	}
	
}
