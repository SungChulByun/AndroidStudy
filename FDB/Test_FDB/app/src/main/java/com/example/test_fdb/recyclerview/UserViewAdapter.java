package com.example.test_fdb.recyclerview;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.test_fdb.R;
import com.example.test_fdb.models.User;

public class UserViewAdapter extends RecyclerView.Adapter<UserHolder> {
	private ArrayList<User> userList;

	public UserViewAdapter(ArrayList<User> userList){
		this.userList = userList;
	}

	@NonNull
	@Override
	public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new UserHolder(
			LayoutInflater.from(parent.getContext()).inflate(R.layout.user_holder, parent, false));
	}

	@Override
	public void onBindViewHolder(@NonNull UserHolder holder, int position) {
		holder.setData(userList.get(position).username, userList.get(position).email);
	}

	@Override
	public int getItemCount() {
		return userList.size();
	}
}

class UserHolder extends RecyclerView.ViewHolder {
	TextView name;
	TextView mail;

	public UserHolder(@NonNull View itemView) {
		super(itemView);
		name = itemView.findViewById(R.id.user_name);
		mail = itemView.findViewById(R.id.user_mail);
	}

	public void setData(String userName, String userMail) {
		name.setText(userName);
		mail.setText(userMail);
	}
}
