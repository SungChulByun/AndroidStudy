package com.example.pr_room_version_test.room;

import java.util.concurrent.ExecutorService;

import com.example.restaurant_recommend.RecommendRestaurant;

public class RoomHelper {
	public static RoomHelper instance;
	private MemberDatabase memberDatabase;
	private MemberDao memberDao;
	private ExecutorService databaseExecutor;

	public static RoomHelper getInstance(){
		if(instance == null){
			instance = new RoomHelper();
		}
		return instance;
	}

	public RoomHelper(){
		memberDatabase = MemberDatabase.getInstance(RecommendRestaurant.context);
		memberDao = memberDatabase.memberDao();
		databaseExecutor = MemberDatabase.databaseExecutor;
	}

	public MemberDatabase getMemberDatabase() {
		return memberDatabase;
	}

	public MemberDao getMemberDao() {
		return memberDao;
	}

	public ExecutorService getDatabaseExecutor() {
		return databaseExecutor;
	}
}
