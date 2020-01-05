package view;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class About extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public About() {
		super("About GH3-CH Converter");
		setVisible(false);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 405, 330);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(About.class.getResource("/ch-logo.png")));
		lblLogo.setBounds(-13, 0, 175, 135);
		contentPane.add(lblLogo);
	        
	    JLabel lblDownload = new JLabel();
	    lblDownload.setBounds(163, 27, 212, 14);
	    lblDownload.setText("Click here to download the game:");
	    contentPane.add(lblDownload);
	        
	    JLabel lblDownloadLink = new JLabel();
	    lblDownloadLink.setBounds(163, 39, 152, 22);
	    lblDownloadLink.setText("<html><a href=\"\">https://clonehero.net/</a></html>");
	    lblDownloadLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    lblDownloadLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    try {
                            Desktop.getDesktop().browse(new URI("https://clonehero.net/"));
                    } catch (Exception e1) {
                    	
                    }
            }
	    });
	    contentPane.add(lblDownloadLink);
	    
	    JLabel lblDiscord = new JLabel();
	    lblDiscord.setText("Join the Discord community!");
		lblDiscord.setBounds(163, 72, 175, 14);
		contentPane.add(lblDiscord);
		
		JLabel lblDiscordLink = new JLabel();
		lblDiscordLink.setBounds(163, 84, 152, 22);
		lblDiscordLink.setText("<html><a href=\"\">https://discord.gg/Hsn4Cgu</a></html>");
        lblDiscordLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblDiscordLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    try {
                            Desktop.getDesktop().browse(new URI("https://discord.gg/Hsn4Cgu"));
                    } catch (Exception e1) {
                    	
                    }
            }
        });
		contentPane.add(lblDiscordLink); 
		
		JLabel lblText = new JLabel();
		lblText.setBounds(10, 126, 414, 22);
		lblText.setText("Project made by lucasbrbz,");
		contentPane.add(lblText);
		
		JLabel lblText2 = new JLabel();
		lblText2.setBounds(10, 146, 414, 22);
		lblText2.setText("based on MSVTools and chartExtractor by Xebozone");
		contentPane.add(lblText2);
		
		JLabel lblText3 = new JLabel();
		lblText3.setText("Feel free to contact me for suggestions and/or critics, it helps me");
		lblText3.setBounds(10, 186, 414, 22);
		contentPane.add(lblText3);
		
		JLabel lblText4 = new JLabel();
		lblText4.setText("to keep improving as a programmer. Thank you very much!");
		lblText4.setBounds(10, 206, 414, 22);
		contentPane.add(lblText4);
		
		JLabel lblEmail = new JLabel();
		lblEmail.setText("<html>E-mail: <a href=\"\">brbzmg22@gmail.com</a></html>");
		lblEmail.setBounds(10, 246, 414, 22);
		lblEmail.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblEmail.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    try {
                            Desktop.getDesktop().browse(new URI("mailto:brbzmg22@gmail.com"));
                    } catch (Exception e1) {

                    }
            }
        });
		contentPane.add(lblEmail);
		
		JLabel lblSourceCode = new JLabel();
		lblSourceCode.setText("<html>Source Code: <a href=\"\">https://github.com/lucasbrbz/gh3-ch-converter</a></html>");
		lblSourceCode.setBounds(10, 266, 414, 22);
		lblSourceCode.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblSourceCode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    try {
                            Desktop.getDesktop().browse(new URI("https://github.com/lucasbrbz/gh3-ch-converter"));
                    } catch (Exception e1) {
                            
                    }
            }
        });
		contentPane.add(lblSourceCode);
	}
}
