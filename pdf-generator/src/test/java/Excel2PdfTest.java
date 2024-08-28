import com.netease.lowcode.pdf.extension.Excel2Pdf;
import com.netease.lowcode.pdf.extension.structures.BaseResponse;
import com.netease.lowcode.pdf.extension.structures.CreateByXlsxRequest;
import com.netease.lowcode.pdf.extension.utils.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class Excel2PdfTest {

    public static void main(String[] args) {
        String s = "{\n" +
                "    \"name\":\"测试名字\",\n" +
                "    \"list\":[\n" +
                "        {\n" +
                "            \"no\":1,\n" +
                "            \"name\":\"项目1\",\n" +
                "            \"std\":\"国标1\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"no\":2,\n" +
                "            \"name\":\"项目2\",\n" +
                "            \"std\":\"国标2\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"no\":3,\n" +
                "            \"name\":\"项目3\",\n" +
                "            \"std\":\"国标3\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        CreateByXlsxRequest request = new CreateByXlsxRequest();
        request.setJsonData(s);
        request.setExportFileName("测试.pdf");
        request.setTemplateUrl("http://dev.erp.fx.lcap.163yun.com/upload/app/fa9c98b7-cc09-42bb-ba17-f65cc8bca381/导出pdf模板_20240828155248152.xlsx");
        BaseResponse baseResponse = Excel2Pdf.xlsx2pdf(request);
        System.out.println(baseResponse);
    }

    public static void main2(String[] args) throws IOException {
        File templateFile = new File("D:\\新建 XLSX 工作表.xlsx");

        String fileName = templateFile.getName();
        InputStream inputStream = new FileInputStream(templateFile);

        Workbook wb =new XSSFWorkbook(inputStream);
        Sheet sheet0 = wb.getSheetAt(0);
        Row row = sheet0.getRow(0);
        Cell cell0 = row.getCell(0);
        Cell cell1 = row.getCell(1);

        // 字体信息
        XSSFFont font0 = (XSSFFont) wb.getFontAt(cell0.getCellStyle().getFontIndexAsInt());
        XSSFFont font1 = (XSSFFont) wb.getFontAt(cell1.getCellStyle().getFontIndexAsInt());

        String fontName0 = font0.getFontName();
        String fontName1 = font1.getFontName();

        int family0 = font0.getFamily();
        int family1 = font1.getFamily();


        System.out.println();
    }

    public static void main3(String[] args) {
        CreateByXlsxRequest request = new CreateByXlsxRequest();

        Excel2Pdf.xlsx2pdf(request);
    }
}
