package integrations;

public class Schedule extends MockIntegration {
    @Override
    protected String doRequestData() {
        return "This is todays schedule";
    }
}
