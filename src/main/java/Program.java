import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

public class Program {

    public static final int BEES_COUNT = 50;
    public static final int MISS_THRESHOLD = 10;

    public void run(String[] args) {

        Input[] inputs;
        try {
            inputs = readInputs(args);
        } catch (IOException e) {
            System.out.println("Error reading input " + e);
            return;
        }

        for (Input input : inputs) {
            System.out.println("--------" + input.getName() + "----------");

            ArtificialBeeColonySchedule scheduler = createSimpleABCScheduler(input);


            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(".\\output\\output_data.txt"))) {
                StringBuilder loadingBuilder = new StringBuilder(32);
                loadingBuilder.append("[");
                loadingBuilder.append("                              ");
                loadingBuilder.append("]");

                ArrayList<Output> outputs = new ArrayList<>();

                for (int i = 0; i < 30; i++) {
                    //Loading bar
                    loadingBuilder.setCharAt(i + 1, '#');
                    System.out.println("Loading " + loadingBuilder);

                    //Output
                    Output output = scheduler.schedule(input);
                    bufferedWriter.write(output.toString() + "\n");
                    outputs.add(output);
                }
                Optional<Output> bestOutput = outputs.stream().min(Comparator.comparingInt(Output::getMakespan));
                if(bestOutput.isPresent()){
                    bufferedWriter.write("\n\n\n\n");
                    bufferedWriter.write("The Best Result \n");
                    bufferedWriter.write(bestOutput.get().toString());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    protected Input[] readInputs(String[] args) throws IOException {
        if (args.length < 1) {
            throw new IllegalArgumentException("There must be arg with file path.");
        }

        File[] files = getFiles(args);
        Input[] inputs = new Input[files.length];

        for (int i = 0; i < files.length; i++) {
            inputs[i] = parseInput(new FileInputStream(files[i]));
            inputs[i].setName(files[i].getName());
        }

        return inputs;
    }

    protected File[] getFiles(String[] args) {
        String path = args[0];

        return new File[]{new File(path)};
    }

    protected Input parseInput(InputStream inputStream) throws IOException {
        SimpleTextParser parser = new SimpleTextParser();
        Input input = parser.parse(inputStream);
        inputStream.close();
        return input;
    }

    protected ArtificialBeeColonySchedule createSimpleABCScheduler(Input input) {
        MakespanCounter makespanCounter = new MakespanCounter(input.getJobs());
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(input.getJobsCount());
        TournamentOnlookerChooser onlookerChooser = new TournamentOnlookerChooser(new RouletteWheelSelection());

        Bee[] bees = new Bee[BEES_COUNT];
        for (int i = 0; i < BEES_COUNT; i++) {
            ILocalSearchStrategy strategy = new SelfAdaptiveSearchStrategy(makespanCounter);
            bees[i] = createBee(strategy, makespanCounter);
        }

        return new ArtificialBeeColonySchedule(bees, randomPositionGenerator, onlookerChooser, MISS_THRESHOLD, 1000);
    }

    protected Bee createBee(ILocalSearchStrategy strategy, MakespanCounter function) {
        return new Bee(strategy, function);
    }


}
