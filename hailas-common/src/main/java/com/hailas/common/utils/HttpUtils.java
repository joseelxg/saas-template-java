package com.hailas.common.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by Administrator on 2016/11/21.
 */
public class HttpUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);
    private static CloseableHttpClient httpclient;

    static {
        TrustStrategy trustStrategy = new TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                return true;
            }
        };
        SSLContext sslContext = null;
        try {
            sslContext = SSLContexts.custom()
                    .loadTrustMaterial(trustStrategy)
                    .build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        LayeredConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
        Registry<ConnectionSocketFactory> r = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new PlainConnectionSocketFactory())
                .register("https", sslsf)
                .build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(r);
        // Increase max total connection to 200
        cm.setMaxTotal(200);
        // Increase default max connection per route to 20
        cm.setDefaultMaxPerRoute(20);

        RequestConfig config = RequestConfig.custom()
                .setSocketTimeout(30000)
                .setConnectTimeout(30000)
                .setConnectionRequestTimeout(30000)
                .setCookieSpec(CookieSpecs.IGNORE_COOKIES)  // prevents cookie warn
                //.setProxy(new HttpHost("localhost", 8888))
                .build();
        httpclient = HttpClients.custom()
                .setConnectionManager(cm)
                .setDefaultRequestConfig(config)
                .setKeepAliveStrategy(new ConnectionKeepAliveStrategy() {
                    @Override
                    public long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
                        return 0;
                    }
                })
                .build();
    }

    public static CloseableHttpResponse get(String uri) throws IOException {
        HttpGet httpget = new HttpGet(uri);
        httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36");
        httpget.setHeader("Connection", "close");
        return httpclient.execute(httpget);
    }

    public static CloseableHttpResponse get(URI uri) throws IOException {
        return get(uri.toString());
    }

    public static CloseableHttpResponse get(String uri, Header[] headers) throws IOException {
        HttpGet httpget = new HttpGet(uri);
        httpget.setHeaders(headers);
        return httpclient.execute(httpget);
    }

    public static CloseableHttpResponse get(URI uri, Header[] headers) throws IOException {
        return get(uri.toString(), headers);
    }

    public static CloseableHttpResponse post(String uri, HttpEntity entity) throws IOException {
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36");
        httpPost.setHeader("Connection", "close");
        httpPost.setEntity(entity);
        return httpclient.execute(httpPost);
    }

    public static CloseableHttpResponse post(URI uri, HttpEntity entity) throws IOException {
        return post(uri.toString(), entity);
    }

    public static CloseableHttpResponse post(String uri, HttpEntity entity, Header[] headers) throws IOException {
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setHeaders(headers);
        httpPost.setEntity(entity);
        return httpclient.execute(httpPost);
    }

    public static CloseableHttpResponse post(URI uri, HttpEntity entity, Header[] headers) throws IOException {
        return post(uri.toString(), entity, headers);
    }

    public static JsonNode getJson(String uri) throws IOException {
        CloseableHttpResponse response = get(uri);
        HttpEntity entity = response.getEntity();
        try {
            if (entity != null) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readTree(entity.getContent());
            }
        } finally {
            EntityUtils.consume(entity);
            IOUtils.closeQuietly(response);
        }
        return null;
    }

    public static JsonNode getJson(String uri, Header[] headers) throws IOException {
        CloseableHttpResponse response = get(uri,headers);
        HttpEntity entity = response.getEntity();
        try {
            if (entity != null) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readTree(entity.getContent());
            }
        } finally {
            EntityUtils.consume(entity);
            IOUtils.closeQuietly(response);
        }
        return null;
    }

    public static <T> T getJson(String uri, Class<T> valueType) throws IOException {
        CloseableHttpResponse response = get(uri);
        HttpEntity entity = response.getEntity();
        try {
            if (entity != null) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(entity.getContent(), valueType);
            }
        } finally {
            EntityUtils.consume(entity);
            IOUtils.closeQuietly(response);
        }
        return null;
    }

    public static <T> T getJson(URI uri, Class<T> valueType) throws IOException {
        return getJson(uri.toString(), valueType);
    }

    public static <T> T getJson(String uri, Header[] headers, Class<T> valueType) throws IOException {
        CloseableHttpResponse response = get(uri, headers);
        HttpEntity entity = response.getEntity();
        try {
            if (entity != null) {
                ObjectMapper mapper = new ObjectMapper();
                T instance = mapper.readValue(entity.getContent(), valueType);
                setStatusCode(response,instance);
                return instance;
            }
        } finally {
            EntityUtils.consume(entity);
            IOUtils.closeQuietly(response);
        }
        return null;
    }

    public static <T> T getJson(URI uri, Header[] headers, Class<T> valueType) throws IOException {
        return getJson(uri.toString(), headers, valueType);
    }

    public static JsonNode postJson(String uri, HttpEntity param) throws IOException {
        CloseableHttpResponse response = post(uri, param);
        HttpEntity entity = response.getEntity();
        try {
            if (entity != null) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readTree(entity.getContent());
            }
        } finally {
            EntityUtils.consume(entity);
            IOUtils.closeQuietly(response);
        }
        return null;
    }

    public static JsonNode postJson(String uri, Header[] headers, HttpEntity param) throws IOException {
        CloseableHttpResponse response = post(uri, param, headers);
        HttpEntity entity = response.getEntity();
        try {
            if (entity != null) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readTree(entity.getContent());
            }
        } finally {
            EntityUtils.consume(entity);
            IOUtils.closeQuietly(response);
        }
        return null;
    }

    public static <T> T postJson(String uri, Object object, Class<T> responseType) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        HttpEntity entity = new StringEntity(mapper.writeValueAsString(object), ContentType.APPLICATION_JSON);
        return post(uri,entity,responseType);
    }

    public static <T> T postJson(String uri, Header[] headers, Object object, Class<T> responseType) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        HttpEntity entity = new StringEntity(mapper.writeValueAsString(object), ContentType.APPLICATION_JSON);
        return post(uri,entity,headers,responseType);
    }

    public static <T> T post(String uri, HttpEntity param, Class<T> valueType) throws IOException {
        CloseableHttpResponse response = post(uri, param);
        HttpEntity entity = response.getEntity();
        try {
            if (entity != null) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(entity.getContent(), valueType);
            }
        } finally {
            EntityUtils.consume(entity);
            IOUtils.closeQuietly(response);
        }
        return null;
    }

    public static <T> T post(URI uri, HttpEntity param, Class<T> valueType) throws IOException {
        return post(uri.toString(), param, valueType);
    }

    public static <T> T post(String uri, HttpEntity param, Header[] headers, Class<T> valueType) throws IOException {
        CloseableHttpResponse response = post(uri, param, headers);
        HttpEntity entity = response.getEntity();
        try {
            //System.out.println(EntityUtils.toString(entity));
            if (entity != null) {
                ObjectMapper mapper = new ObjectMapper();
                T instance = mapper.readValue(entity.getContent(), valueType);
                setStatusCode(response,instance);
                return instance;
            }
        } finally {
            EntityUtils.consume(entity);
            IOUtils.closeQuietly(response);
        }
        return null;
    }

    public static <T> T post(URI uri, HttpEntity param, Header[] headers, Class<T> valueType) throws IOException {
        return post(uri.toString(), param, headers, valueType);
    }

    public static InputStream downloadFile(String uri) throws IOException {
        HttpGet get = new HttpGet(uri);
        CloseableHttpResponse response = httpclient.execute(get);
        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            return null;
        }
        HttpEntity entity = response.getEntity();
        return entity.getContent();
    }

    public static InputStream downloadFile(String uri, HttpEntity param, Header[] headers) throws IOException {
        CloseableHttpResponse response = post(uri, param, headers);
        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            return null;
        }
        HttpEntity entity = response.getEntity();
        return entity.getContent();
    }

    public static void setStatusCode(CloseableHttpResponse response,Object object){
        Field[] fields = object.getClass().getSuperclass().getDeclaredFields();
        try {
            for(Field field:fields){
                if(field.getName().equals("statusCode")){
                    field.setAccessible(true);
                    field.set(object, response.getStatusLine().getStatusCode());
                    break;
                }
            }
        }catch (Exception e){

        }
    }

    public static void main(String[] args) throws IOException {
//        CloseableHttpResponse response = null;
//        try {
//            Header header = new BasicHeader("Authorization", "0CE29CEF87DE072AD7B016DF1D86DA90");
//            Header header2 = new BasicHeader("Accept", "application/json");
//            Header[] headers = {header, header2};
//            HttpEntity entity = new StringEntity("{\"bodyType\":\"json\",\"appId\":\"8a216da85bad7876015bb2a997fa01fb\",\"userAccounts \":[\"014877ac7d30440792a6e703f635fe91\"]}");
//            response = HttpUtils.post("https://dcsvip1imapp.cloopen.net/2013-12-26/Accounts/aaf98f89539b228f0153a258e0200dac/IM/GetUserState/?sig=0CE29CEF87DE072AD7B016DF1D86DA90", entity, headers);
//            System.out.println(EntityUtils.toString(response.getEntity()));
//        } finally {
//            IOUtils.closeQuietly(response);
//        }

        for (int i=0; i<100; ++i){
            System.out.println(i);
            CloseableHttpResponse response = get("https://www.baidu.com/");
            IOUtils.closeQuietly(response);
        }
    }
}
