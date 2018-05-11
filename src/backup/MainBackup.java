package backup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import model.ValuesModel;

public class MainBackup {

	public static Map<String, File> filesWithPath = new HashMap<String, File>();
	public static File projectFolder = new File(ValuesModel.projectPath);
	
	public static void main() throws FileNotFoundException, IOException{ 
		
		//поиск всех файлов в текущем проекте, с заданным расширением
		System.out.println(ValuesModel.filetype);
		ReadFolder.subDirectory(projectFolder, ValuesModel.filetype, filesWithPath);
		
		//создание архива из найденных файлов
		MakeZip.createZip(filesWithPath);
		//распаковка требуемого архива 
	}
	
	public static void recover() {
		try {
			System.out.println(ValuesModel.recoverArchieve);
			RecoverZip.unzip(ValuesModel.archivesPath+ValuesModel.recoverArchieve);
			RecoverZip.search(projectFolder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Map<String, File> getMap(){
		return filesWithPath;
	}
}