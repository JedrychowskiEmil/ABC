import java.util.Arrays;
import java.util.List;

public class ArtificialBeeColonySchedule {

    private final Bee[] bees;
    private final int attemptsThreshold;
    private final RandomPositionGenerator positionGenerator;
    private final int onlookersCount;
    private final TournamentOnlookerChooser onlookerChooser;
    private final int numOfIterations;


    private int[] bestSolution;
    private int bestValue;

    public ArtificialBeeColonySchedule(Bee[] bees, RandomPositionGenerator generator, TournamentOnlookerChooser onlookerChooser,
                                       int attemptsThreshold, int numOfIterations) {

        if (attemptsThreshold < 1) {
            throw new IllegalArgumentException("Attempts threshold must be positive");
        }

        this.bees = Arrays.copyOf(bees, bees.length);
        positionGenerator = generator;
        this.onlookerChooser = onlookerChooser;
        this.attemptsThreshold = attemptsThreshold;

        //same size of onlookers as bees
        onlookersCount = bees.length;
        this.numOfIterations = numOfIterations;
    }


    public Output schedule(Input input) {
        bestSolution = null;
        bestValue = Integer.MAX_VALUE;

        //setup Bees
        for (Bee bee : bees) {
            scout(bee);
        }

        for (int i = 0; i < numOfIterations; i++) {
            //search near bees
            double fitnessSum = 0;
            for (Bee bee : bees) {
                localSearch(bee);

                fitnessSum += bee.getFitnessValue();
            }

            //send onlookers
            for (int j = 0; j < onlookersCount; j++) {
                Bee bee = onlookerChooser.selectBee(bees, fitnessSum);
                localSearch(bee);
            }

            //make scout and send them back
            for (Bee bee : bees) {
                if (bee.getCountOfMisses() >= attemptsThreshold) {
                    scout(bee);
                }
            }
        }

        Job[] bestSequence = input.getWithOrder(bestSolution);
        List<JobSchedule> jobSchedules = JobSchedule.createJobSchedules(bestSequence);

        return new Output(jobSchedules, input);
    }

    protected void localSearch(Bee bee) {
        boolean foundBetter = bee.searchForNewPosition();
        if (foundBetter && bee.positionValue < bestValue) {
            bestValue = bee.positionValue;
            bestSolution = bee.position;
        }
    }

    protected void scout(Bee bee) {
        int value = bee.sendScouting(positionGenerator);
        if (value < bestValue) {
            bestSolution = bee.getPosition();
            bestValue = value;
        }
    }
}
