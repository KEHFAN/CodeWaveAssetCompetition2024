import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Test;

public class JSONTest {

    @Test
    public void test1() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action","exec");
        jsonObject.put("command","version");

        System.out.println(jsonObject.toString());
    }
}
