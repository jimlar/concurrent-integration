package controllers.actors;

import integrations.Schedule;

public class ScheduleWorker extends Worker {
    public ScheduleWorker() {
        super(new Schedule(), "s");
    }
}
