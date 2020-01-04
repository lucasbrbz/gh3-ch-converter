package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import view.*;
import converter.Wave;

public class Main {
	
	private static Selection selection_frame = new Selection();
	private static Wait wait_frame;
	private static ArrayList<Integer> selectedStreams;
	private static int first;
	private static int step;
	
	public static void main(String[] args) throws Exception {
		
		Process p = null;
		BufferedReader reader;
		String line;
		File f = new File("./tools/converter.log");
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
			case 4:
			    try {
			    	p = Runtime.getRuntime().exec("cmd.exe /c cd Music & start MSVWavConverter.bat & exit");
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
			case 5:
				JOptionPane.showMessageDialog(null,"Select audio streams to be merged (Double check if there's\n"
						+ "any audio on them or you will end up with a mute audio file)","GH3-CH Converter",JOptionPane.WARNING_MESSAGE);
				selection_frame.setVisible(true);
				while(true) {
					System.out.print("");
					if(selection_frame.getStatus())
						break;
				}
				for(int i=1;i<=8;i++) {
					for(int j=1;j<=5;j++) {
						if(i == 8 && j == 5) break;
						wait_frame = new Wait(i,j);
						Wave.merge(i,j,selectedStreams,first);
					}
				}
				try {
					p = Runtime.getRuntime().exec("cmd.exe /c cd Music & move audio-converter.exe ../tools & move MSVWavConverter.bat ../tools & exit");
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
	
	public static void init(File f) throws Exception {
		if(f.exists()) {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			step = br.read() - 48;
			br.close();
			fr.close();
		} else {
			FileWriter fw = new FileWriter(f);
	        BufferedWriter bw = new BufferedWriter(fw);
	        bw.write("1");
	        step = 1;
	        bw.close();
	        fw.close();
		}
	}
	
	public static Wait getWaitFrame() {
		return wait_frame;
	}
	
	public static void setList(ArrayList<Integer> list,int qtd) {
		selectedStreams = list;
		switch(qtd) {
			case 1:
				first = 0;
				break;
			case 3:
				first = 1;
				break;
			case 6:
				first = 3;
				break;
			case 10:
				first = 6;
				break;
			case 15:
				first = 10;
				break;
			case 21:
				first = 15;
				break;
		}
	}
}
