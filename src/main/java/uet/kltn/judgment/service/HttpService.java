package uet.kltn.judgment.service;


import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uet.kltn.judgment.dto.ResponseDto;
import uet.kltn.judgment.util.JsonUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class HttpService {
    Logger logger = LoggerFactory.getLogger(HttpService.class);

    private static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");

    public Map<String, Object> responseHandle(Map<String, Object> result, Integer statusCode, String url, Map<String, Object> pathData,
                                              Map<String, Object> queryData, Map<String, String> header, String jsonBody, boolean isGet) throws IOException {
        return result;
    }

    public HttpService() {
    }

    protected Map<String, Object> get(String url, Map<String, Object> pathData, Map<String, Object> queryData, Map<String, String> header) throws IOException {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.hostnameVerifier((hostname, session) -> true);
        OkHttpClient client = builder
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        //add path params
        if (pathData != null && !pathData.isEmpty()) {
            for (Map.Entry<String, Object> param : pathData.entrySet()) {
                url = url.replace("%" + param.getKey() + "%", param.getValue().toString());
            }
        }
        //add query params
        StringBuilder queryParamsData = new StringBuilder();
        if (queryData != null && !queryData.isEmpty()) {
            url += "?";
            for (Map.Entry<String, Object> param : queryData.entrySet()) {
                if (param.getValue() != null) {
                    queryParamsData.append(param.getKey()).append("=").append(param.getValue()).append("&");
                }
            }
            url += queryParamsData.substring(0, queryParamsData.length() - 1);
        }
        Request request;
        if (header != null && !header.isEmpty()) {
            Headers headerBuild = Headers.of(header);
            request = new Request.Builder()
                    .url(url)
                    .headers(headerBuild)
                    .build();
        } else {
            request = new Request.Builder()
                    .url(url)
                    .build();
        }
        // Get response body
        Response response = client.newCall(request).execute();
        Integer statusCode = response.code();
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("data", response.body().string());
        } catch (Exception e) {
            logger.error("Cannot get string body data");
            logger.error(e.getMessage());
        }
        if (statusCode <400 && response.body().contentLength() == -1) {
            try {
                ResponseBody bodyData = response.body();
                byte[] byteData = bodyData.bytes();
                if (byteData.length > 0) {
                    result.put("byte_data", byteData);
                }
            } catch (Exception e) {
                logger.error("Cannot get byte data");
                logger.error(e.getMessage());
            }
        }
        result.put("status_code", statusCode);
        logger.info("GET, URL: " + url + ", Header: " + header);
        logger.info("RESPONSE: " + result);
        response.close();
        return result;
    }

    protected Map<String, Object> getByteData(String url, Map<String, Object> pathData, Map<String, Object> queryData, Map<String, String> header) throws IOException {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.hostnameVerifier((hostname, session) -> true);
        OkHttpClient client = builder
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        //add path params
        if (pathData != null && !pathData.isEmpty()) {
            for (Map.Entry<String, Object> param : pathData.entrySet()) {
                url = url.replace("%" + param.getKey() + "%", param.getValue().toString());
            }
        }
        //add query params
        StringBuilder queryParamsData = new StringBuilder();
        if (queryData != null && !queryData.isEmpty()) {
            url += "?";
            for (Map.Entry<String, Object> param : queryData.entrySet()) {
                if (param.getValue() != null) {
                    queryParamsData.append(param.getKey()).append("=").append(param.getValue()).append("&");
                }
            }
            url += queryParamsData.substring(0, queryParamsData.length() - 1);
        }
        Request request;
        if (header != null && !header.isEmpty()) {
            Headers headerBuild = Headers.of(header);
            request = new Request.Builder()
                    .url(url)
                    .headers(headerBuild)
                    .build();
        } else {
            request = new Request.Builder()
                    .url(url)
                    .build();
        }
        // Get response body
        Response response = client.newCall(request).execute();
        Integer statusCode = response.code();
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("byte_data", response.body().bytes());
        } catch (Exception e) {
            logger.error("Cannot get byte data");
            logger.error(e.getMessage());
        }
        result.put("status_code", statusCode);
        logger.info("GET, URL: " + url + ", Header: " + header);
        logger.info("RESPONSE: " + result);
        response.close();
        return result;
    }

    public <D> ResponseDto<D> get(String url, Object object, Class<D> clazz, String cookie) throws IOException {
        return this.get(url, object, clazz , new HashMap<>());
    }

    public <D> ResponseDto<List<D>> getList(String url, Object object, Class<D> clazz, String cookie) throws IOException {
        return this.getList(url, object, clazz , new HashMap<>());
    }

    public <D> ResponseDto<D> get(String url, Object object, Class<D> clazz, Map<String, String> header) throws IOException {
        Map<String, Object> response = this.get(url, null, JsonUtil.fromObjectToMap(object), header);
        int status = (int) response.get("status_code");
        response = this.responseHandle(response, status, url, null, JsonUtil.fromObjectToMap(object), header, null, true);
        ResponseDto<D> responseDto = new ResponseDto<>();
        responseDto.setStatus(status);
        responseDto.setData(JsonUtil.toDto(response.get("data"), clazz, true));
        return responseDto;
    }

    public <D> ResponseDto<List<D>> getList(String url, Object object, Class<D> clazz, Map<String, String> header) throws IOException {
        Map<String, Object> response = this.get(url, null, JsonUtil.fromObjectToMap(object), header);
        int status = (int) response.get("status_code");
        response = this.responseHandle(response, status, url, null, JsonUtil.fromObjectToMap(object), header, null, true);
        ResponseDto<List<D>> responseDto = new ResponseDto<>();
        responseDto.setStatus(status);
        responseDto.setData(JsonUtil.toDtoList(JsonUtil.fromJsonStringToList(response.get("data").toString()), clazz, true));
        return responseDto;
    }

    public <D> ResponseDto<D> post(String url, Object object, Class<D> clazz) throws IOException {
        return this.post(url, object, clazz, new HashMap<>());
    }

    public <D> ResponseDto<D> post(String url, Object object, Class<D> clazz, Map<String, String> header) throws IOException {
        String jsonBody;
        if (!(object instanceof String)) {
            jsonBody = JsonUtil.fromObjectToJsonString(object);
        } else {
            jsonBody = (String) object;
        }
        Map<String, Object> response = this.post(url, null, null, jsonBody, header);
        int status = (int) response.get("status_code");
        response = this.responseHandle(response, status, url, null, null, header, jsonBody, false);
        ResponseDto<D> responseDto = new ResponseDto<>();
        responseDto.setStatus(status);
        responseDto.setData(JsonUtil.toDto(response, clazz, true));
        return responseDto;
    }

    protected Map<String, Object> post(String url, Map<String, Object> pathData, Map<String, Object> queryData, String jsonBody, Map<String, String> header) throws IOException {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.hostnameVerifier((hostname, session) -> true);
        OkHttpClient client = builder
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        //add path params
        if (pathData != null && !pathData.isEmpty()) {
            for (Map.Entry<String, Object> param : pathData.entrySet()) {
                url = url.replace("%" + param.getKey() + "%", param.getValue().toString());
            }
        }
        //add query params
        StringBuilder queryParamsData = new StringBuilder();
        if (queryData != null && !queryData.isEmpty()) {
            url += "?";
            for (Map.Entry<String, Object> param : queryData.entrySet()) {
                if (param.getValue() != null) {
                    queryParamsData.append(param.getKey()).append("=").append(param.getValue()).append("&");
                }
            }
            url += queryParamsData.substring(0, queryParamsData.length() - 1);
        }
        RequestBody body = RequestBody.create(jsonBody, JSON_TYPE);
        Request request;
        if (header != null && !header.isEmpty()) {
            Headers headerBuild = Headers.of(header);
            request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .headers(headerBuild)
                    .build();
        } else {
            request = new Request.Builder()
                    .post(body)
                    .url(url)
                    .build();
        }
        // Get response body
        Response response = client.newCall(request).execute();
        Integer statusCode = response.code();
        Map<String, Object> result = JsonUtil.fromJsonStringToMap(response.body().string());
        result.put("status_code", statusCode);
        if (response.headers("Set-Cookie") != null) {
            List<String> cookies = response.headers("Set-Cookie");
            result.put("cookie", cookies);
        }

        logger.info("POST, URL: " + url + ", Header: " + header);
        logger.info("RESPONSE: " + result);
        return result;
    }
}
