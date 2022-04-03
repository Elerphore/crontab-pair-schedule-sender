package ru.elerphore;

import static org.junit.Assert.assertTrue;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws IOException {
        try(CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = new HttpPost("https://api.telegram.org/bot5115649258:AAH6ZUs3IOXnKJVZCaghPXhFZ77hbtrO8FU/sendMessage?chat_id=735391827");
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Accept", "application/json");
            String json = "{\"text\":\"жопа\"}";
            System.out.println(json);
            request.setEntity(new StringEntity(json, Charset.defaultCharset()));
            client.execute(request);

//            return response;
        }
    }
}
