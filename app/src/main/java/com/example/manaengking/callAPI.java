package com.example.manaengking;

import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.IOException;
import java.net.MalformedURLException;


public class callAPI {
    final static String key = "d7b4bc92edbb449d94d9";
    static String url = "https://openapi.foodsafetykorea.go.kr/api/" + key + "/COOKRCP01/json/1/100/";

    public static void getJson(String resource1, String resource2) {
        //if(!resource.equals("")) url += "RCP_NM=" + resource;
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                String result;

                try {
                    URL u = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) u.openConnection();

                    con.setReadTimeout(10000);
                    con.setConnectTimeout(10000);
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.setRequestMethod("GET");
                    con.setUseCaches(false);
                    con.connect();

                    InputStream is;
                    int responseCode = con.getResponseCode();
                    if(responseCode == con.HTTP_OK) { is = con.getInputStream(); }
                    else { is = con.getErrorStream(); }
                    InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                    BufferedReader br = new BufferedReader(isr);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    br.close();
                    con.disconnect();
                    result = sb.toString().trim();
                    System.out.println(result);
//                    CookingRecmd.textView.setText(result);
                    String results = "";


                    JSONObject root = (JSONObject) new JSONTokener(result).nextValue();
                    root = root.getJSONObject("COOKRCP01");
                    JSONObject tmp = root.getJSONObject("RESULT");
                    String msg = tmp.getString("MSG");
                    if(!msg.equals("정상처리되었습니다.")) {
                        CookingRecmd.textView.setText("API 호출 실패");
                        return;
                    }
                    JSONArray array = new JSONArray(root.getString("row"));
                    System.out.println(array.length());
                    int cnt = 0;
                    if(MainActivity.items >= 1) {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            if (obj.getString("RCP_PARTS_DTLS").contains(resource1)) {
                                results += (cnt + 1) + ") " + obj.getString("RCP_NM") + "\n";
                                results += "필요한 재료 : " + obj.getString("RCP_PARTS_DTLS") + "\n";
                                results += "\n\n";
                                cnt++;
                                if (cnt == 5) break;
                            }
                        }
                    }
                    results += "=================================\n\n";
                    cnt = 0;
                    if(MainActivity.items >= 2) {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            if (obj.getString("RCP_PARTS_DTLS").contains(resource2)) {
                                results += (cnt + 1) + ") " + obj.getString("RCP_NM") + "\n";
                                results += "필요한 재료 : " + obj.getString("RCP_PARTS_DTLS") + "\n";
                                results += "\n\n";
                                cnt++;
                                if (cnt == 5) break;
                            }
                        }
                    }
                    if(results.equals("")) CookingRecmd.textView.setText("추천할만한 요리가 없습니다!!");
                    CookingRecmd.textView.setText(results);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        th.start();
    }
}