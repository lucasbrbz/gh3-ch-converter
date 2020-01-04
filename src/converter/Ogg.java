package converter;

import java.io.File;

public class Ogg {
	
	public static void convert(File f) {
		int index = f.getName().lastIndexOf(".");
		String filename = f.getName().substring(0,index);
		f.renameTo(new File(f.getParent(),filename + ".ogg"));
	}
}
