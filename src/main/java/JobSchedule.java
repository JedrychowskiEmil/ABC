import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class JobSchedule {

  private final Job job;
  private final int startTime;

  private final Interval[] jobIntervals;

  public JobSchedule(Job job, int startTime) {
    if (startTime < 0) {
      throw new IllegalArgumentException(String.format("Start time must be > 0", startTime));
    }

    this.job = job;
    this.startTime = startTime;

    jobIntervals = countIntervals(this.job.getDurations(), startTime);
  }

  public int getStartTime() {
    return startTime;
  }

  public Interval[] getJobIntervals() {
    return jobIntervals;
  }

  public int getDelay(Job next) {
    return job.getDepartureDelay(next);
  }

  public int getFinishTime() {
    return jobIntervals[jobIntervals.length - 1].getEnd();
  }

  private static Interval[] countIntervals(int[] durations, int start) {
    int[] startingTimes = countStartingTimes(durations, start);
    int[] endingTimes = countDepartureTimes(durations, start);

    return countIntervals(startingTimes, endingTimes);
  }

  private static Interval[] countIntervals(int[] startingTimes, int[] endingTimes) {
    Interval[] intervals = new Interval[startingTimes.length];
    for (int i = 0; i < startingTimes.length; i++) {
      intervals[i] = new Interval(startingTimes[i], endingTimes[i]);
    }

    return intervals;
  }

  private static int[] countDepartureTimes(int[] durations, int start) {
    int[] times = countStartingTimes(durations, start);

    for (int i = 0; i < durations.length; i++) {
      times[i] += durations[i];
    }

    return times;
  }

  private static int[] countStartingTimes(int[] durations, final int start) {
    int[] startingTimes = new int[durations.length];

    int time = start;
    startingTimes[0] = start;
    for (int i = 1; i < durations.length; i++) {
      time += durations[i - 1];
      startingTimes[i] = time;
    }

    return startingTimes;
  }

  public static List<JobSchedule> createJobSchedules(Job[] jobs) {
    List<JobSchedule> schedules = new ArrayList<>(jobs.length);

    JobSchedule previous = new JobSchedule(jobs[0], 0);
    schedules.add(previous);

    for (int i = 1; i < jobs.length; i++) {
      Job next = jobs[i];
      int nextStart = calculateNextStart(previous, next);

      previous = new JobSchedule(next, nextStart);
      schedules.add(previous);
    }
    return schedules;
  }

  protected static int calculateNextStart(JobSchedule previous, Job next) {
    return previous.getStartTime() + previous.getDelay(next);
  }

  @Override
  public String toString() {
    return "Schedule for job " + job.getName() + " Running intervals: " + Arrays.toString(getJobIntervals());
  }
}
