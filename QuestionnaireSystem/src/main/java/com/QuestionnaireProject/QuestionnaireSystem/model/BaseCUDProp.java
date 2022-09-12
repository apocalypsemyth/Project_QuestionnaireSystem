package com.QuestionnaireProject.QuestionnaireSystem.model;

import java.io.Serializable;

public class BaseCUDProp implements Serializable {
	
	private static final long serialVersionUID = 5962989125925204452L;
	
	private boolean isCreated;
	
	private boolean isUpdated;
	
	private boolean isDeleted;
	
	public BaseCUDProp() {
	}
	
	public boolean getIsCreated() {
		return isCreated;
	}

	public void setIsCreated(boolean isCreated) {
		this.isCreated = isCreated;
	}

	public boolean getIsUpdated() {
		return isUpdated;
	}

	public void setIsUpdated(boolean isUpdated) {
		this.isUpdated = isUpdated;
	}

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
}
