package namic.cad.preprocessor.fs;

import namic.cad.models.Patient;
import namic.cad.preprocessor.interfaces.PWorker;
import namic.cad.preprocessor.interfaces.PreprocessingMangager;
import namic.cad.utils.ShellInteractor;

public class FSWorker implements PWorker{
    private int curPid = -1;
    
    private boolean busy = false;
    
    private PreprocessingMangager myManager = null;
	
	public int getCurPid() {
		return curPid;
	}

	public void setCurPid(int curPid) {
		this.curPid = curPid;
	}

	public PreprocessingMangager getMyManager() {
		return myManager;
	}

	public void setMyManager(PreprocessingMangager myManager) {
		this.myManager = myManager;
	}

	@Override
	public void setBusy(boolean busy) {
		this.busy = busy;
	}

	public FSWorker(PreprocessingMangager myManager) {
		super();
		this.myManager = myManager;
	}

	@Override
	public boolean isBusy() {
		return this.busy;
	}

	@Override
	public void preprocess(int pID) {
		busy = true;
		curPid = pID;
		
		// Lookup the database to retrieve the image file of the patient with pID
		Patient p = this.retrievePatient(pID);
				
		// start the command with freesurfer recon-all with a subject name of the patient's serial number
		this.runFsRecon(p);
		
		// Notify the manager when the preprocessing is done
		this.myManager.notifyPreprocessingFinished(this);
	}

	private void runFsRecon(Patient p) {
		System.out.println("process...process...process...");
	    System.out.println(new ShellInteractor().executeCommand("recon-all -i " + p.getOriginal_fpath() +
	    		" -subject " + p.getSerial() + " -all"));
	}

	private Patient retrievePatient(int pID) {
		// dummy data for debugging
		Patient p = new Patient();
		p.setEmail("lsqshr@gmail.com");
		p.setOriginal_fpath("/root/tmp_scans/siqi.nii");
		p.setSerial("123ABC");
		
		return p;
	}

}
