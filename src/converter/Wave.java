package converter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class Wave {

	public void merge() {			
		try 
		{
			File outputFile = null;
			for(int i=2;i<=5;i++) {
				
				String wavFile1 = String.format("%d-%d-1.wav",1,1);
				String wavFile2 = String.format("%d-%d-%d.wav",1,1,i);
				
				List<AudioInputStream> audioInputStreamList = new ArrayList<AudioInputStream>();		
				AudioInputStream wave1 =  AudioSystem.getAudioInputStream(new File("C:/Users/Lucas/Desktop/tmp/"+wavFile1));
				audioInputStreamList.add(wave1);					
				AudioInputStream wave2 =  AudioSystem.getAudioInputStream(new File("C:/Users/Lucas/Desktop/tmp/"+wavFile2));			
				audioInputStreamList.add(wave2);
				AudioFormat	audioFormat = wave1.getFormat();
				
				AudioInputStream merged = new MixingAudioInputStream(audioFormat, audioInputStreamList);	
				outputFile = new File("C:/Users/Lucas/Desktop/tmp/tmp/1-1.wav");
				AudioSystem.write(merged,AudioFileFormat.Type.WAVE,outputFile);
			}
			Ogg.convert(outputFile);
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}