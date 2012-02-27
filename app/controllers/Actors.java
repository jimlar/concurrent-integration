package controllers;

import akka.actor.*;
import akka.actor.*;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActorFactory;
import akka.util.Duration;
import controllers.actors.Master;
import controllers.actors.NewsWorker;
import play.Logger;
import play.mvc.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Actors extends Controller {

    public static void index() {
        long start = System.currentTimeMillis();

        final HashMap<String, String> results = new HashMap<String, String>();
        System.getProperties().setProperty("config.resource", "akka.conf");
        
        ActorSystem system = ActorSystem.create("system");

        ActorRef listener = system.actorOf(new Props(new UntypedActorFactory() {
            public akka.actor.UntypedActor create() {
                return new akka.actor.UntypedActor() {
                    @Override
                    public void onReceive(Object message) throws Exception {
                        if (message instanceof Map) {
                            results.putAll((Map<String, String>) message);
                            getContext().system().shutdown();
                        } else {
                            this.getContext().actorOf(new Props(Master.class)).tell("doit", getSelf());
                        }
                    }
                };
            }
        }));

        listener.tell("doit");
        system.awaitTermination(Duration.create(10, TimeUnit.SECONDS));

        for (String key : results.keySet()) {
            renderArgs.put(key, results.get(key));
        }
        long time = System.currentTimeMillis() - start;
        renderTemplate("index.html", time);
    }
}