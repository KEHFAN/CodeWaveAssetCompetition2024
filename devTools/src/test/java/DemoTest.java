import com.netease.lowcode.extension.config.DevToolsConfig;
import com.netease.lowcode.extension.model.MethodModel;

public class DemoTest {
    public static void main(String[] args) {

        Long begin = System.currentTimeMillis();
        String key = "aaa";
        String declaringTypeName = "a";
        String name = "aa";

        MethodModel model = DevToolsConfig.aopAnalyzeDetailData.getOrDefault(key, initData(key, declaringTypeName, name));
        model.setCount(model.getCount() + 1L);
        model.setCost(model.getCost() + (System.currentTimeMillis() - begin));
        model.setAvgCost((double)model.getCost() / (double)model.getCount());
        DevToolsConfig.aopAnalyzeDetailData.put(key, model);

        System.out.println();

        model = DevToolsConfig.aopAnalyzeDetailData.getOrDefault(key, initData(key, declaringTypeName, name));
        model.setCount(model.getCount() + 1L);
        model.setCost(model.getCost() + (System.currentTimeMillis() - begin));
        model.setAvgCost((double)model.getCost() / (double)model.getCount());
        DevToolsConfig.aopAnalyzeDetailData.put(key, model);

        System.out.println();
    }

    private static MethodModel initData(String key,String typeName,String methodName) {
        MethodModel model = new MethodModel();
        model.setUniqueId(key);
        model.setClassType(typeName);
        model.setMethodName(methodName);
        //model.setLogicName();
        //model.setTraceId();
        model.setCount(0L);
        model.setCost(0L);
        model.setAvgCost(0.0);

        return model;
    }
}
