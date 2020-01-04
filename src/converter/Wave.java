package converter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.JOptionPane;

import controller.Main;

public class Wave {

	public static void merge(int set,int song,ArrayList<Integer> selectedStreams,int first) {
		Main.getWaitFrame().setVisible(true);
		String wavFile,out;
		File f = new File("./Music/"+set+"-"+song+".ogg");
		if(!f.exists()) {
			try {
				File outputFile = null;
				AudioInputStream wave = null;
				List<AudioInputStream> audioInputStreamList = new ArrayList<AudioInputStream>();
						
				for(int i : selectedStreams.subList(first,selectedStreams.size())) {
					if(set == 'b')
						wavFile = String.format("%c-%d-%d.wav",set,song,i);
					else
						wavFile = String.format("%d-%d-%d.wav",set,song,i);
					wave =  AudioSystem.getAudioInputStream(new File("./Music/" + wavFile));
					audioInputStreamList.add(wave);				
				}
				
				AudioInputStream merged = new MixingAudioInputStream(wave.getFormat(), audioInputStreamList);
				if(set == 'b')
					out = String.format("%c-%d.wav",set,song);
				else
					out = String.format("%d-%d.wav",set,song);
				outputFile = new File("./Music/" + out);
				AudioSystem.write(merged,AudioFileFormat.Type.WAVE,outputFile);
				Ogg.convert(outputFile);
				Main.getWaitFrame().dispose();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}
}