package integrations;

public class News extends MockIntegration {
    @Override
    protected String doRequestData() {
        return "No news yet";
    }
}
