package preprocessor.fs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import preprocessor.interfaces.PWorker;
import preprocessor.interfaces.PreprocessingMangager;
import utils.ShellInteractor;

// The Free Surfer Manager to manager the preprocessing workers locally on the same server
class FSManager implements PreprocessingMangager {

	private int nmaxworker = 1;
	private Queue<Integer> taskQueue;
	private List<PWorker> workers;

	public FSManager(int nmaxworker) {
		this.nmaxworker = nmaxworker;
		this.taskQueue = new LinkedList<Integer>();
		this.workers = new ArrayList<PWorker>();
		for (int i = 0; i < nmaxworker; i++) {
			this.workers.add(new FSWorker(this));
		}
	}

	public int getNmaxworker() {
		return nmaxworker;
	}

	public void setNmaxworker(int nmaxworker) {
		this.nmaxworker = nmaxworker;
	}

	public List<PWorker> getWorkers() {
		return workers;
	}

	public void setWorkers(List<PWorker> workers) {
		this.workers = workers;
	}

	@Override
	public void start() {

	}

	@Override
	public void addTask(int pID) {

		// only start a new pworker when there are enough available workers
		PWorker curWorker = null;
		int workerid = 0;
		// find the worker who is not busy
		for (PWorker w : this.workers) {
			if (w.isBusy() == false) {
				curWorker = w;
				break;
			}

			workerid++;
		}

		// if there is no available worker at the moment, add the patient to the
		// queue
		if (curWorker == null) {
			this.pushTaskQueue(pID);
		} else {
			System.out.println("Processing Patient "
					+ new Integer(pID).toString() + " with worker "
					+ new Integer(workerid).toString());
			System.out.println("Worker " + new Integer(workerid).toString()
					+ " has been started...");
			this.startPworker(curWorker, pID);
			
		}
	}

	private synchronized void pushTaskQueue(int pID) {
		this.taskQueue.add(pID);
	}

	private synchronized Integer popTaskQueue() {
		return this.taskQueue.remove();
	}

	private synchronized boolean isQueueEmpty() {
		return this.taskQueue.size() == 0;
	}

	@Override
	public void startPworker(PWorker curWorker, int pID) {
		// TODO: define the parallism of preprocessing
		// whether it should be put in the curworker or the manager
		curWorker.preprocess(pID);
	}

	@Override
	public void notifyPreprocessingFinished(PWorker worker) {
		worker.setBusy(false);

		// check if there is any request waiting in the queue
		// if there is, start work on the first request
		// other wise the worker will be released
		if (this.isQueueEmpty() == false) {
			Integer pID = this.popTaskQueue();
			this.startPworker(worker, pID);
		}

	}
	
	public static void main(String[] args) {
		// DEBUG Walkthrough without listening
		PreprocessingMangager testm = new FSManager(1);
		testm.addTask(12); // 
		
	}
}
