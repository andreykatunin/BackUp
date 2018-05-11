package backup;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;

public class ReadFolder {
	
	public static ArrayList<File> listFiles = new ArrayList<File>();
	public static ArrayList<String> filesPath = new ArrayList<String>();
	public static Map<String, String> mapFiles = new HashMap<String, String>();
	
	public static void subDirectory(File fileDirectory, String filetype, Map<String, File> filesWithPath) {
		File[] listOfFiles = fileDirectory.listFiles();
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		        //System.out.println("File "+file.getName());
		        if(FilenameUtils.getExtension(file.getName()).equals(filetype)){
		        	System.out.println("	type file " + file.getName());
		        	listFiles.add(file);
		        	filesWithPath.put(file.getPath(), file);
		        }
		    }
		    if (file.isDirectory() && !file.getName().equals("archieves")) {
		        System.out.println("Directory "+file.getName());
		        subDirectory(file, filetype, filesWithPath);
		    }
		}
	}
}