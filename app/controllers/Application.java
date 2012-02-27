package controllers;

import integrations.News;
import integrations.Results;
import integrations.Schedule;
import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {
    public static News news = new News();
    public static Results results = new Results();
    public static Schedule schedule = new Schedule();

    public static void index() {
        long start = System.currentTimeMillis();
        String n = news.requestData();
        String r = results.requestData();
        String s = schedule.requestData();
        long time = System.currentTimeMillis() - start;
        render(n, r, s, time);
    }
}