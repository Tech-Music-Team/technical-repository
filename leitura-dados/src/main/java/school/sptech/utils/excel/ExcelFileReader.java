package school.sptech.utils.excel;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import school.sptech.entities.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ExcelFileReader {

    public static Sheet lerSheet(String caminhoArquivo) throws IOException {
        Logger.info(ExcelFileReader.class.getPackageName(), ExcelFileReader.class.getName(),
                "Abrindo arquivo Excel: " + caminhoArquivo);
        
        try {
            InputStream inputStream = new FileInputStream(caminhoArquivo);
            Workbook workbook = new XSSFWorkbook(inputStream);
            
            Logger.info(ExcelFileReader.class.getPackageName(), ExcelFileReader.class.getName(),
                    "Arquivo Excel carregado com sucesso");
            
            return workbook.getSheetAt(0);
        } catch (IOException e) {
            Logger.error(ExcelFileReader.class.getPackageName(), ExcelFileReader.class.getName(),
                    "Erro ao abrir arquivo Excel: " + e.getMessage());
            throw e;
        }
    }
}
