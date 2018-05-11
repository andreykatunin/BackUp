package backup;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FilenameUtils;
import model.ValuesModel;

public class RecoverZip {
	
	public static ArrayList<File> listFiles = new ArrayList<File>();
	
	public static void main(String[] args) throws FileNotFoundException, IOException{ 
		unzip(ValuesModel.archivesPath+ValuesModel.recoverArchieve);
	}
	
	public static String unzip(String path) {
		
		String folderName=null;
        File file = new File(path);
        if (!file.exists() || !file.canRead()) {
            System.out.println("File cannot be read");
            return folderName;
        }

        try {
            ZipFile zip = new ZipFile(path, Charset.forName("CP866"));
            Enumeration<?> entries = zip.entries();
            
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                if(folderName==null){
                	folderName=entry.getName();
                	System.out.println(folderName);
                }

                if (entry.isDirectory()) {
                    new File(file.getParent(), entry.getName()).mkdirs();
                } else {
                    write(zip.getInputStream(entry), new BufferedOutputStream(new FileOutputStream(new File(ValuesModel.recoverPath, entry.getName()))));
                }
            }

            zip.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        readRecoverZip();
        return folderName;
    }

    private static void write(InputStream in, OutputStream out) throws IOException {
	    byte[] buffer = new byte[1024];
	    int len;
	    while ((len = in.read(buffer)) >= 0)
		    out.write(buffer, 0, len);
	    out.close();
	    in.close();
	}
    
    private static void readRecoverZip(){
    	File Folder = new File(ValuesModel.recoverPath);
    	File[] listOfFiles = Folder.listFiles();
    	for (File file : listOfFiles) {
    		if(FilenameUtils.getExtension(file.getName()).equals(ValuesModel.filetype)){
    			listFiles.add(file);
		    }
		}
    }
    
    public static void search(File fileDirectory) throws IOException{
    	File[] listOfFiles = fileDirectory.listFiles();
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		        //System.out.println("File "+file.getName());
		        if(FilenameUtils.getExtension(file.getName()).equals(ValuesModel.filetype)){
		        	for (File fch : listFiles) {
		        		if(file.getName().equals(fch.getName())){
		        			System.out.println("Замена "+file.getName());
		        			System.out.println("Путь "+fch.getParent().toString());
		        			Files.move(Paths.get(fch.getPath()), Paths.get(file.getPath()), StandardCopyOption.REPLACE_EXISTING);
		        		}
		        	}
		        	
		        }
		    }
		    if (file.isDirectory() && !file.getName().equals("archieves")) {
		        System.out.println("Directory "+file.getName());
		        search(file);
		    }
		}
    }
}
