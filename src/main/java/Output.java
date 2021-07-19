import java.util.Collections;
import java.util.List;

public final class Output {

  private final List<JobSchedule> jobSchedules;

  public Output(List<JobSchedule> jobSchedules, Input relatedInput) {

    this.jobSchedules = Collections.unmodifiableList(jobSchedules);
  }

  public int getMakespan() {
    return jobSchedules.get(jobSchedules.size() - 1).getFinishTime();
  }

  @Override
  public String toString() {
    StringBuilder b = new StringBuilder(1000);
    b.append("Makespan: ").append(getMakespan()).append('\n');
    b.append("Schedules:\n");

    for (JobSchedule schedule : jobSchedules) {
      b.append(schedule).append(",\n");
    }

    //remove last comma
    b.setLength(b.length() - 2);
    
    b.append("\n");

    return b.toString();
  }
}
