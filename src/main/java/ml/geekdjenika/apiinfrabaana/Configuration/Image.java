package ml.geekdjenika.apiinfrabaana.Configuration;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Image {
    public static void saveImage(String uplodDirectory, String filename, MultipartFile file) throws IOException {

        Path uploadPath = Paths.get(uplodDirectory);

        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try(InputStream inputStream = file.getInputStream()){
            Path fichierPath = uploadPath.resolve(filename);

            Files.copy(inputStream, fichierPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe){
            throw new IOException("Impossible d'enregistrer le fichier image:" + filename, ioe);
        }
    }
}
