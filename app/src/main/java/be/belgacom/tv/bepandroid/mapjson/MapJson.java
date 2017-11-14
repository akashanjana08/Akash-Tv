package be.belgacom.tv.bepandroid.mapjson;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by akash.sharma on 4/14/2017.
 */

public class MapJson {

    public static String getJsonForMap(Map<?, ?> map) {
        JSONObject json = null;
        try {
            json = new JSONObject(map);

            return json.toString();
        } catch (Exception e) {
            return "Invalid Map";
        }

    }
}
