package integrations;

public class Results extends Integration {
    @Override
    protected String doRequestData() {
        return "These are your results";
    }
}
