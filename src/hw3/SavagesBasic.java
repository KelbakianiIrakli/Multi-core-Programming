package hw3;

import java.util.LinkedList;

public class SavagesBasic {
	public static int numberOfSavages = 5;
	public static int numberOfMaximumServings = 3;

	public static void main(String[] args) {

		PotManager pot = new PotManager();
		System.out.println("Created Pot...");
		System.out.println("Maximum Serving number = " + numberOfMaximumServings + " ,Number off Savages = " + numberOfSavages);
		Savage savages[] = new Savage[numberOfSavages];
		for (int i = 0; i < numberOfSavages; i++) {
			System.out.println("Creating Savage " + (i + 1) + " thread.");
			savages[i] = new Savage(pot, i);
			savages[i].start();
		}

		Cook cook = new Cook(pot);
		System.out.println("Cook thread was created...");
		cook.start();

	}
}

class Savage extends Thread implements Runnable {
	PotManager pot;
	int id;
	boolean isHungry;

	public Savage(PotManager pot, int savageID) {
		this.pot = pot;
		this.id = savageID;
		this.isHungry = true;
	}

	public void run() {
		while (true) {

			try {
				if(this.isHungry) {
				pot.getserving(id);
				this.isHungry= false;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class Cook extends Thread implements Runnable {
	PotManager pot;

	public Cook(PotManager pot) {
		this.pot = pot;
	}

	public void run() {
		while (true)
			try {
				pot.fillpot();

			} catch (InterruptedException e) {

				e.printStackTrace();
			}

	}
}

class PotManager {

	public LinkedList<Integer> buf;
	private volatile boolean empty;


	PotManager() {
		buf = new LinkedList<Integer>();
		empty = true;

	}

	public void getserving(int id) throws InterruptedException {
		while (empty) {
		}
		;
		getServ(id);

	}

	private synchronized void getServ(int id) {
		if (!buf.isEmpty()) {
			buf.remove();
			System.out.println(
					"Savage " + (id + 1) + " has just eaten... Remaining servings = " + (buf.size()) + "]");
		}
		if (buf.isEmpty()) {
			empty = true;
		}
	}

	private synchronized void fillIn() {
		System.out.println("Cook filled the pot... [Number of serving in the pot are " + SavagesBasic.numberOfMaximumServings + "]");
		for (int i = 0; i < SavagesBasic.numberOfMaximumServings; i++) {
			buf.add(i);
		}
		empty = false;
	}

	public void fillpot() throws InterruptedException {
		while (!empty) {}
		fillIn();
	}
}
