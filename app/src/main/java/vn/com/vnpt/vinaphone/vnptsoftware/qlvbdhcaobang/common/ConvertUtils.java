package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VietNH on 8/25/2017.
 */

public class ConvertUtils {

    public static <T> T fromJSON(Object object, Class<T> classConvert) {
        try {
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            T t = gson.fromJson(jsonElement, classConvert);
            return t;
        } catch (Exception ex) {
            return null;
        }
    }

    public static <T> List<T> fromJSONList(Object object, Class<T> classConvert) {
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            String source = gson.toJson(object);
            JsonParser parser = new JsonParser();
            JsonArray Jarray = parser.parse(source).getAsJsonArray();
            List<T> result = new ArrayList<T>();
            for (JsonElement obj : Jarray) {
                T t = gson.fromJson(obj, classConvert);
                result.add(t);
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
