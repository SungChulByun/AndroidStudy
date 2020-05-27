package com.example.yongin.helper;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.yongin.model.Member;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import static android.content.ContentValues.TAG;
import static com.example.yongin.helper.Values.MEMBER;

public class MemberDatabaseHelper {
    private DatabaseReference reference;
    private static MemberDatabaseHelper instance;

    public static MemberDatabaseHelper getInstance() {
        if (instance == null) {
            instance = new MemberDatabaseHelper();
        }
        return instance;
    }

    private MemberDatabaseHelper() {
        reference = FirebaseDatabase.getInstance().getReference().child(MEMBER);
    }

    public void addMember(String memberName) {
        Member member = new Member(memberName);
        String key = reference.push().getKey();
        if (key != null) {
            reference.child(key).setValue(member);
        }
    }

    public void deleteMember(Member member) {
        if (reference.orderByChild("name").equalTo(member.getName()) != null) {
            reference.orderByChild("name").equalTo(member.getName()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        snapshot.getRef().removeValue();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public Observable<Member> getMemberList(){
        Observable<Member> observable = Observable.create(new ObservableOnSubscribe<Member>() {
            @Override
            public void subscribe(ObservableEmitter<Member> emitter) throws Exception {
                reference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Log.d(TAG, "onChildAdded: ");
                        HashMap<String, Object> map = (HashMap) dataSnapshot.getValue();
                        Member member = new Member((String) map.get("name"));
                        Log.d(TAG, "onChildAdded: name : " + map.get("name"));
                        emitter.onNext(member);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        return observable;
    }

    public Observable<List<Member>> getMemberListForDuplication(){
        Observable<List<Member>> observable = Observable.create(new ObservableOnSubscribe<List<Member>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Member>> emitter) throws Exception {
                List<Member> memberList = new ArrayList<>();
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot currentSnap : dataSnapshot.getChildren()){
                            HashMap<String, String> map = (HashMap) currentSnap.getValue();
                            Member member = new Member(map.get("name"));
                            Log.d(TAG, "onChildAdded: name : " + map.get("name"));
                            memberList.add(member);
                        }
                        emitter.onNext(memberList);
                        emitter.onComplete();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        return observable;
    }
}
