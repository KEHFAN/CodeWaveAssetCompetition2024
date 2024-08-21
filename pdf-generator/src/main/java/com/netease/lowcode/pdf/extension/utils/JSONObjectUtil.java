package com.netease.lowcode.pdf.extension.utils;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONObjectUtil {

    /**
     * 将originRow中的freemarker list标签填充数据
     *
     * @param originRow
     * @param requestJsonData
     * @return
     */
    public static List<List<JSONObject>> fillListData(List<JSONObject> originRow, JSONObject requestJsonData) {

        Map<String, JSONArray> tmpRequestData = new HashMap<>();

        List<JSONObject> cloneRow = new ArrayList<>();
        for (int j = 0; j < originRow.size(); j++) {
            JSONObject originCell = originRow.get(j);
            JSONObject cloneCell = originCell.clone();
            // 找到需要填充的list
            if(originCell.containsKey("elements")&&originCell.getJSONArray("elements").getJSONObject(0).containsKey("text")){
                String originText = originCell.getJSONArray("elements").getJSONObject(0).getString("text");
                // 匹配 ${xx.xxx} xx为list变量名，xxx为item属性名
                if (StringUtils.isNotBlank(originText) &&
                        originText.startsWith("${") &&
                        originText.endsWith("}") &&
                        originText.contains(".")) {

                    // 获取list名称
                    String listName = originText.substring(2, originText.indexOf("."));
                    // 属性名
                    String arrName = originText.substring(originText.indexOf(".") + 1, originText.length() - 1);

                    // 开始填充数据
                    if(!requestJsonData.containsKey(listName)){
                        // 请求中不包含当前list的填充数据
                        continue;
                    }
                    // 先从缓存获取
                    if (tmpRequestData.containsKey(listName)) {
                        JSONArray jsonArray = tmpRequestData.get(listName);
                        // 获取数据
                        if(jsonArray.isEmpty()){
                            continue;
                        }
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        if(!jsonObject.containsKey(arrName)){
                            // 对象中不包含该属性
                            continue;
                        }
                        cloneCell.getJSONArray("elements").getJSONObject(0).put("text",jsonObject.getString(arrName));
                        // 请求数据移除该组

                    } else {
                        JSONArray jsonArray = requestJsonData.getJSONArray(listName);
                        if(jsonArray.isEmpty()){
                            continue;
                        }
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        if(!jsonObject.containsKey(arrName)){
                            // 对象中不包含该属性
                            continue;
                        }
                        cloneCell.getJSONArray("elements").getJSONObject(0).put("text",jsonObject.getString(arrName));

                        tmpRequestData.put(listName, jsonArray);
                    }
                }
            }


            cloneRow.add(cloneCell);

        }




        return null;
    }

}
