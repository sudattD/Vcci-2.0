package vcci.consumer.android.data.models.login;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("member")
    private Member member;

    @SerializedName("error")
    private int error;

    public void setMember(Member member){
        this.member = member;
    }

    public Member getMember(){
        return member;
    }

    public void setError(int error){
        this.error = error;
    }

    public int getError(){
        return error;
    }

    @Override
    public String toString(){
        return
                "LoginResponse{" +
                        "member = '" + member + '\'' +
                        ",error = '" + error + '\'' +
                        "}";
    }
}
