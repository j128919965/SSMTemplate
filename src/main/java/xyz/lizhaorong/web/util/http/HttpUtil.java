package xyz.lizhaorong.web.util.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil<S,T> {
    private static final ObjectMapper jsonMapper = new ObjectMapper();
    Map<String,String> headers = new HashMap<>();

    public void setHeader(String k,String v){
        headers.put(k,v);
    }

    public T post(String url, S data , Class<T> retClass) throws Exception {
        String json = jsonMapper.writeValueAsString(data);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        StringEntity entity = new StringEntity(json, Consts.UTF_8);
        entity.setContentType("application/json");
        HttpPost httpPost = new HttpPost(url);
        //设置http头
        headers.forEach(httpPost::setHeader);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        int status = response.getStatusLine().getStatusCode();
        System.out.println(status);
        String back = EntityUtils.toString(response.getEntity());

        response.close();
        httpClient.close();
        return jsonMapper.readValue(back,retClass);
    }

}
