
public class Bee {


  protected int[] position;
  protected int positionValue;
  private double fitnessValue;

  private int countOfMisses;

  private final ILocalSearchStrategy localSearchStrategy;
  private final MakespanCounter objectiveFunction;

  public Bee(ILocalSearchStrategy localSearchStrategy, MakespanCounter objectiveFunction) {
    this.localSearchStrategy = localSearchStrategy;
    this.objectiveFunction = objectiveFunction;
  }

  public int[] getPosition() {
    return position;
  }

  public int getPositionValue() {
    return positionValue;
  }

  public int getCountOfMisses() {
    return countOfMisses;
  }

  public double getFitnessValue() {
    return fitnessValue;
  }

  public int sendScouting(RandomPositionGenerator generator) {
    position = generator.generate();
    positionValue = objectiveFunction.evaluate(position);

    onNewFound();

    return positionValue;
  }

  public boolean searchForNewPosition() {
    int[] next = localSearchStrategy.getNext(position);

    int value = objectiveFunction.evaluate(next);
    if (value < positionValue) {
      positionValue = value;
      position = next;

      onBetterFound();

      return true;
    } else {
      countOfMisses++;

      return false;
    }
  }

  protected void onBetterFound() {
    onNewFound();
  }

  private void onNewFound() {
    countOfMisses = 0;

    fitnessValue = 1.0 / (1 + positionValue);
  }

}
