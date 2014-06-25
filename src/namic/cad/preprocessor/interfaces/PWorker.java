package namic.cad.preprocessor.interfaces;

public interface PWorker {

	boolean isBusy();
	
	void setBusy(boolean busy);

	void preprocess(int pID);

}
