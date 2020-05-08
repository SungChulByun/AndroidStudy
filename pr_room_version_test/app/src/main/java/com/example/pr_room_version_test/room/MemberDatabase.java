package com.example.pr_room_version_test.room;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.restaurant_recommend.models.Member;

@Database(entities = {Member.class}, version = 1, exportSchema = false)
public abstract class MemberDatabase extends RoomDatabase {
	private static MemberDatabase INSTANCE;
	private static final int NUMBER_OF_THREADS = 7;
	public static final ExecutorService databaseExecutor =
		Executors.newFixedThreadPool(NUMBER_OF_THREADS);

	public abstract MemberDao memberDao();

	public synchronized static MemberDatabase getInstance(Context context){
		if(INSTANCE == null){
			INSTANCE = Room.databaseBuilder(context, MemberDatabase.class, "MemberDatabase.db")
				.build();
		}

		return INSTANCE;
	}
}
