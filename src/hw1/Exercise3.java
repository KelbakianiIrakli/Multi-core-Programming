package hw1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Exercise3 {
	public static void main(String args[]) throws Exception {
		Integer N = 10;
		Integer threadNumbers = 5;
		LockBasedQueue<Integer> queue = new LockBasedQueue(N);

		ArrayList<Thread> threadsList = new ArrayList<>();
		for (int i = 0; i < threadNumbers; i++) {
			Thread producer = producer(N, queue);
			Thread consumer = consumerThread(queue);
			threadsList.add(producer);
			threadsList.add(consumer);
			producer.start();
			consumer.start();
		}
		for (int i = 0; i < threadsList.size(); i++) {
			try {
				threadsList.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static Thread consumerThread(LockBasedQueue<Integer> queue) {
		Thread consumer = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						queue.remove();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		return consumer;
	}

	private static Thread producer(Integer N, LockBasedQueue<Integer> queue) {
		Thread producer = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						Integer randomNum = ThreadLocalRandom.current().nextInt(0, N);
						queue.add(randomNum);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		return producer;
	}
}
