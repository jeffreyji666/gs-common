package com.ctrip.gs.common.json;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author wgji
 * 
 * @param <E>
 */
public class JEntryList<E> {
    public List<E> values;
    public long cacheTime;

    public JEntryList() {
    }

    public JEntryList(List<E> values) {
        this(values, System.currentTimeMillis());
    }

    public JEntryList(List<E> values, long cacheTime) {
        this.values = values;
        this.cacheTime = cacheTime;
    }

    public String toJson() {
        JSONArray vs = (JSONArray) JSON.toJSON(values);
        JSONObject json = new JSONObject();
        json.put("values", vs);
        json.put("cacheTime", cacheTime);
        return json.toJSONString();
    }

    public static <T> JEntryList<T> parseJson(String text, Class<T> clazz) {
        JSONObject json = JSON.parseObject(text);
        long cacheTime = json.getLongValue("cacheTime");
        JSONArray vs = json.getJSONArray("values");
        List<T> values = JSON.parseArray(vs.toJSONString(), clazz);
        return new JEntryList<T>(values, cacheTime);
    }
}
