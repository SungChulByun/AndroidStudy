package com.example.pr_room_version_test.room;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "memberTable")
public class Member {

	@NonNull
	@ColumnInfo(name = "member_name")
	private String memberName;

	@NonNull
	@PrimaryKey
	@ColumnInfo(name = "member_email")
	private String email;

	@Ignore
	private List<Category> favoriteCategory;
	@Ignore
	private List<Category> dislikeCategory;
	@Ignore
	private List<String> favoriteRestaurantKeys;
	@Ignore
	private List<String> dislikeRestaurantKeys;

	public Member(String memberName, String email) {
		this.memberName = memberName;
		this.email = email;
		this.favoriteCategory = new ArrayList<>();
		this.dislikeCategory = new ArrayList<>();
		this.favoriteRestaurantKeys = new ArrayList<>();
		this.dislikeRestaurantKeys = new ArrayList<>();

	}

	@NonNull
	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	@NonNull
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Category> getFavoriteCategory() {
		return favoriteCategory;
	}

	public void setFavoriteCategory(
		List<Category> favoriteCategory) {
		this.favoriteCategory = favoriteCategory;
	}

	public List<Category> getDislikeCategory() {
		return dislikeCategory;
	}

	public void setDislikeCategory(
		List<Category> dislikeCategory) {
		this.dislikeCategory = dislikeCategory;
	}

	public List<String> getFavoriteRestaurantKeys() {
		return favoriteRestaurantKeys;
	}

	public void setFavoriteRestaurantKeys(List<String> favoriteRestaurantKeys) {
		this.favoriteRestaurantKeys = favoriteRestaurantKeys;
	}

	public List<String> getDislikeRestaurantKeys() {
		return dislikeRestaurantKeys;
	}

	public void setDislikeRestaurantKeys(List<String> dislikeRestaurantKeys) {
		this.dislikeRestaurantKeys = dislikeRestaurantKeys;
	}
}
