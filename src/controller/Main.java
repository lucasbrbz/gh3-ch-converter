package controller;

import converter.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;

public class Main {
	
	public static void main(String[] args) throws Exception {
		
		Process p = null;
		String line;
		BufferedReader reader;
		
		JOptionPane.showMessageDialog(null,"Extracting charts...","GH3-CH Converter",JOptionPane.INFORMATION_MESSAGE);
		try {
			p = Runtime.getRuntime().exec("cmd.exe /c cd tools/chartExtractor & start chartExtractor.bat");
			p.waitFor();
			reader = new BufferedReader(new InputStreamReader(p.getInputStream())); 
			while((line = reader.readLine()) != null) { 
				if(line.equals("END")) {
					p.destroy();
				}
			}
		} catch(Exception e) {

		}
		try {
			p = Runtime.getRuntime().exec("cmd.exe /c cd tools & start chartRename.bat");
		    p.waitFor();
		    reader = new BufferedReader(new InputStreamReader(p.getInputStream())); 
		     
		    while((line = reader.readLine()) != null) {		    	
			    if(line.equals("END")) {
			    	p.destroy();
			    }
		    }
		} catch(Exception e) {

		}
		JOptionPane.showMessageDialog(null,"Extracting songs...","GH3-CH Converter",JOptionPane.INFORMATION_MESSAGE);
		try {
			p = Runtime.getRuntime().exec("cmd.exe /c cd tools & start msvRename.bat");
		    p.waitFor();
		    reader = new BufferedReader(new InputStreamReader(p.getInputStream()));  
		    while((line = reader.readLine()) != null) { 
			    if(line.equals("END")) {
			    	p.destroy();
			    }
		    }
		} catch(Exception e) {
 
		}
		try {
			p = Runtime.getRuntime().exec("cmd.exe /c cd Music & start MSVWavConverter.bat");
		    p.waitFor();
		    reader = new BufferedReader(new InputStreamReader(p.getInputStream())); 
		    while((line = reader.readLine()) != null) { 
			    if(line.equals("END")) {
			    	p.destroy();
			    }
		    }
		} catch(Exception e) {
 
		}
	}
}
