package ml.geekdjenika.apiinfrabaana.Configuration;

import ml.geekdjenika.apiinfrabaana.Model.ExcelDto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Excel {

    public static List<ExcelDto> importer(MultipartFile file) {

        try {
            // creation d'une liste dans la quelle on va mettre la liste recuperée
            List<ExcelDto> listeimportee = new ArrayList<>();

            // lecture du fichier
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Iterator<Sheet> sheet = workbook.sheetIterator();

            DataFormatter formatter = new DataFormatter();

            while (sheet.hasNext()) {

                int numeroLigne = 0;

                Sheet sh = sheet.next();
                Iterator<Row> iterator = sh.iterator();
                // parcour du fichier excel ligne par ligne
                while (iterator.hasNext()) {
                    Row row = iterator.next();
                    Iterator<Cell> cellIterator = row.iterator();
                    // Recuperation de la ligne courante
                    // Row ligneCourante = ligne.next();
                    // on lui dit de sauter la première ligne du fichier, qui est l'entête
                    if (numeroLigne == 0) {
                        numeroLigne++;
                        continue;
                    }

                    // Après avoir recuperer une ligne, on crée un postulant et on recupère ses
                    // attributs;
                    ExcelDto excelDto = new ExcelDto();

                    int numeroColonne = 0;
                    // parcour des colonnes d'une ligne
                    while (cellIterator.hasNext()) {
                        // Recuperation de la colonne courante
                        Cell colonneCourante = cellIterator.next();
                        // recuperation des infos de chaque colonne
                        switch (numeroColonne) {
                            // première colonne contenant le nom
                            case 0:
                                excelDto.setDescription(formatter.formatCellValue(colonneCourante));
                                break;
                            case 1:
                                excelDto.setReference(formatter.formatCellValue(colonneCourante));
                                break;
                            case 2:
                                excelDto.setCategorie1(formatter.formatCellValue(colonneCourante));
                                break;
                            case 3:
                                excelDto.setMontant1(formatter.formatCellValue(colonneCourante));
                                break;
                            case 4:
                                excelDto.setDevise1(formatter.formatCellValue(colonneCourante));
                                break;
                            case 5:
                                excelDto.setCategorie2(formatter.formatCellValue(colonneCourante));
                                break;
                            case 6:
                                excelDto.setMontant2(formatter.formatCellValue(colonneCourante));
                                break;
                            case 7:
                                excelDto.setDevise2(formatter.formatCellValue(colonneCourante));
                                break;

                            default:
                                break;
                        }
                        numeroColonne++;
                    }
                    listeimportee.add(excelDto);
                    // numeroLigne++;
                }
            }

            workbook.close();
            return listeimportee;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            // TODO: handle exception
            return null;
        }

    }

}
