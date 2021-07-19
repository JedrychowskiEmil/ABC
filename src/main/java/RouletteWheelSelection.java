import java.util.Random;

public final class RouletteWheelSelection{

  private final Random mRandom;


  public RouletteWheelSelection() {
    this(new Random());
  }

  public RouletteWheelSelection(Random random) {
    mRandom = random;
  }

  public Bee selectBee(Bee[] bees, double fitnessSum) {
    double value = fitnessSum * mRandom.nextDouble();

    double sum = 0;
    for (Bee bee : bees) {
      sum += bee.getFitnessValue();
      if (sum >= value) {
        return bee;
      }
    }

    throw new IllegalStateException("Should not come here.");
  }

}
