package view;

import javax.swing.JFrame;

import controller.Main;

import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Selection extends JFrame {
 
	private JCheckBox cbox1,cbox2,cbox3,cbox4,cbox5,cbox6;
	private JButton btnOK,btnCancel;
	private static ArrayList<Integer> list;
	private static int count = 0; 
	private boolean status = false;
	
	public ArrayList<Integer> getList(){
		return list;
	}
	
	public boolean getStatus() {
		return status;
	}
	
	public void setStatus() {
		status = true;
	}
	
	public int getCount() {
		return count;
	}
 
	public Selection() {
		super("Select streams");
		setResizable(false);
		setVisible(false);
		setBounds(100, 100, 255, 92);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new FlowLayout());

		cbox1 = new JCheckBox("1");
		cbox2 = new JCheckBox("2");
		cbox3 = new JCheckBox("3");
		cbox4 = new JCheckBox("4");
		cbox5 = new JCheckBox("5");
		cbox6 = new JCheckBox("6");
	  
		CheckBoxHandler tratador = new CheckBoxHandler();
		cbox1.addItemListener(tratador);
		cbox2.addItemListener(tratador);
		cbox3.addItemListener(tratador);
		cbox4.addItemListener(tratador);
		cbox5.addItemListener(tratador);
		cbox6.addItemListener(tratador);
		
		getContentPane().add(cbox1);
		getContentPane().add(cbox2);
		getContentPane().add(cbox3);
		getContentPane().add(cbox4);
		getContentPane().add(cbox5);
		getContentPane().add(cbox6);	  
		
		btnOK = new JButton("OK");
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.setList(tratador.getSelected(),tratador.getCount());
				setStatus();
				dispose();
			}
		});
		getContentPane().add(btnOK);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(btnCancel);
	}
 
	private class CheckBoxHandler implements ItemListener {
		private ArrayList<Integer> selection = new ArrayList<Integer>();
		private int count = 0;	  
		
		@Override
		public void itemStateChanged(ItemEvent evento) {
			if(cbox1.isSelected()) {
				selection.add(1);
				count++;
			}
			if(cbox2.isSelected()) {
				selection.add(2);
				count++;
			}
			if(cbox3.isSelected()) {
				selection.add(3);
				count++;
			}
			if(cbox4.isSelected()) {
				selection.add(4);
				count++;
			}
			if(cbox5.isSelected()) {
				selection.add(5);
				count++;
			}
			if(cbox6.isSelected()) {
				selection.add(6);
				count++;
			}
		}
		
		public ArrayList<Integer> getSelected() {
			return selection;
		}
		
		public int getCount() {
			return count;
		}
	}
}
