package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

public class Wait extends JFrame {

	private JPanel contentPane;

	public Wait(int set,int song) {
		super("GH3-CH Converter");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 255, 92);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(5, 31, 229, 17);
		progressBar.setIndeterminate(true);
		getContentPane().add(progressBar);
		
		String text = String.format("Converting %d-%d.wav...",set,song);
		JLabel lblNewLabel = new JLabel(text);
		lblNewLabel.setBounds(5, 11, 229, 14);
		contentPane.add(lblNewLabel);
	}
}
