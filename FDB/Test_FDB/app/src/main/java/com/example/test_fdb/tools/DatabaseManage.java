package com.example.test_fdb;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.test_fdb.models.User;

public class DatabaseManage {
	static DatabaseManage instance;
	private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();;

	public static DatabaseManage getInstance(){
		if(instance == null){
			return instance = new DatabaseManage();
		}
		else{
			return instance;
		}
	}
	private void writeNewUser(String userId, String name, String email) {
		User user = new User(name, email);

		mDatabase.child("users").child(userId).setValue(user);
	}
}
