package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.JOptionPane;

import view.About;

public class Main {
	
	private static int step = 1;
	private static About about_frame = new About();
	
	public static void main(String[] args) throws Exception {

		LinkedHashMap<String,String> setlist = new LinkedHashMap<String,String>();
		Process p = null;
		BufferedReader reader;
		String line;
		File f = new File("./tools/converter.dat");
		init(f);		

		switch(step) {
			case 1:
				JOptionPane.showMessageDialog(null,"Extracting charts...","GH3-CH Converter",JOptionPane.INFORMATION_MESSAGE);
				try {
					p = Runtime.getRuntime().exec("cmd.exe /c cd tools/chartExtractor & start chartExtractor.bat & exit");
					p.waitFor();
				    reader = new BufferedReader(new InputStreamReader(p.getInputStream())); 
				    while((line = reader.readLine()) != null) { 
					    if(line.equals("END")) {
					    	p.destroy();
					    }
				     }
				 } catch(Exception e) {

				 }
				step++;
				write(f);
			case 2:
				try {
					p = Runtime.getRuntime().exec("cmd.exe /c cd tools & start chartRename.bat & exit");
					p.waitFor();
				    reader = new BufferedReader(new InputStreamReader(p.getInputStream())); 
				    while((line = reader.readLine()) != null) { 
					    if(line.equals("END")) {
					    	p.destroy();
					    }
				     }
				 } catch(Exception e) {

				 }
				step++;
				write(f);
			case 3:
				JOptionPane.showMessageDialog(null,"Extracting songs...","GH3-CH Converter",JOptionPane.INFORMATION_MESSAGE);
				try {
					p = Runtime.getRuntime().exec("cmd.exe /c cd tools & start msvRename.bat & exit");
				    p.waitFor();
				    reader = new BufferedReader(new InputStreamReader(p.getInputStream())); 
				    while((line = reader.readLine()) != null) { 
					    if(line.equals("END")) {
					    	p.destroy();
					    }
				     }
				 } catch(Exception e) {

				 }
			    step++;
			    write(f);
			case 4:
			    try { 
			    	p = Runtime.getRuntime().exec("cmd.exe /c cd tools & move *.exe ../Music"
			    			+ "& move MSVWavConverter.bat ../Music & cd ../Music & start MSVWavConverter.bat & exit");
					p.waitFor();
					reader = new BufferedReader(new InputStreamReader(p.getInputStream())); 
					while((line = reader.readLine()) != null) { 
						if(line.equals("END")) {
						   	p.destroy();
						}
					}
					p = Runtime.getRuntime().exec("cmd.exe /c cd ../Music & move *.exe ../tools"
							+ "& move MSVWavConverter.bat ../tools & exit");
					p.waitFor();
					reader = new BufferedReader(new InputStreamReader(p.getInputStream())); 
					while((line = reader.readLine()) != null) { 
						if(line.equals("END")) {
						   	p.destroy();
						}
					}
					step++;
				 } catch(Exception e) {

				 }
			    write(f);
			case 5:
				setSetlist(setlist);
//				f.delete();
				about_frame.setVisible(true);
		}
	}
	
	private static void init(File f) throws Exception {
		if(f.exists()) {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			step = br.read() - 48;
			br.close();
			fr.close();
		} else {
			write(f);
		}
	}
	
	private static void write(File f) throws Exception {
		FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(step + 48);
        bw.close();
        fw.close();
	}
	
	private static void setSetlist(LinkedHashMap<String,String> setlist) throws Exception {
		FileReader fr = new FileReader("setlist.txt");
		BufferedReader br = new BufferedReader(fr);
		String line,aux;
		int i = 1,j = 1;
		while((line = br.readLine()) != null) { 
			aux = String.format("%d-%d",i,j);
			setlist.put(aux,line);
		}
		br.close();
		fr.close();
	}
}
