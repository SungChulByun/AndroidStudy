package com.example.viewpager_practice_byun.SearchType;

import com.google.gson.annotations.SerializedName;

public class Profile_Result {
    @SerializedName("resultcode")
    public String resultcode;
    @SerializedName("message")
    public Integer message;
    @SerializedName("response")
    public Response response;


    public class Response {

        @SerializedName("email")
        public String email;
        @SerializedName("nickname")
        public String nickname;
        @SerializedName("profile_image")
        public String profile_image;
        @SerializedName("age")
        public String age;
        @SerializedName("gender")
        public String gender;
        @SerializedName("id")
        public String id;
        @SerializedName("name")
        public String name;
        @SerializedName("birthday")
        public String birthday;

    }
}