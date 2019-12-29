package converter;

import java.io.File;
import java.io.SequenceInputStream;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class Wave {
	
	public static void merge(String wavFile1, String wavFile2, String newWavFilePath) {
		try {
			File wave1 = new File(wavFile1);
			AudioInputStream clip1 = AudioSystem.getAudioInputStream(wave1);
			AudioInputStream clip2 = AudioSystem.getAudioInputStream(new File(wavFile2));			
			
			AudioInputStream emptyClip = 
				AudioSystem.getAudioInputStream(new File(emptyWavPath));			
			
			AudioInputStream appendedFiles = new AudioInputStream(
					new SequenceInputStream(clip1, emptyClip),
					clip1.getFormat(),
					clip1.getFrameLength() + 100
			);

			clip1 = appendedFiles;
			appendedFiles = new AudioInputStream(
					new SequenceInputStream(clip1, clip2),
					clip1.getFormat(),
					clip1.getFrameLength() + clip2.getFrameLength()
			);

			AudioSystem.write(appendedFiles, AudioFileFormat.Type.WAVE, new File(newWavFilePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
