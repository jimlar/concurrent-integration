package integrations;

public class Schedule extends Integration {
    @Override
    protected String doRequestData() {
        return "This is todays schedule";
    }
}
