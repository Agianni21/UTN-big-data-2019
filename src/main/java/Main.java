import com.mongodb.client.*;
import org.bson.Document;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import com.mongodb.ConnectionString;

import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.Arrays;
import com.mongodb.Block;

import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;


public class Main {
    static public void main(String[] args) {
        String searchWord = "futbol";
        String filterRegex = "[Cc]lub";
        System.out.println(args[0]);
        System.out.println(args[1]);
        // Conexion con mongito
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("mydb");
        MongoCollection<Document> collection = database.getCollection("test");

        /* FILTRO
        FindIterable<Document> queryResult = collection.find(regex("text", "[Cc]lub"));
        for (Document doc:queryResult) {
            System.out.println(doc.toJson());
        }

         */

        // Todo lo que es twitter
        /*
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("4vfqDOhQe2onAdeTKL5FFq8lK")
                .setOAuthConsumerSecret("dXsMHh1oYq3DcpXq6MEMxkYUD74r0zOXIoRfT3tEoO5d2poDaB")
                .setOAuthAccessToken("3108911170-iB2sAGhGuF2HZAYVQsRII8qL3dtwR7MHgN3Mo2e")
                .setOAuthAccessTokenSecret("BB0IWfFu2LlCwj0hRlJsckw7rvpAj2tmykK6hHIa448OI");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        Query query = new Query(searchWord);
        query.count(100);
        try {
            QueryResult result = twitter.search(query);
            List<Document> documents = new ArrayList<Document>();
            for (Status status: result.getTweets()) {
                Document document = new Document("user", status.getUser().getScreenName())
                        .append("text", status.getText());
                documents.add(document);
            }
            collection.insertMany(documents);
        } catch (Exception e) {
            System.out.println("Ooops..");
            System.out.println(e);
        }

         */
    }
}
