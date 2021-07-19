import java.util.Arrays;

public final class Input {

  private final int machinesCount;
  private final Job[] jobs;

  private String name;

  public Input(int machinesCount, Job[] jobs) {
    if (machinesCount < 1) {
      throw new IllegalArgumentException("Num of machines must be > 0");
    }

    this.machinesCount = machinesCount;
    this.jobs = Arrays.copyOf(jobs, jobs.length);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Job[] getJobs() {
    return jobs;
  }

  public int getMachinesCount() {
    return machinesCount;
  }

  public int getJobsCount() {
    return jobs.length;
  }

  public Job[] getWithOrder(int[] order) {
    Job[] jobs = new Job[this.jobs.length];
    for (int i = 0; i < this.jobs.length; i++) {
      jobs[i] = this.jobs[order[i]];
    }

    return jobs;
  }

  @Override
  public String toString() {
    return "MachinesCount=" + machinesCount + ", Jobs" + Arrays.toString(jobs);
  }

}
