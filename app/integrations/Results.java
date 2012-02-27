package integrations;

public class Results extends MockIntegration {
    @Override
    protected String doRequestData() {
        return "These are your results";
    }
}
