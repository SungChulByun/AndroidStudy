package com.example.test_fdb.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.test_fdb.R;
import com.example.test_fdb.models.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddUserActivity extends AppCompatActivity {
	private EditText userNameInput;
	private EditText userMailInput;
	private EditText userIdInput;
	private TextView addUserButton;
	private DatabaseReference databaseReference;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_post);

		init();
	}

	void init() {
		userNameInput = findViewById(R.id.user_name_input);
		userMailInput = findViewById(R.id.user_mail_input);
		userIdInput = findViewById(R.id.user_id_input);
		addUserButton = findViewById(R.id.add_user);
		addUserButton.setOnClickListener(myListener);
		databaseReference = FirebaseDatabase.getInstance().getReference();
	}

	private View.OnClickListener myListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.add_user:
					addNewUser(userIdInput.getText().toString(), userNameInput.getText().toString(),
						userMailInput.getText().toString());
					break;
			}
		}
	};

	private void addNewUser(String userId, String name, String email) {
		User user = new User(name, email);

		databaseReference.child("users").child(userId).setValue(user);
	}
}
