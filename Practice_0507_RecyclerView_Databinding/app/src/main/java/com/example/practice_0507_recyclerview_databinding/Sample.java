package com.example.practice_0507_recyclerview_databinding;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class Sample extends BaseObservable {
	private String sampleName;
	private String sampleEmail;

	public Sample(String sampleName, String sampleEmail) {
		this.sampleName = sampleName;
		this.sampleEmail = sampleEmail;
	}

	@Bindable
	public String getSampleName() {
		return sampleName;
	}

	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
		notifyPropertyChanged(BR.sampleName);
	}

	@Bindable
	public String getSampleEmail() {
		return sampleEmail;
	}

	public void setSampleEmail(String sampleEmail) {
		this.sampleEmail = sampleEmail;
		notifyPropertyChanged(BR.sampleEmail);
	}
}
