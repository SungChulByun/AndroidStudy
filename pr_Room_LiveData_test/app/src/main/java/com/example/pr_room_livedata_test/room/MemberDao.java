package com.example.pr_room_livedata_test.room;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.pr_room_livedata_test.Member;

@Dao
public interface MemberDao {
	@Query("SELECT * FROM memberTable")
	List<Member> getAllMember();

	@Query("SELECT * FROM memberTable WHERE member_email Like :email Limit 1 ")
	Member getMemberByEmail(String email);

	//	@Query("SELECT * FROM memberTable WHERE first_name LIKE :first AND " +
	//		"last_name LIKE :last LIMIT 1")
	//	Member findByName(String first, String last);

	@Insert
	void insertAll(Member... users);

	@Insert
	void addMember(Member member);

	@Delete
	void delete(Member user);
}
