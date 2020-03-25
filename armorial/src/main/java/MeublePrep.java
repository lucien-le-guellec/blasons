import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MeublePrep {

	public static void main(String[] args) {
		String dir = "C:\\Users\\valen\\git\\blasons\\motifs";
		List<String> files = null;
		
		try {
			files = Files.list(Paths.get(dir))
					.filter(Files::isRegularFile)
					.map(x -> x.toString())
					.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		for(String file : files) {			
			File oldFile = new File(file);
			Path path = Paths.get(file);
			String fileName = path.getFileName().toString();
			
			fileName = fileName.split("-")[fileName.split("-").length-1];
			fileName = fileName.replace(".svg.png", ".png");
			fileName = fileName.replace(".svg(1)", "");
			fileName = fileName.toLowerCase();
			fileName = fileName.replace("é", "e");
			fileName = fileName.replace("è", "e");
			fileName = fileName.replace("ê", "e");
			fileName = fileName.replace("à", "a");
			fileName = fileName.replace("meuble_heraldique_", "");
			fileName = fileName.replace("heraldique_", "");
			fileName = fileName.replace("heraldry", "");
			fileName = fileName.replace("heraldique_meuble_", "");
			fileName = fileName.replace("meuble_", "");
			fileName = fileName.replace("(heraldique)", "");
			
			while(fileName.charAt(0) == '_') {
				fileName = fileName.substring(1);
			}
			
			Path newFilePath = null;
			File newFile = null;
			
			System.out.println("File : " + fileName);
			
			int  i = 0;
			do {
				String fileNameToSave = fileName;
				if(i > 0) {
					if(fileName.equals(".png")) {
						fileNameToSave = " (" + i + ").png";
					}
					else {
						fileNameToSave = fileName.split(".png")[0] + " (" + i + ").png";
					}
				}
				
				System.out.println("Try to save as " + fileNameToSave);
				
				newFilePath = Paths.get(dir).resolve(fileNameToSave);
				newFile = newFilePath.toFile();
				i++;
			} while(!oldFile.renameTo(newFile));
			
		}
		

	}

}
