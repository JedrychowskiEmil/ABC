public final class TournamentOnlookerChooser {

  private final RouletteWheelSelection wheelSelection;


  public TournamentOnlookerChooser(RouletteWheelSelection wheelSelection) {
    this.wheelSelection = wheelSelection;
  }

  public Bee selectBee(Bee[] bees, double fitnessSum) {
    Bee firstBee = wheelSelection.selectBee(bees, fitnessSum);
    Bee secondBee = wheelSelection.selectBee(bees, fitnessSum);

    //pick the better one
    if (firstBee.getPositionValue() < secondBee.getPositionValue()) {
      return firstBee;
    } else {
      return secondBee;
    }
  }
}
