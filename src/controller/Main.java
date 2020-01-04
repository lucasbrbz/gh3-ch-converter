package controller;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import converter.Wave;
import view.*;

public class Main {
	
	private static Selection selection_frame = new Selection();
	private static Wait wait_frame;
	private static ArrayList<Integer> selectedStreams;
	private static int first;
	
	public static void main(String[] args) throws Exception {
		
		Process p = null;
		
		JOptionPane.showMessageDialog(null,"Extracting charts...","GH3-CH Converter",JOptionPane.INFORMATION_MESSAGE);
		p = Runtime.getRuntime().exec("cmd.exe /c cd tools/chartExtractor & start chartExtractor.bat & exit");
		p.waitFor();
			
		p = Runtime.getRuntime().exec("cmd.exe /c cd tools & start chartRename.bat & exit");
		p.waitFor();
		    
		JOptionPane.showMessageDialog(null,"Extracting songs...","GH3-CH Converter",JOptionPane.INFORMATION_MESSAGE);
		p = Runtime.getRuntime().exec("cmd.exe /c cd tools & start msvRename.bat & exit");
	    p.waitFor();

		p = Runtime.getRuntime().exec("cmd.exe /c cd Music & start MSVWavConverter.bat & exit");
	    p.waitFor();
		
		JOptionPane.showMessageDialog(null,"Select audio streams to be merged (Double check if there's "
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
		
		p = Runtime.getRuntime().exec("cmd.exe /c cd Music & move audio-converter.exe ../tools & move MSVWavConverter.bat ../tools & exit");
		p.waitFor();
		
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
