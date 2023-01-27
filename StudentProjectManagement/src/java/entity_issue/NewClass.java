/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_issue;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import static com.mysql.cj.xdevapi.Type.JSON;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jdk.nashorn.internal.parser.JSONParser;

/**
 *
 * @author trung
 */
public class NewClass {

    public static void main(String[] args) throws IOException {
         OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .header("PRIVATE-TOKEN", "glpat-Q7gbM-Kqsm94-XUX8vWo")
                    .url("https://gitlab.com/api/v4/projects/38406507/issues?iids[]=23")
                    .build();

            
            Gson g = new Gson();  
            JsonParser parser = new JsonParser();
            Response response = client.newCall(request).execute();
            JsonArray objects = (JsonArray) parser.parse(response.body().string());
//            System.out.println();
            for(JsonElement object : objects){
                System.out.println(object.getAsJsonObject().get("due_date").toString().replaceAll("^\"|\"$", ""));
            }

    }
}
