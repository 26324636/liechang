package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import model.User;

/*
 * 管理员身份登录总界面
 * */
public class AdminAllFrame extends JFrame {
	private static final long serialVersionUID = 816162796278875865L;
	private JMenuBar jmb;
	private JMenu jm1,jm2,jm3;
	private JMenuItem jmi1,jmi2,jmi3,jmi4;
	private UserPanel up;
	private WorkPlacePanel wp;
	private HRPanel hp;
	private JPanel jp1;
	private User admin;
	
	
	public WorkPlacePanel getWp() {
		return wp;
	}

	public void setDp(WorkPlacePanel wp) {
		this.wp = wp;
	}

	public HRPanel getHp() {
		return hp;
	}

	public void setHp(HRPanel hp) {
		this.hp = hp;
	}

	public AdminAllFrame(User admin) {
		this.setSize(700, 600);
		this.setLocation(600, 300);
		this.admin = admin;
		this.setTitle("欢迎使用猎场-HR人力资源管理系统,当前管理者是:"+this.admin.getNickname());
		ImageIcon i = new ImageIcon(this.getClass().getResource("/img/tubiao.jpg"));
        this.setIconImage(i.getImage());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		jmb = new JMenuBar();
		jm1 = new JMenu("职场管理");
		jm2 = new JMenu("人力资源管理");
		jm3 = new JMenu("系统管理");
		jmi1 = new JMenuItem("职场信息管理");
		jmi2 = new JMenuItem("人力资源信息管理");
		jmi3 = new JMenuItem("用户信息管理");
		jmi4 = new JMenuItem("退出系统");
		jm1.add(jmi1); jm2.add(jmi2); jm3.add(jmi3); jm3.add(jmi4);
		jmb.add(jm1); jmb.add(jm2); jmb.add(jm3);
		this.add(jmb,BorderLayout.NORTH);
		jp1 = new JPanel(new BorderLayout());
		up = new UserPanel(this);
		wp = new WorkPlacePanel(this);
		hp = new HRPanel(this);
		this.add(jp1);
		
		jmi1.addActionListener(new MenuClick());
		jmi2.addActionListener(new MenuClick());
		jmi3.addActionListener(new MenuClick());
		jmi4.addActionListener(new MenuClick());
		
		this.setVisible(true);
	}
	
	private void close() {
		this.setVisible(false);
		new LoginFrame();
	}
	
	private class MenuClick implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			jp1.removeAll();
			if(e.getSource()==jmi1) {
				//职场管理
				jp1.add(wp);
			} else if(e.getSource()==jmi2) {
				//人力资源管理
				jp1.add(hp);
			} else if(e.getSource()==jmi3) {
				//用户管理
				jp1.add(up);
			} else if(e.getSource()==jmi4) {
				close();
			}
			jp1.updateUI();
		}
	}
	
}
