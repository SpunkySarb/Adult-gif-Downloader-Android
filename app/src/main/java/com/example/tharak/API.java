package com.example.tharak;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class API implements Runnable{

static ArrayList<String> links = new ArrayList<>();
public  String Search;
public API(String Query){

        Search = Query;


}
    @Override
    public void run() {


        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://bing-image-search1.p.rapidapi.com/images/search?safeSearch=Off&q=" + Search + "%20bw%20gif")
                .get()
                .addHeader("x-rapidapi-host", "bing-image-search1.p.rapidapi.com")
                
                //PRovide your own key here, I have removed mine
                .addHeader("x-rapidapi-key", "your key here ")
                .build();

        try {

            okhttp3.Response response = client.newCall(request).execute();
            System.out.println("Success");
            String Json = response.body().string();
            // System.out.println(Json);

            JsonElement jsonElement = new JsonParser().parse(Json);

            JsonObject jsonObject = jsonElement.getAsJsonObject();


            //System.out.println( jsonObject.getAsJsonArray("value") );

            JsonArray results = jsonObject.getAsJsonArray("value");

            for (int i = 0; i < results.size(); i++) {


                JsonObject url = results.get(i).getAsJsonObject();

                // System.out.println(url.get("contentUrl").toString());

                links.add(url.get("contentUrl").toString());


            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }



    }

    public  ArrayList<String> links(){

    return links;
    }
}
