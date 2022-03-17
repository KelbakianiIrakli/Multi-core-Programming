package hw1;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class Exercise2 {
	static AtomicLong counter = new AtomicLong();

	public static void main(String[] args) {
		int[] numbers = { 10000000, 100000000 };
		int[] threads = { 1, 2, 4, 8, 16 };
		ArrayList<Float> results = new ArrayList<>();

		for (int n = 0; n < numbers.length; n++) {
			for (int t = 0; t < threads.length; t++) {
				counter.set(0);
				Date startDate = new Date();
				threadManager2(numbers[n], threads[t]);
				Date endDate = new Date();
				float timeIntervalInSeconds = (float) ((endDate.getTime() - startDate.getTime()) / 1000.0);
				results.add(timeIntervalInSeconds);

			}
		}
		System.out.println(results);
	}

	private static void threadManager2(int number, int threads) {
		ArrayList<PrimesFinderThread2> threadsList = new ArrayList<>();
		for (int i = 0; i < threads; i++) {
			PrimesFinderThread2 temp = new PrimesFinderThread2(Exercise2.counter.get(), number);
			temp.start();
			threadsList.add(temp);
		}
		for (int i = 0; i < threadsList.size(); i++) {
			try {
				threadsList.get(i).join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

class PrimesFinderThread2 extends Thread {
	
	long value = 0;
	int upperBound = -1;

	public PrimesFinderThread2(long lowerBound, int upperBound) {
		value = lowerBound;
		this.upperBound = upperBound;
	}

	public PrimesFinderThread2(int value) {
		this.value = value;
	}

	public void run() {
		primesFinder2(value, upperBound);
	}

	public void primesFinder2(long value, int upperBound) {
		long temp = Exercise2.counter.getAndIncrement();
		while (temp < upperBound) {
			if (isNumberPrime(temp))
				System.out.println(temp + " ");

			temp = Exercise2.counter.getAndIncrement();
		}
	}

	private static Boolean isNumberPrime(long number) {
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