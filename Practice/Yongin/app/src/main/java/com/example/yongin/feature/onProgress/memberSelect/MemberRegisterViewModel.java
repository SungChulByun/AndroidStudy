package com.example.yongin.feature.onProgress.memberSelect;

import android.util.Log;

import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import com.example.yongin.BR;
import com.example.yongin.feature.custom.CustomViewModel;
import com.example.yongin.helper.MemberDatabaseHelper;
import com.example.yongin.model.Member;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static android.content.ContentValues.TAG;
import static com.example.yongin.helper.Values.DUPLICATED;
import static com.example.yongin.helper.Values.FINISH;
import static com.example.yongin.helper.Values.REGISTER;

public class MemberRegisterViewModel extends CustomViewModel {
    private final MutableLiveData<String> liveDataForAction = new MutableLiveData<>();
    private MemberDatabaseHelper memberDatabaseHelper;
    private CompositeDisposable compositeDisposable;
    private String memberName;

    private List<Member> memberList;


    public MemberRegisterViewModel() {
        memberDatabaseHelper = MemberDatabaseHelper.getInstance();
        compositeDisposable = new CompositeDisposable();
        memberList = new ArrayList<>();
    }

    public void duplicationCheck() {
        memberDatabaseHelper.getMemberListForDuplication().subscribe(new Observer<List<Member>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(List<Member> members) {
                if (isContained(members)) {
                    setLiveDataForAction(DUPLICATED);
                    Log.d(TAG, "onNext: Duplicated");
                } else {
                    memberDatabaseHelper.addMember(memberName);
                    setLiveDataForAction(FINISH);
                    Log.d(TAG, "onNext: Not Duplicated");
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private boolean isContained(List<Member> memberList) {
        for (Member member : memberList) {
            if (member.getName().equals(memberName)) {
                return true;
            }
        }
        return false;
    }

    public MutableLiveData<String> getLiveDataForAction() {
        return liveDataForAction;
    }

    public void setLiveDataForAction(String type) {
        liveDataForAction.setValue(type);
    }

    @Bindable
    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
        notifyPropertyChanged(BR.memberName);
    }

    public void onRegisterButtonClick() {
        setLiveDataForAction(REGISTER);
    }

    public void onCancelButtonClick() {
        setLiveDataForAction(FINISH);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
