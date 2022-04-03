package ru.elerphore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.codec.digest.DigestUtils;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import ru.elerphore.data.Day;
import ru.elerphore.data.Hash;
import ru.elerphore.data.Table;
import ru.elerphore.data.TablesResponse;
import ru.elerphore.utils.RequestClient;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * Hello world!
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class App {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final MessageDigest md = MessageDigest.getInstance("MD5");
    static RequestClient client = new RequestClient();

    public App() throws NoSuchAlgorithmException {
    }

    public static void main( String[] args ) throws IOException {
        TablesResponse tables = null;
        try {
            tables = client.getTables();
        } catch (IOException e) {
            client.telegramErrorRequest();
        }

            CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
            CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

            mapper.registerModule(new JavaTimeModule());
            mapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);

            MongoClientSettings settings = MongoClientSettings.builder()
                    .codecRegistry(codecRegistry)
                    .build();
            MongoClient mClient = MongoClients.create(settings);
            MongoDatabase mDatabase = mClient.getDatabase("home");
            MongoCollection<Day> scheduleCollection = mDatabase.getCollection("schedule", Day.class);
            MongoCollection<Hash> hashesCollection = mDatabase.getCollection("hashes", Hash.class);

            if(hashesCollection.find().cursor().hasNext()) {
                Logger.getGlobal().warning("old hash exists");
                var oldHash = hashesCollection.find().first();

                try {
                    if(oldHash.getHash().equals(DigestUtils.md5Hex(tables.toString()))) {
                        Logger.getGlobal().warning("hashes are the same");
                    } else {
                        Logger.getGlobal().warning("hashes are NOT the same");
                        Logger.getGlobal().warning("Updating pairs");

                        for(Table table : tables.items) {
                            Document filterByDayName = new Document("dayName", table.getDate().getDayOfWeek().name());
                            var day = scheduleCollection.find(filterByDayName).first();
                            day.setPairs(table.getPairs());
                            scheduleCollection.findOneAndReplace(filterByDayName, day);
                        }
                        Logger.getGlobal().warning("Pairs updated");

                        oldHash.setHash(DigestUtils.md5Hex(tables.toString()));
                        Document filterById = new Document("_id", oldHash.getId());
                        hashesCollection.findOneAndReplace(filterById, oldHash);
                        Logger.getGlobal().warning("hashes updated");
                    }
                } catch (NullPointerException e) {
                    client.telegramErrorRequest();
                }

            } else {
                Logger.getGlobal().warning("old hash doesn't exists");
                hashesCollection.insertOne(new Hash(DigestUtils.md5Hex(tables.toString())));
                Logger.getGlobal().warning("new hash saved");
                Logger.getGlobal().warning("Saving days");

                for(Table tb : tables.items) {
                    scheduleCollection.insertOne(new Day(tb.getDate().getDayOfWeek(), tb.getPairs()));
                }
            }

            for(Day day : scheduleCollection.find()) {
                client.telegramRequest(day);
            }
    }
}
