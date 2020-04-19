package com.example.test_recyclerview_databiinding;

import java.util.ArrayList;
import java.util.List;

public class MemoViewModel {

	private List<Memo> items;
	private RecyclerAdapter adapter;


	public MemoViewModel() {
		if (items == null) {
			items = new ArrayList<>();
		}

		if (adapter == null) {
			adapter = new RecyclerAdapter(this);
		}
		testLogic();
	}

	public void testLogic() {
		for (int i = 0; i < 30; i++) {
			Memo memo = new Memo();
			memo.setId(i);
			memo.setMemoContext("테스트 메몸입니다. " + i);
			memo.setTitle(i + "번째 메모 제목입니다.");
			items.add(memo);
		}
	}

	public void onCreate() {
		adapter.notifyDataSetChanged();
	}

	public void onResume() {

	}

	public RecyclerAdapter getAdapter() {
		return adapter;
	}

	public List<Memo> getItems() {
		return items;
	}

	public String getTitle(int pos) {
		return items.get(pos).getTitle();
	}

	public String getContents(int pos) {
		return items.get(pos).getMemoContext();
	}
}
