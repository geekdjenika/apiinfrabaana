package ml.geekdjenika.apiinfrabaana.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
public class Audio {
    private static final List<String> AUDIO_EXTENSIONS = Arrays.asList(".mp3", ".wav", ".ogg", ".flac", ".m4a");

    public static final String SOURCE_DIR = "C:\\Users\\djeni\\IdeaProjects\\infrakunafoni\\assets\\";

    public static void saveAudio(String uploadDir, File file) {
        // Parcourez les fichiers dans le répertoire source
        for (String extension : AUDIO_EXTENSIONS) {
            if (file.getName().endsWith(extension)) {
                // Construisez les chemins complets pour les fichiers source et de destination
                File destinationFile = new File(uploadDir, file.getName());
                try {
                    // Copiez le fichier audio
                    Files.copy(file.toPath(), destinationFile.toPath());
                    System.out.println("Copié " + file.getName());
                } catch (IOException e) {
                    System.out.println("Erreur lors de la copie " + file.getName());
                }
                break;
            }
        }
    }

}
