import java.io.InputStream;
import java.util.Scanner;

public final class SimpleTextParser {

  public Input parse(InputStream inputStream) {

    Scanner scanner = new Scanner(inputStream);

    int machinesCount = scanner.nextInt();
    int jobsCount = scanner.nextInt();

    int[][] data = new int[jobsCount][machinesCount];

    for (int i = 0; i < machinesCount; i++) {
      for (int j = 0; j < jobsCount; j++) {
        //save in index reverse order to have job columns in lines
        data[j][i] = scanner.nextInt();
      }
    }

    Job[] jobs = new Job[jobsCount];
    for (int i = 0; i < jobsCount; i++) {
      String name = String.valueOf(i + 1);
      Job job = new Job(name, data[i]); //job data are stored in line
      jobs[i] = job;
    }

    return new Input(machinesCount, jobs);
  }
}
