import com.netease.extension.PdfGenerator;
import com.netease.extension.structures.PDFStructure;

import java.io.FileNotFoundException;

public class PdfGeneratorV2Test {

    public static void main(String[] args) throws FileNotFoundException {
        PDFStructure pdfStructure = new PDFStructure();



        PdfGenerator.createPDFV2(pdfStructure);
    }

}
