package ecommerceautomation.utils;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonUtils {

    private JSONObject jsonObject;

    public JsonUtils(String filePath) throws Exception {
        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader(filePath);
        jsonObject = (JSONObject) parser.parse(reader);
    }

    public String getString(String key) {
        return (String) jsonObject.get(key);
    }

    public long getLong(String key) {
        return (Long) jsonObject.get(key);
    }

    public double getDouble(String key) {
        return (Double) jsonObject.get(key);
    }

    // New method: Get list of strings from JSON array
    public List<String> getStringList(String key) {
        List<String> list = new ArrayList<>();
        JSONArray array = (JSONArray) jsonObject.get(key);
        if (array != null) {
            for (Object obj : array) {
                list.add((String) obj);
            }
        }
        return list;
    }
}
