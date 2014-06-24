import java.util.LinkedList;
import java.util.Queue;

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
		// TODO Auto-generated method stub
		
	}
}
