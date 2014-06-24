package Preprocessor;
import java.util.LinkedList;
import java.util.Queue;

// The Free Surfer Manager to manager the preprocessing workers locally on the same server
class FSManager implements PreprocessingMangager{
	
   private int nmaxworker = 1;
   private Queue<Integer> taskQueue;

    public void PreprocessingManager(int nmaxworker){
        this.nmaxworker = nmaxworker;
        this.taskQueue = new LinkedList<Integer>();
    }

	@Override
	public void start() {
				
	}

	@Override
	public void addTask(int pID) {
		// add the ID in the queue & check whether there is enough workers at the moment
        taskQueue.add(pID);
        
        // only start a new pworker when there are enough available workers
        if( taskQueue.size() < nmaxworker){
        	
        }else{
        	
        }

		
	}
	
	public void startPworker(){
		
	}
}
