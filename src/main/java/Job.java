import java.util.Arrays;

public final class Job {


  private final String name;
  private final int[] durations;
  private final int totalDuration;

  public Job(String name, int[] durations) {

    if (durations.length == 0) {
      throw new IllegalArgumentException("Each job has to have at least one operation");
    }

    this.name = name;
    this.durations = Arrays.copyOf(durations, durations.length);

    totalDuration = countTotalDuration(this.durations);
  }

  public String getName() {
    return name;
  }

  public int[] getDurations() {
    return Arrays.copyOf(durations, durations.length);
  }

  public int getDurationsCount() {
    return durations.length;
  }

  public int getTotalDuration() {
    return totalDuration;
  }

  protected static int countTotalDuration(int[] durations) {
    int sum = 0;

    for (int duration : durations) {
      sum += duration;
    }

    return sum;
  }

  public int getDepartureDelay(Job next) {
    int[] nextDurations = next.durations;
    final int length = nextDurations.length;
    int delay = durations[0]; //need to wait at start

    int prevLeaveTime = 0;
    int nextComeTime = 0;
    final int end = length - 1;
    for (int i = 0; i < end; i++) {
      prevLeaveTime += durations[i + 1];
      nextComeTime += nextDurations[i];

      if (nextComeTime < prevLeaveTime) {
        int diff = prevLeaveTime - nextComeTime;
        delay += diff;
        nextComeTime += diff;
      }
    }

    return delay;
  }

  @Override
  public String toString() {
    return "Job" + name + ", Durations=" + Arrays.toString(durations);
  }

}
