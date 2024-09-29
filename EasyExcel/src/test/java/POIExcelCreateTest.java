import com.fasterxml.jackson.core.JsonProcessingException;
import com.netease.lowcode.extensions.JsonUtil;
import com.netease.lowcode.extensions.poi.POIExcelCreate;
import com.netease.lowcode.extensions.poi.POIExcelCreateDTO;

import java.util.ArrayList;
import java.util.List;

public class POIExcelCreateTest {

    public static void main(String[] args) throws JsonProcessingException {
        POIExcelCreateDTO request = new POIExcelCreateDTO();
        request.setExportFileName("tttttttt.xls");

        List<DataStructure> data = new ArrayList<>();
        DataStructure e = new DataStructure();
        e.property1 = "hh";
        e.property2 = "ff";
        data.add(e);

        request.setJsonData(JsonUtil.toJson(data));
        POIExcelCreate.poiCreateXls(request,DataStructure.class);
    }
}

class DataStructure {

    @Label("标题1")
    public String property1;
    @Label("标题2")
    public String property2;
    @Label
    public String property3;

    public String property4;


}
