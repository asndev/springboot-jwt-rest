package ninja.asn.model;

/**
 * Created by hknd on 12.10.16.
 */
public class Greeting {

    private long id;
    private String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
