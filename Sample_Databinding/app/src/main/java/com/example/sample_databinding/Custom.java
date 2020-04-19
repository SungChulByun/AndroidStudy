package com.example.sample_databinding;

import android.view.View;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

public class Custom {
	public ObservableField<String> name = new ObservableField<>();
	public ObservableInt age = new ObservableInt();
	public ObservableInt likes = new ObservableInt();

	public Custom(String nameString, int ageInt) {
		name.set(nameString);
		age.set(ageInt);
		likes.set(0);
	}

	public void onClickLike(View view) {
		likes.set(likes.get() + 1);
		age.set(age.get() + 1);
	}
}
