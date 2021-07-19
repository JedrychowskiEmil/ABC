import java.util.*;

public final class SelfAdaptiveSearchStrategy implements ILocalSearchStrategy {

  public static final int DEFAULT_SIZE = 200;

  private final Random random;
  private final MakespanCounter objectiveFunction;

  private final Queue<ILocalSearchStrategy> neighboursStrategies;
  private final Queue<ILocalSearchStrategy> winningNeighbourStrategies;

  private final int size;

  public SelfAdaptiveSearchStrategy(MakespanCounter objectiveFunction) {
    this(new Random(), objectiveFunction, DEFAULT_SIZE);
  }

  public SelfAdaptiveSearchStrategy(Random random, MakespanCounter objectiveFunction, int size) {

    if (size < 1) {
      throw new IllegalArgumentException("Size cannot be lower than zero, but was:" + size);
    }

    this.random = random;
    this.objectiveFunction = objectiveFunction;
    this.size = size;

    neighboursStrategies = new LinkedList<>();
    winningNeighbourStrategies = new LinkedList<>();
  }

  @Override
  public int[] getNext(int[] currentSolution) {
    if (neighboursStrategies.isEmpty()) {
      fillNeighboursList();
    }

    ILocalSearchStrategy strategy = neighboursStrategies.remove();

    int previousValue = objectiveFunction.evaluate(currentSolution);

    int[] next = strategy.getNext(currentSolution);

    int newValue = objectiveFunction.evaluate(next);

    if (newValue < previousValue) {
      winningNeighbourStrategies.add(strategy);
    }

    return next;
  }

  protected void fillNeighboursList() {
    int threshold = (size * 3) / 4;

    int added = 0;
    for (ILocalSearchStrategy localSearchStrategy : winningNeighbourStrategies) {
      if (added > threshold) {
        break;
      }
      added++;

      neighboursStrategies.add(localSearchStrategy);
    }

    winningNeighbourStrategies.clear();

    //fill the list to the correct size;
    for (int i = neighboursStrategies.size(); i < size; i++) {
      neighboursStrategies.add(getNextStrategy());
    }
  }

  protected ILocalSearchStrategy getNextStrategy() {
    final int random = this.random.nextInt(4);

    switch (random) {
      case 0:
        return new Swap();
      case 1:
        return new Insert();
      case 2:
        return new TwoSwaps();
      case 3:
        return new TwoInserts();

      default:
        throw new IllegalArgumentException();
    }
  }

  static class TwoInserts implements ILocalSearchStrategy {
    private final Insert insert;

    public TwoInserts() {
      this(new Insert());
    }

    public TwoInserts(Insert insert) {

      this.insert = insert;
    }

    @Override
    public int[] getNext(int[] currentSolution) {
      int[] firstInsert = insert.getNext(currentSolution);

      return insert.getNext(firstInsert);
    }
  }

  static class TwoSwaps implements ILocalSearchStrategy {
    private final Swap swap;

    public TwoSwaps() {
      this(new Swap());
    }

    public TwoSwaps(Swap swap) {

      this.swap = swap;
    }

    @Override
    public int[] getNext(int[] currentSolution) {
      int[] firstSwap = swap.getNext(currentSolution);

      return swap.getNext(firstSwap);
    }
  }

}
