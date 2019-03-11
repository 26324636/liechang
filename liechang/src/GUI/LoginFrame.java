package GUI;

import java.awt.BorderLayout;

/*
 * 用户登录总页面
 * */
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.LieChangException;
import model.User;
import Util.LieChangUtil;
import dao.UserDaoJDBC;
public class LoginFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel jp1,jp2,jp3,jpbig;
	private JLabel jl1,jl2,jlimg;
	private JTextField jtf;
	private JPasswordField jpf;
	private JButton jb;
	private UserDaoJDBC ud;

	public LoginFrame() {
		this.setSize(320, 230);
		this.setResizable(false);
		this.setLocation(700, 300);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("猎场-系统登录");
		ImageIcon i = new ImageIcon(this.getClass().getResource("/img/tubiao.jpg"));
        this.setIconImage(i.getImage());
		ud = new UserDaoJDBC();
		
		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		jpbig = new JPanel();
		jlimg = new JLabel();
		ImageIcon headimg = new ImageIcon(LoginFrame.class.getResource("/img/head.jpg"));
		jlimg = new JLabel(headimg);
	    Image img = headimg.getImage();    
	    img = img.getScaledInstance(300, 100, Image.SCALE_DEFAULT); 
	    headimg.setImage(img);  
	    jlimg.setIcon(headimg);
		jl1 = new JLabel("用户名称:");
		jl2 = new JLabel("用户密码:");
		jtf = new JTextField(20);
		jpf = new JPasswordField(20);
		jb = new JButton("登录");
		jb.addActionListener(new BtnClick());
		
		jp1.add(jl1); jp1.add(jtf);
		jp2.add(jl2); jp2.add(jpf);
		jpbig.setLayout(new GridLayout(2,1));
		jpbig.add(jp1);
		jpbig.add(jp2);
		jp3.add(jb);
		this.add(jlimg,BorderLayout.NORTH);
		this.add(jpbig,BorderLayout.CENTER); 
		this.add(jp3,BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
	
	private void close() {
		this.setVisible(false);
	}
	
	private class BtnClick implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==jb) {
				try {
					String username = jtf.getText();
					String password = new String(jpf.getPassword());
					User u = ud.login(username, password);
					System.out.println(u);
					if("0".equals(u.getPass())){
						new AdminAllFrame(u);//如果是管理员，进入管理员界面
					}
					else{
						new UserAllFrame(u);//如果是普通用户，进入用户界面
					}
					
					close();
				} catch (LieChangException e1) {
					LieChangUtil.showError(jp1, e1.getMessage());
				}
			}
		}
	}
}
