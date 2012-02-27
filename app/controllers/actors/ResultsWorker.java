package controllers.actors;

import integrations.Results;

public class ResultsWorker extends Worker {
    public ResultsWorker() {
        super(new Results(), "r");
    }
}
