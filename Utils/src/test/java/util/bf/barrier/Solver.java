package util.bf.barrier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class Solver {
	final int N;
	final float[][] data;
	final CyclicBarrier barrier;

	class Worker implements Runnable {
		int myRow;

		Worker(int row) {
			myRow = row;
		}

		public void run() {
			while (!done()) {
				processRow(myRow);

				try {
					barrier.await();
				} catch (Exception ex) {
					return;
				}
			}
		}

		private void processRow(int myRow2) {
			// TODO Auto-generated method stub

		}

		private boolean done() {
			// TODO Auto-generated method stub
			return false;
		}
	}

	public Solver(float[][] matrix) {
		data = matrix;
		N = matrix.length;
		Runnable barrierAction = new Runnable() {
			public void run() {
				mergeRows();
			}
		};
		barrier = new CyclicBarrier(N, barrierAction);

		List<Thread> threads = new ArrayList<>(N);
		for (int i = 0; i < N; i++) {
			Thread thread = new Thread(new Worker(i));
			threads.add(thread);
			thread.start();
		}

		// wait until done
		for (Thread thread : threads)
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	protected void mergeRows() {
		// TODO Auto-generated method stub

	}
}