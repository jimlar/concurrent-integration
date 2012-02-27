package integrations;

public abstract class MockIntegration {
    
    public String requestData() {
        try {
            Thread.sleep(500 + (int) Math.rint(1000));
        } catch (InterruptedException e) { }
        return doRequestData();
    }
    
    protected abstract String doRequestData();
}
