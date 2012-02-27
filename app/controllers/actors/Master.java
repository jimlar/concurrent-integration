package controllers.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import static akka.pattern.Patterns.ask;
import static akka.pattern.Patterns.pipe;

import akka.dispatch.Future;
import akka.dispatch.Futures;
import akka.dispatch.Mapper;
import controllers.Result;
import play.Logger;

import java.util.*;

public class Master extends UntypedActor {
    private final ActorRef newsWorker;
    private final ActorRef resultsWorker;
    private final ActorRef scheduleWorker;

    public Master() {
        newsWorker = this.getContext().actorOf(new Props(NewsWorker.class));
        resultsWorker = this.getContext().actorOf(new Props(ResultsWorker.class));
        scheduleWorker = this.getContext().actorOf(new Props(ScheduleWorker.class));
    }

    public void onReceive(Object message) {
        List<Future<Object>> futures = new ArrayList<Future<Object>>();
        futures.add(ask(newsWorker, "doit", 5000));
        futures.add(ask(scheduleWorker, "doit", 5000));
        futures.add(ask(resultsWorker, "doit", 5000));

        Future<Iterable<Object>> aggregate = Futures.sequence(futures, getContext().system().dispatcher());
        final Future<Map> transformed = aggregate.map(new Mapper<Iterable<Object>, Map>() {
            public Map<String, String> apply(Iterable<Object> coll) {
                Map<String, String> response = new HashMap<String, String>();
                for (Object o : coll) {
                    Result result = (Result) o;
                    response.put(result.name, result.value);
                }
                return response;
            }
        });
        pipe(transformed).to(getSender());
    }
}
