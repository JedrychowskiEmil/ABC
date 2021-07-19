import java.util.Arrays;
import java.util.Random;

public class Swap implements ILocalSearchStrategy
{

  private final Random random;

	public Swap()
	{
		this(new Random());
	}

	public Swap(Random random)
	{

	  this.random = random;
	}

	@Override
	public int[] getNext(int[] currentSolution)
	{
		int length = currentSolution.length;

		int[] copyOf = Arrays.copyOf(currentSolution, currentSolution.length);

	  int firstIndex = random.nextInt(length);

		//uniformly select from remaining indexes
	  int secondIndex = random.nextInt(length - 1);
		if (secondIndex >= firstIndex)
		{
			secondIndex++;
		}

		copyOf[secondIndex] = currentSolution[firstIndex];
		copyOf[firstIndex] = currentSolution[secondIndex];

		return copyOf;
	}
}
