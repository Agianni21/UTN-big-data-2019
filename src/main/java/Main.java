import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class Main {
    static public void main(String[] args) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("4vfqDOhQe2onAdeTKL5FFq8lK")
                .setOAuthConsumerSecret("dXsMHh1oYq3DcpXq6MEMxkYUD74r0zOXIoRfT3tEoO5d2poDaB")
                .setOAuthAccessToken("3108911170-iB2sAGhGuF2HZAYVQsRII8qL3dtwR7MHgN3Mo2e")
                .setOAuthAccessTokenSecret("BB0IWfFu2LlCwj0hRlJsckw7rvpAj2tmykK6hHIa448OI");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        Query query = new Query("macri");
        query.count(100);
        try {
            QueryResult result = twitter.search(query);
            for (Status status: result.getTweets()) {
                System.out.println("@" + status.getUser().getScreenName() + ":" +  status.getText());
            }
        } catch (Exception e) {
            System.out.println("Ooops..");
            System.out.println(e);
        }

    }
}
