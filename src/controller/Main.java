package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedHashMap;

import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

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
					JOptionPane.showMessageDialog(null,"move back","GH3-CH Converter",JOptionPane.INFORMATION_MESSAGE);
					p = Runtime.getRuntime().exec("cmd.exe /c cd ../Music & move *.exe ../tools"
							+ "& move MSVWavConverter.bat ../tools & exit");
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
			case 5:
				int opt = JOptionPane.showConfirmDialog(null,"Do you wish to import a setlist?","GH3-CH Converter",JOptionPane.YES_NO_OPTION);
				if(opt == 0) {
					boolean flag = true;
					FileWriter fw = new FileWriter(new File("setlist.txt"));
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write("null");
					bw.close();
					fw.close();
					while(flag) {
						JOptionPane.showMessageDialog(null,"Please fill setlist.txt with your information in order and formatted"
								+ "\n\"artist - song\" line by line and press OK to continue...","GH3-CH Converter",JOptionPane.WARNING_MESSAGE);
						FileReader fr = new FileReader(new File("setlist.txt"));
						BufferedReader br = new BufferedReader(fr);
						if(br.readLine().equals("null")) flag = true;
						else flag = false;
						br.close();
						fr.close();
					}
					mkSetlist(setlist);
					mkChartDirectory(setlist);	
					JOptionPane.showMessageDialog(null,"All charts created!","GH3-CH Converter",JOptionPane.INFORMATION_MESSAGE);
				}
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
	
	private static void mkSetlist(LinkedHashMap<String,String> setlist) throws Exception {
		FileReader fr = new FileReader("setlist.txt");
		BufferedReader br = new BufferedReader(fr);
		String line,aux;
		int i = 1,j = 1,k = 1;
		while((line = br.readLine()) != null) {
			if(i == 8 && j == 5) {
				aux = String.format("b-%d",k);
				setlist.put(aux,line);
				k++;
			} else {
				if(j == 6) {
					i++;
					j = 1;
				}
				aux = String.format("%d-%d",i,j);
				setlist.put(aux,line);
				j++;
			}
		}
		br.close();
		fr.close();
	}
	
	private static void mkChartDirectory(LinkedHashMap<String,String> setlist)  {
		String charter = JOptionPane.showInputDialog(null,"Charter name:","GH3-CH Converter",JOptionPane.INFORMATION_MESSAGE);
		setlist.forEach((k,v) -> {
			try {
				String command = "cmd.exe /c cd Charts & move " + k + ".chart ../Music/"+ k +"/notes.chart";
				Process p = Runtime.getRuntime().exec(command);
			} catch (IOException e) {

			}
			String[] split = v.split("-");
			FileWriter fw;
			try {
				fw = new FileWriter(new File("./Music/" + k + "/song.ini"));
				BufferedWriter bw = new BufferedWriter(fw);
		        bw.write("[Song]");
		        bw.newLine();
		        bw.write("name = " + split[1]);
		        bw.newLine();
		        bw.write("artist = " + split[0]);
		        bw.newLine();
		        bw.write("charter = " + charter);
		        bw.close();
		        fw.close();
			} catch (IOException e) {

			}
			File chart = new File("./Music/" + k + "/" + k + ".chart");
			chart.renameTo(new File("./Music/" + k + "/notes.chart"));
			File dir = new File("./Music/" + k);
			dir.renameTo(new File("./Music/" + v));
		});
	}
}
