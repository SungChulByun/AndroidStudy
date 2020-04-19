package com.example.test_fdb.activities;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.test_fdb.R;
import com.example.test_fdb.models.User;
import com.example.test_fdb.recyclerview.RecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CheckUserListActivity extends AppCompatActivity {
	private final String TAG = "sungchul";
	private RecyclerView recyclerView;
	private RecyclerViewAdapter recyclerViewAdapter;
	private TextView getDataFromDatabase;
	private DatabaseReference databaseReference;
	private ArrayList<User> userList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_data);
		init();
	}

	void init() {
		databaseReference = FirebaseDatabase.getInstance().getReference("users");
		getDataFromDatabase = findViewById(R.id.get_data_from_database);
		getDataFromDatabase.setOnClickListener(myListener);
		recyclerView = findViewById(R.id.recycler_view);
		clearUserList();
	}

	private View.OnClickListener myListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.get_data_from_database:
					//					clearUserList();
					getUserDataFromDatabase();
					setRecyclerView();
					break;
			}
		}
	};

	private void getUserDataFromDatabase() {
		databaseReference.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				// This method is called once with the initial value and again
				// whenever data at this location is updated.
				for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
					if (snapshot.getKey() != null && snapshot.getValue() != null) {
						String uid = snapshot.getKey();
						User user = snapshot.getValue(User.class);
						if (user.email != null && user.username != null) {
							userList.add(user);
						}
						Log.d(TAG, "name: " + user.username + ", mail: " + user.email);

						//						userList.add(new User(snapshot.getKey(), snapshot.getValue().toString()));
						//						Log.d(TAG, "add to userList: key : " + snapshot.getKey() + ", val : " + snapshot.getValue().toString());
					}
					Log.d("MainActivity", "ValueEventListener : " + snapshot.getValue());
				}

			}

			@Override
			public void onCancelled(DatabaseError error) {
				// Failed to read value
				Log.w(TAG, "Failed to read value.", error.toException());
			}
		});
	}

	private void setRecyclerView() {
		recyclerViewAdapter = new RecyclerViewAdapter(userList);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(recyclerViewAdapter);
		recyclerViewAdapter.notifyDataSetChanged();
	}

	private void clearUserList() {
		userList = new ArrayList<>();
	}
}
