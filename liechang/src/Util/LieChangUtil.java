package Util;

import java.awt.Component;

import javax.swing.JOptionPane;
/*
 *错误信息
 **/
public class LieChangUtil {
	public static void showError(Component parent,String msg) {
		JOptionPane.showMessageDialog(parent, msg,"发现错误",JOptionPane.ERROR_MESSAGE);
	}
}
