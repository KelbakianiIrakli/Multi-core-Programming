package hw1;

import java.util.ArrayList;
import java.util.Date;

class PrimesFinderThread extends Thread {
	int value = 0;
	int upperBound = -1;

	public PrimesFinderThread(int lowerBound, int upperBound) {
		value = lowerBound;
		this.upperBound = upperBound;
	}


	public void run() {
		PrimesFinder.primesFinder(value, upperBound);
	}

}

class workerThreadsManager {

	public static void assignJobsToThreads(int number, int numberOfThreads) {
		int sum = 0;
		ArrayList<PrimesFinderThread> threadsList = new ArrayList<>();
		ArrayList<Integer> numbers = PrimesFinder.subjobCalculator(number, numberOfThreads);
		for (int i = 0; i < numbers.size(); i++) {
			PrimesFinderThread temp = new PrimesFinderThread(sum, sum += numbers.get(i));
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

	public static void primesFinder(int value, int upperBound) {
		if (upperBound == -1) {
			if (isNumberPrime(value))
				System.out.println(value + " ");
		} else {
			while (value < upperBound) {
				if (isNumberPrime(value))
					System.out.println(value + " ");
				value++;
			}
		}
	}

	private static Boolean isNumberPrime(int number) {
		boolean flag = false;
		for (int i = 2; i <= Math.sqrt(number); i++) {
			if (number % i == 0) {
				flag = true;
				break;
			}
		}
		if (!flag && number != 0 && number != 1) {
			return true;
		}
		return false;
	}

}

public class Exercise1 {
	public static void main(String[] args) {
		int[] numbers = { 10000000, 100000000 };
		int[] threads = { 1, 2, 4, 8, 16 };
		ArrayList<Float> results = new ArrayList<>();
		for (int n = 0; n < numbers.length; n++) {
			for (int t = 0; t < threads.length; t++) {
				Date startDate = new Date();
				workerThreadsManager.assignJobsToThreads(numbers[n], threads[t]);
				Date endDate = new Date();
				float timeIntervalInSeconds = (float) ((endDate.getTime() - startDate.getTime()) / 1000.0);
				results.add(timeIntervalInSeconds);

			}
		}
		System.out.println(results);
	}
}