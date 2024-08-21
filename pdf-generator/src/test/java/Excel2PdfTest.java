import com.netease.lowcode.pdf.extension.Excel2Pdf;

public class Excel2PdfTest {

    public static void main(String[] args) {
        Excel2Pdf.xlsx2pdf(
                "{\n" +
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
                        "}"
        ,"");
    }
}
