package ru.elerphore.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import ru.elerphore.data.Day;
import ru.elerphore.data.TablesResponse;

import java.io.IOException;
import java.nio.charset.Charset;

public class RequestClient {
    private static final ObjectMapper mapper = new ObjectMapper();

    public RequestClient() {
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
    }

    public TablesResponse getTables() throws IOException {
        try(CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet("https://ivanik.ru/mpk/api/tables/latest/%D0%98%D0%A1%D0%BF-19-2");
            TablesResponse response = client.execute(request, classicHttpResponse -> mapper.readValue(classicHttpResponse.getEntity().getContent(), TablesResponse.class));

            return response;
        }
    }

    public void telegramRequest(Day day) throws IOException {
        try(CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(System.getenv("TG_URI"));
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Accept", "application/json");
            String json = "{\"text\":" + "\"" + day.toString() + "\"";
            System.out.println(json);
            request.setEntity(new StringEntity(json, Charset.defaultCharset()));
            client.execute(request);
        }
    }

    public void telegramErrorRequest() throws IOException {
        try(CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(System.getenv("TG_URI"));
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Accept", "application/json");
            String json = "{\"text\": \"Cannot parse the data\"";
            request.setEntity(new StringEntity(json, Charset.defaultCharset()));
            client.execute(request);
        }
    }

}
