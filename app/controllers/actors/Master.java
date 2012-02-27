package controllers.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import controllers.Result;

import java.util.Map;

public class Master extends UntypedActor {
    private final ActorRef newsWorker;
    private final ActorRef resultsWorker;
    private final ActorRef scheduleWorker;

    private int numMessages = 0;
    private Map results;

    public Master(Map results) {
        this.results = results;
        newsWorker = this.getContext().actorOf(new Props(NewsWorker.class), "newsWorker");
        resultsWorker = this.getContext().actorOf(new Props(ResultsWorker.class), "resultsWorker");
        scheduleWorker = this.getContext().actorOf(new Props(ScheduleWorker.class), "scheduleWorker");
    }

    public void onReceive(Object message) {
        if (message instanceof Result) {
            Result result = (Result) message;
            results.put(result.name, result.value);
            numMessages++;
            if (numMessages == 3) {
                getContext().system().shutdown();
            }

        } else {
            newsWorker.tell("doit", getSelf());
            scheduleWorker.tell("doit", getSelf());
            resultsWorker.tell("doit", getSelf());
        }
    }
}
