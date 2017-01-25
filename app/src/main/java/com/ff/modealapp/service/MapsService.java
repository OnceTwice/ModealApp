package com.ff.modealapp.service;


import android.util.Log;

import com.ff.modealapp.network.JSONResult;
import com.ff.modealapp.vo.AddressVo;
import com.ff.modealapp.vo.ShopVo;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.Reader;
import java.net.HttpURLConnection;
import java.util.List;

public class MapsService {

    public AddressVo fetchAddress(String addr) {
        String url = "http://163.44.171.41:8080/modeal/map/addresstopoint";
        HttpRequest httpRequest = HttpRequest.get(url);
        httpRequest.contentType(HttpRequest.CONTENT_TYPE_FORM);
        httpRequest.accept(HttpRequest.CONTENT_TYPE_JSON);
        httpRequest.connectTimeout(3000);
        httpRequest.readTimeout(3000);
        int responseCode = httpRequest.send("addr=" + addr).code();

        if (responseCode != HttpURLConnection.HTTP_OK) {
            //에러처리
            throw new RuntimeException("Http Response:::: " + responseCode);
        }
        JSONResultAddress jsonResultAddress = fromJSON(httpRequest, JSONResultAddress.class);

        Log.d("jsonResultAddress--->", "" + jsonResultAddress);
        return jsonResultAddress.getData();
    }

    public List<ShopVo> fetchShopList() {

        String url = "http://163.44.171.41:8080/modeal/map/list";
        HttpRequest httpRequest = HttpRequest.get(url);
        httpRequest.contentType(HttpRequest.CONTENT_TYPE_JSON);
        httpRequest.accept(HttpRequest.CONTENT_TYPE_JSON);
        httpRequest.connectTimeout(3000);
        httpRequest.readTimeout(3000);

        int responseCode = httpRequest.code();

        if (responseCode != HttpURLConnection.HTTP_OK) {
            //에러처리
            throw new RuntimeException("Http Response: " + responseCode);
        }

        JSONResultUserList jsonResultUserList = fromJSON(httpRequest, JSONResultUserList.class);

        return jsonResultUserList.getData();
    }

    protected <V> V fromJSON(HttpRequest request, Class<V> target) {
        V v = null;

        try {
            Gson gson = new GsonBuilder().create();

            Reader reader = request.bufferedReader();
            v = gson.fromJson(reader, target);
            reader.close();

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return v;
    }

  /*  private abstract class JSONAddress<channel> {
        private channel channel;


        public channel getChannel() {
            return channel;
        }

        public void setChannel(channel channel) {
            this.channel = channel;
        }

        @Override
        public String toString() {
            return "JSONAddress{" +
                    "channel=" + channel +
                    '}';
        }
    }*/

    private class JSONResultAddress extends JSONResult<AddressVo> {

    /*
    JSON 문자열을 자바 객체로 변환
    @param request
    @param target
    @param <V>
    @return
    * */
    }

    private class JSONResultUserList extends JSONResult<List<ShopVo>> {

    /*
    JSON 문자열을 자바 객체로 변환
    @param request
    @param target
    @param <V>
    @return
    * */
    }
}
