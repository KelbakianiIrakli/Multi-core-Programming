
import java.util.ArrayList;
import java.util.Date;

class PrimesFinderThread extends Thread {
	int low_val;
	int high_val;

	public PrimesFinderThread(String s, int low, int high) {
		low_val = low;
		high_val = high;
	}

	public void run() {
		PrimesFinder.primesFinderInRange(low_val, high_val);
	}
	
}

class workerThreadsManager {
	
	public static void assignJobsToThreads(int number, int numberOfThreads) {
	int sum = 0;
	ArrayList<PrimesFinderThread> threadsList = new ArrayList<>();
	ArrayList<Integer> numbers = PrimesFinder.subjobCalculator(number, numberOfThreads);
	for (int i = 0; i < numbers.size(); i++) {
		PrimesFinderThread temp = new PrimesFinderThread("Thread #", sum, sum += numbers.get(i));
		temp.start();
		threadsList.add(temp);
	}
	for (int i = 0; i < threadsList.size(); i++) {
		try {
			threadsList.get(i).join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
}

class PrimesFinder {

	public static ArrayList<Integer> subjobCalculator(int total, int divider) {
		ArrayList<Integer> numbers = new ArrayList<>();
		int rest = total % divider;
		double result = total / (double) divider;

		for (int i = 0; i < divider; i++) {
			if (rest-- > 0)
				numbers.add((int) Math.ceil(result));
			else
				numbers.add((int) Math.floor(result));
		}
		return numbers;
	}

	public static void primesFinderInRange(int lowerBound, int upperBound) {
		while (lowerBound < upperBound) {
			boolean flag = false;
			for (int i = 2; i <= Math.sqrt(lowerBound); i++) {
				if (lowerBound % i == 0) {
					flag = true;
					break;
				}
			}
			if (!flag && lowerBound != 0 && lowerBound != 1) {
				System.out.println(lowerBound + " ");
			}
			lowerBound++;
		}
	}

}
public class Exercise1{
	public static void main(String[] args) {
		int[] numbers = {10000000, 100000000};
		int[] threads = { 1, 2, 4, 8, 16 };
		ArrayList<Integer> results = new ArrayList<>();
		for (int n = 0; n < numbers.length; n++) {
			for (int t = 0; t < threads.length; t++) {
				Date startDate = new Date();
				workerThreadsManager.assignJobsToThreads(numbers[n], threads[t]);
				Date endDate = new Date();
				int numSeconds = (int) ((endDate.getTime() - startDate.getTime()) / 1000);
				results.add(numSeconds);

			}
		}
		System.out.println(results);
	}
}