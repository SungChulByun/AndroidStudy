package com.example.yongin.feature.onProgress;

import android.util.Log;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.example.yongin.feature.custom.CustomViewModel;
import com.example.yongin.helper.MemberDatabaseHelper;
import com.example.yongin.model.LexioScore;
import com.example.yongin.model.Member;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static android.content.ContentValues.TAG;
import static com.example.yongin.helper.Values.ADD_MEMBER;
import static com.example.yongin.helper.Values.NEXT;

public class ProgressViewModel extends CustomViewModel {
    private final ObservableField<String> title = new ObservableField<>();
    private final MutableLiveData<String> liveDataForButtonClick = new MutableLiveData<>();
    private final ObservableArrayList<Member> memberList = new ObservableArrayList<>();
    private final ObservableArrayList<Member> selectedMemberList = new ObservableArrayList<>();

    private final ObservableArrayList<LexioScore> lexioScoreList = new ObservableArrayList<>();

    private CompositeDisposable compositeDisposable;
    private MemberDatabaseHelper memberDatabaseHelper;

    public ProgressViewModel() {
        init();
        getMemberListFromFDB();
    }

    private void getMemberListFromFDB() {
        Log.d(TAG, "getMemberListFromFDB: ");
        memberDatabaseHelper.getMemberList().subscribe(new Observer<Member>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Member member) {
                Log.d(TAG, "onNext: member name : " + member.getName());
                memberList.add(member);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void init() {
        memberDatabaseHelper = MemberDatabaseHelper.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    public ObservableField<String> getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void onClickAddMemberButton() {
        setLiveDataForButtonClick(ADD_MEMBER);
    }

    public MutableLiveData<String> getLiveDataForButtonClick() {
        return liveDataForButtonClick;
    }

    private void setLiveDataForButtonClick(String type) {
        liveDataForButtonClick.setValue(type);
    }

    public ObservableArrayList<Member> getMemberList() {
        return memberList;
    }

    public void onClickNextButton() {
        setLiveDataForButtonClick(NEXT);
        setSelectedMemberList();
        setLexioScoreList();
    }

    public ObservableArrayList<Member> getSelectedMemberList() {
        return selectedMemberList;
    }

    private void setSelectedMemberList() {
        selectedMemberList.add(new Member("blank"));
        for (Member member : memberList) {
            if (member.isSelected()) {
                selectedMemberList.add(member);
            }
        }
    }

    public ObservableArrayList<LexioScore> getLexioScoreList() {
        return lexioScoreList;
    }

    private void setLexioScoreList(){
        LexioScore lexioScore = new LexioScore(selectedMemberList.size());
        LexioScore empty = new LexioScore(selectedMemberList.size());
        lexioScoreList.add(lexioScore);
        lexioScoreList.add(empty);
    }

}
