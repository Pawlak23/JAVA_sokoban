package gamefspp;

import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AppUtils {

    public static StopWatch STOPWATCH = StopWatch.create();
    public static List<Long> TIME_LIST = new ArrayList<>();

    public static void reloadTimer(){
        STOPWATCH.stop();
        TIME_LIST.add(STOPWATCH.getTime(TimeUnit.SECONDS));
        STOPWATCH.reset();
    }
}
