package controllers.actors;

import integrations.News;

public class NewsWorker extends Worker {
    public NewsWorker() {
        super(new News(), "n");
    }
}
