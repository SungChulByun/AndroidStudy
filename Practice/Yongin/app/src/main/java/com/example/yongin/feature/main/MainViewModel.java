package com.example.yongin.feature.main;

import android.util.Log;

import androidx.databinding.Bindable;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;

import com.example.yongin.BR;
import com.example.yongin.feature.custom.CustomViewModel;
import com.example.yongin.helper.MemberDatabaseHelper;
import com.example.yongin.model.Member;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static android.content.ContentValues.TAG;
import static com.example.yongin.helper.Values.ADD_MEMBER;
import static com.example.yongin.helper.Values.LEXIO;
import static com.example.yongin.helper.Values.POKER;

public class MainViewModel extends CustomViewModel {
    private final ObservableArrayList<Member> memberList = new ObservableArrayList<>();
    private MemberDatabaseHelper memberDatabaseHelper;
    private CompositeDisposable compositeDisposable;

    public MainViewModel() {
        init();
        getMemberListFromFDB();
    }

    private void init() {
        Log.d(TAG, "init: ");
        memberDatabaseHelper = MemberDatabaseHelper.getInstance();
        compositeDisposable = new CompositeDisposable();
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
                addMemberToList(member);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private final MutableLiveData<String> liveDataForButtonClick = new MutableLiveData<>();

    public MutableLiveData<String> getLiveDataForButtonClick() {
        return liveDataForButtonClick;
    }

    public void setLiveDataForButtonClick(String type) {
        liveDataForButtonClick.setValue(type);
    }

    public void onClickAddButton() {
        setLiveDataForButtonClick(ADD_MEMBER);
    }

    public void onClickLexioButton() {
        setLiveDataForButtonClick(LEXIO);
    }

    public void onClickPokerButton() {
        setLiveDataForButtonClick(POKER);
    }

    @Bindable
    public ObservableArrayList<Member> getMemberList() {
        return memberList;
    }

    public void addMemberToList(Member member){
        memberList.add(member);
        notifyPropertyChanged(BR.memberList);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
