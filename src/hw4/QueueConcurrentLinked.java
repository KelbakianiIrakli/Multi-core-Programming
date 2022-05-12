package hw4;

import java.util.Date;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class QueueConcurrentLinked {

	static Queue<String> queue = new ConcurrentLinkedQueue();;

	public static void main(String[] args) {
		try (Scanner in = new Scanner(System.in)) {
			System.out.print("Number of producer,consumer : ");
			int T = in.nextInt();
			System.out.print("Number of objects each consumer,producer should process: ");
			int n = in.nextInt();
			Date startDate = new Date();

			ExecutorService pes = Executors.newFixedThreadPool(T);
			ExecutorService ces = Executors.newFixedThreadPool(T);
			for (int i = 0; i < T; i++) {
				pes.submit(new ProducerThread(n));
				ces.submit(new ConsumerThread(n));
			}
			pes.shutdown();
			ces.shutdown();
			try {
				pes.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
				ces.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Date endDate = new Date();
			float timeIntervalInSeconds = (float) ((endDate.getTime() - startDate.getTime()) / 1000.0);
			System.out.println(timeIntervalInSeconds);
		}

	}

	public static class ProducerThread implements Runnable {
		long n;

		public ProducerThread(long n) {
			this.n = n;
		}

		@Override
		public void run() {
			String s = this.toString();
			while (n-- > 0) {
				System.out.println("added.");
				queue.add(s);
			}
		}
	}

	public static class ConsumerThread implements Runnable {
		long n;

		public ConsumerThread(long n) {
			this.n = n;
		}

		@Override
		public void run() {
			while (n-- > 0) {
				try {
					System.out.println("removed.");
					queue.remove();
				} catch (Exception e) {
				}

			}
		}
	}

}