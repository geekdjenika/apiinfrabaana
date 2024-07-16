package ml.geekdjenika.apiinfrabaana.configs;

import ml.geekdjenika.apiinfrabaana.dto.Excel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelConfig {

    public static List<Excel> importExcel(MultipartFile file) {

        try {
            // creation d'une liste dans la quelle on va mettre la liste recuperée
            List<Excel> importedList = new ArrayList<>();

            // lecture du fichier
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Iterator<Sheet> sheet = workbook.sheetIterator();

            DataFormatter formatter = new DataFormatter();

            while (sheet.hasNext()) {

                int currentRow = 0;

                Sheet sh = sheet.next();
                Iterator<Row> iterator = sh.iterator();
                // parcour du fichier excel ligne par ligne
                while (iterator.hasNext()) {
                    Row row = iterator.next();
                    Iterator<Cell> cellIterator = row.iterator();
                    // Recuperation de la ligne courante
                    // Row ligneCourante = ligne.next();
                    // on lui dit de sauter la première ligne du fichier, qui est l'entête
                    if (currentRow == 0) {
                        currentRow++;
                        continue;
                    }

                    // Après avoir recuperer une ligne, on crée un postulant et on recupère ses
                    // attributs;
                    Excel excel = new Excel();

                    int currentCell = 0;
                    // parcour des colonnes d'une ligne
                    while (cellIterator.hasNext()) {
                        // Recuperation de la colonne courante
                        Cell colonneCourante = cellIterator.next();
                        // recuperation des infos de chaque colonne
                        switch (currentCell) {
                            // première colonne contenant le nom
                            case 0:
                                excel.setDescription(formatter.formatCellValue(colonneCourante));
                                break;
                            case 1:
                                excel.setReference(formatter.formatCellValue(colonneCourante));
                                break;
                            case 2:
                                excel.setCategory1(formatter.formatCellValue(colonneCourante));
                                break;
                            case 3:
                                excel.setAmount1(formatter.formatCellValue(colonneCourante));
                                break;
                            case 4:
                                excel.setCurrency1(formatter.formatCellValue(colonneCourante));
                                break;
                            case 5:
                                excel.setCategory2(formatter.formatCellValue(colonneCourante));
                                break;
                            case 6:
                                excel.setAmount2(formatter.formatCellValue(colonneCourante));
                                break;
                            case 7:
                                excel.setCurrency2(formatter.formatCellValue(colonneCourante));
                                break;

                            default:
                                break;
                        }
                        currentCell++;
                    }
                    importedList.add(excel);
                }
            }

            workbook.close();
            return importedList;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            // TODO: handle exception
            return null;
        }

    }

}
