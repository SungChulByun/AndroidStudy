package com.example.practice_0507_recyclerview_databinding;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class MainViewModel extends BaseViewModel{
	private List<Object> sampleList;
	private final MutableLiveData<Integer> liveDataForAction = new MutableLiveData<>();

	public MainViewModel() {
		init();
	}

	private void init(){
		int n = 10;
		sampleList = new ArrayList<Object>();
		for(int i=0;i<n;i++){
			Sample sample = new Sample("name " + i, "email " + i);
			sampleList.add(sample);
		}
		setLiveDataForAction(0);
	}

	public List<Object> getSampleList() {
		return sampleList;
	}

	public void setSampleList(
		List<Object> sampleList) {
		this.sampleList = sampleList;
	}

	public MutableLiveData<Integer> getLiveDataForAction() {
		return liveDataForAction;
	}

	private void setLiveDataForAction(int actionType){
		liveDataForAction.setValue(actionType);
	}
}
