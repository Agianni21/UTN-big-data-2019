import com.mongodb.client.*;
import org.bson.Document;
import twitter4j.*;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.*;
import java.util.ArrayList;
import java.util.List;


public class Main {

    Twitter twitter;

    static public void main(String[] args) {

        Main main = new Main();
        if (main.argsAreValid(args)) {

            String action = args[0];
            if (action.equals("find")) {
               main.scrapeTweetsAndStoreThem(args[1]);
            }
            else {
               main.filterTweetsAndPrintThem(args[1]);
            }
        } else {
            main.exitWithError("Invalid arguments");
        }
    }

    private void filterTweetsAndPrintThem(String filterRegex) {
        MongoCollection collection = getMongoCollection();
        FindIterable<Document> selectedResults = collection.find(regex("text", filterRegex));
        for (Document doc: selectedResults) {
            System.out.println(doc.toJson());
        }
    }


    private void scrapeTweetsAndStoreThem(String searchPhrase) {
        twitter = createTwitterInstance();
        Query query = buildTwitterQuery(searchPhrase);
        QueryResult result = processQuery(query);
        if (result == null) {
            return;
        }
        MongoCollection collection = getMongoCollection();
        storeQueryResult(result, collection);
    }

    private Twitter createTwitterInstance() {
        Configuration configuration = createTwitterInstanceConfiguration();
        TwitterFactory tf = new TwitterFactory(configuration);
        Twitter twitter = tf.getInstance();
        return twitter;
    }

    private Configuration createTwitterInstanceConfiguration() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("4vfqDOhQe2onAdeTKL5FFq8lK")
                .setOAuthConsumerSecret("dXsMHh1oYq3DcpXq6MEMxkYUD74r0zOXIoRfT3tEoO5d2poDaB")
                .setOAuthAccessToken("3108911170-iB2sAGhGuF2HZAYVQsRII8qL3dtwR7MHgN3Mo2e")
                .setOAuthAccessTokenSecret("BB0IWfFu2LlCwj0hRlJsckw7rvpAj2tmykK6hHIa448OI");
        Configuration configuration = cb.build();
        return configuration;
    }

    private Query buildTwitterQuery(String searchPhrase) {
        Query query = new Query(searchPhrase);
        query.count(100);
        return query;
    }

    private QueryResult processQuery(Query query) {
        try {
            return twitter.search(query);
        } catch (Exception e) {
            System.out.println("----------Query failed----------");
            System.out.println(e);
            return null;
        }
    }

    private void storeQueryResult(QueryResult result, MongoCollection collection) {
        List<Document> documents = new ArrayList<Document>();
        for (Status status : result.getTweets()) {
            Document document = new Document("user", status.getUser().getScreenName())
                    .append("text", status.getText());
            documents.add(document);
        }
        collection.insertMany(documents);
    }

    private MongoCollection getMongoCollection() {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("mydb");
        MongoCollection<Document> collection = database.getCollection("test");
        return collection;
    }

    private boolean argsAreValid(String[] args) {
        if (args.length == 2) {
            String actionArg = args[0];
            if (actionArg.equals("find") || actionArg.equals("filter")) {
                return true;
            }
        }
        return false;
    }

    private void exitWithError(String errorMessage) {
        System.out.println("-------- Something failed ---------");
        System.out.println(errorMessage);
        System.out.println("-----------------------------------");
    }
}
