package integrations;

public class News extends Integration {
    @Override
    protected String doRequestData() {
        return "No news yet";
    }
}
