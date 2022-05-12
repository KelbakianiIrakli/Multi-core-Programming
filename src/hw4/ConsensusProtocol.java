package hw4;
//The code from "The Art of Multiprocessor Programming" by Maurice Herlihy Nir Shavit Victor Luchangco Michael Spear
import java.util.concurrent.atomic.AtomicInteger;

class CASConsensus extends ConsensusProtocol {
	private final int FIRST = -1;
	private AtomicInteger r = new AtomicInteger(FIRST);

	public Object decide(Object value) {
		propose(value);
		int i = ThreadID.get();
		// it won
		if (r.compareAndSet(FIRST, i))
			return proposed[i];
		else
			// lost
			return proposed[r.get()];
	}
}

public abstract class ConsensusProtocol<T> implements Consensus<T> {
	private static final int N = 2;
	protected T[] proposed = (T[]) new Object[N]; // number of threads

	void propose(T value) {
		proposed[ThreadID.get()] = value;
	}

// find first thread
	abstract public T decide(T value);
}

