package namic.cad.preprocessor.interfaces;
public interface PreprocessingMangager{
   
    // Start the Preprocessing Manager to listen for the new tasks
    public void start();

    // When signal from the communication server is received, add task to the task queue
    // Start PWorker on the preprocessing server
    // Different kind of Preprocessing Manager may have different workers 
    public void addTask(int pID);

	public void startPworker(PWorker curWorker, int pID);

	public void notifyPreprocessingFinished(PWorker worker);

}
