import com.netease.lowcode.extension.config.DevToolsConfig;

public class DemoTest {
    public static void main(String[] args) {
        Long count = DevToolsConfig.aopAnalyzeData.putIfAbsent("a", 0L);
        DevToolsConfig.aopAnalyzeData.put("a", count + 1);

        System.out.println();
    }
}
