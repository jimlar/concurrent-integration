package controllers;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.Await;
import akka.dispatch.Future;
import akka.util.Duration;
import controllers.actors.Master;
import play.mvc.Controller;

import java.util.Map;

import static akka.pattern.Patterns.ask;

public class Actors extends Controller {

    public static void index() throws Exception {
        long start = System.currentTimeMillis();

        System.getProperties().setProperty("config.resource", "akka.conf");

        ActorSystem system = ActorSystem.create("system");

        ActorRef master = system.actorOf(new Props(Master.class));
        Future<Object> future = ask(master, "doit", 5000);
        Map<String, String> result = (Map<String, String>) Await.result(future, Duration.parse("5 seconds"));

        system.shutdown();

        for (String key : result.keySet()) {
            renderArgs.put(key, result.get(key));
        }
        long time = System.currentTimeMillis() - start;
        renderTemplate("index.html", time);
    }
}