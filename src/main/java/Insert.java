import java.util.Arrays;
import java.util.Random;

public class Insert implements ILocalSearchStrategy {

  private final Random random;

  public Insert() {
    this(new Random());
  }

  public Insert(Random random) {
    this.random = random;
  }

  @Override
  public int[] getNext(int[] currentSolution) {
    int length = currentSolution.length;

    int[] copyOf = Arrays.copyOf(currentSolution, currentSolution.length);
    int fromIndex = random.nextInt(length);

    int targetIndex = random.nextInt(length - 1);
    if (targetIndex >= fromIndex) {
      targetIndex++;
    }

    int temp = currentSolution[fromIndex];
    if (fromIndex < targetIndex) {
      System.arraycopy(currentSolution, fromIndex + 1, copyOf, fromIndex, targetIndex - fromIndex);
    } else {
      System.arraycopy(currentSolution, targetIndex, copyOf, targetIndex + 1, fromIndex - targetIndex);
    }

    copyOf[targetIndex] = temp;

    return copyOf;
  }
}
