package converter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;

public class Main {
	
	public static void main(String[] args) throws Exception {
		 Process p = null;
		 JOptionPane.showMessageDialog(null,"Extracting charts...","GH3-CH Converter",JOptionPane.INFORMATION_MESSAGE);
		 int count = 0, max = 3;
		 boolean flag = false;
		 while(true){
			 try {
		         p = Runtime.getRuntime().exec("cmd.exe /k start chartExtractor.bat");
		         p.waitFor();
		         BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream())); 
		         String line; 
		         while((line = reader.readLine()) != null) { 
		        	 if(line.equals("END")) p.destroy();
		         }
		         break;
		     } catch(Exception e) {
		    	 JOptionPane.showMessageDialog(null,"Extraction failed! Trying again...","GH3-CH Converter",JOptionPane.ERROR_MESSAGE);
		    	 if(++count == max) {
		    		 flag = true;
		    		 break;
		    	 }
		     }
		 }
		 if(flag) throw new Exception();
		 JOptionPane.showMessageDialog(null,"Extraction successful! Press 'ok' to proceed...","GH3-CH Converter",JOptionPane.INFORMATION_MESSAGE);
		 try {
			 p = Runtime.getRuntime().exec("cmd.exe /k start chartRename.bat");
		     p.waitFor();
		     BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream())); 
		     String line; 
		     while((line = reader.readLine()) != null) { 
			     if(line.equals("END")) {
			     	 p.destroy();
			     }
		     }
		 } catch(Exception e) {
 
		 }
	}
}
