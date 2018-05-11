package backup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import model.ValuesModel;

public class MakeZip {
	
	public static void createZip(Map<String, File> filesWithPath) throws FileNotFoundException, IOException {
		String[] filesPath = filesWithPath.keySet().toArray(new String[filesWithPath.size()]);
		File[] filesValue = filesWithPath.values().toArray(new File[filesWithPath.size()]);
		
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy-HH.mm.ss");
		Date date = new Date();
		System.out.println();
		File zipFile = new File(ValuesModel.archivesPath+dateFormat.format(date)+".zip");
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
		for (int i = 0; i < filesValue.length; i++) {
			addToZipFile(filesValue[i], out);
			System.out.println(filesPath[i]);
		}
		createReadme(filesPath);
		File readMe = new File(ValuesModel.archivesPath+"log");
		addToZipFile(readMe, out);
		out.close();
		readMe.delete();
	}
	
	private static void addToZipFile(File file, ZipOutputStream out) throws FileNotFoundException, IOException {

		String fileName = file.getName();
		System.out.println("Writing '" + fileName + "' to zip file");

		FileInputStream fis = new FileInputStream(file);
		ZipEntry zipEntry = new ZipEntry(fileName);
		out.putNextEntry(zipEntry);

		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			out.write(bytes, 0, length);
		}

		out.closeEntry();
		fis.close();
	}
	
	private static void createReadme(String[] paths) throws IOException {
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			fw = new FileWriter(ValuesModel.archivesPath+"log");
			bw = new BufferedWriter(fw);
			for (String path : paths) {
				bw.write(path);
				bw.newLine();
			}
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		}
		bw.close();
		fw.close();
	}
}
