import java.util.Arrays;

public final class MakespanCounter {

  private final int jobsLength;
  private final Job[] jobs;

  public MakespanCounter(Job[] jobs) {

    jobsLength = jobs.length;
    this.jobs = Arrays.copyOf(jobs, jobsLength);
  }


  public int evaluate(int[] solution) {
    return countMakespan(solution);
  }

  public int countMakespan(int[] solution) {
    if (solution.length != jobsLength) {
      String message = "Solution length %d and Jobs length %d does not match";
      throw new IllegalArgumentException(String.format(message, solution.length, jobsLength));
    }

    int delaySum = 0;

    for (int i = 1; i < jobsLength; i++) {
      Job previous = jobs[solution[i - 1]];
      Job next = jobs[solution[i]];

      delaySum += previous.getDepartureDelay(next);
    }

    int lastJobDuration = jobs[solution[jobsLength - 1]].getTotalDuration();

    return delaySum + lastJobDuration;
  }

}
