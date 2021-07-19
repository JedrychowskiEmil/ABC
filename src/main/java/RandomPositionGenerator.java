import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class RandomPositionGenerator
{


  private final int mLength;
  private final Random random;

	public RandomPositionGenerator(int length)
	{
		this(length, new Random());
	}

	public RandomPositionGenerator(int length, Random random)
	{
		if (length < 1)
		{
			throw new IllegalArgumentException("Length must be positive");
		}

	  mLength = length;
	  this.random = random;
	}



	public int[] generate()
	{
	  int length = mLength;

		List<Integer> integers = new ArrayList<>(length);
		for (int i = 0; i < length; i++)
		{
			integers.add(i);
		}

		int[] startSolution = new int[length];

		for (int i = 0; i < length; i++)
		{
			int size = integers.size();
		  int position = random.nextInt(size);

			int index = integers.get(position);

			startSolution[index] = i;
			integers.remove(position);
		}

		return startSolution;
	}


}
