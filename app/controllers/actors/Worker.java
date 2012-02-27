package controllers.actors;

import akka.actor.UntypedActor;
import controllers.Result;
import integrations.Integration;
import play.Logger;

public class Worker extends UntypedActor {
    private final Integration service;
    private final String name;

    public Worker(Integration service, String name) {
        this.service = service;
        this.name = name;
    }

    public void onReceive(Object message) {
        getSender().tell(new Result(name, service.requestData()), getSelf());
    }
}
