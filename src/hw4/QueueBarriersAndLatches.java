package hw4;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class QueueBarriersAndLatches {

	static Queue<String> queue = new ConcurrentLinkedQueue();;
	static CyclicBarrier barrier;
	static CountDownLatch countDownLatch;
	public static void main(String[] args) {
		try (Scanner in = new Scanner(System.in)) {
			System.out.print("Number of producer,consumer : ");
			int T = in.nextInt();
			System.out.print("Number of objects each consumer,producer should process: ");
			int n = in.nextInt();
			countDownLatch = new CountDownLatch(T);
		     Runnable barrierAction =
		    	       new Runnable() { public void run() {  }};
			try {
				barrier = new CyclicBarrier(T, barrierAction);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
	         try {
	             barrier.await();
	           } catch (InterruptedException ex) {
	             return;
	           } catch (BrokenBarrierException ex) {
	             return;
	           }
			String s = this.toString();
			while (n-- > 0) {
				queue.add(s);
				System.out.println("added.");
				
			}
			try {
				countDownLatch.countDown();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			try {
				countDownLatch.await();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
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
