package vcci.consumer.android.data.models.cm_msg;

import com.google.gson.annotations.SerializedName;

public class CmMessageResponse {

    @SerializedName("cm_message")
    private CmMessage cmMessage;

    @SerializedName("error")
    private int error;

    public void setCmMessage(CmMessage cmMessage) {
        this.cmMessage = cmMessage;
    }

    public CmMessage getCmMessage() {
        return cmMessage;
    }

    public void setError(int error) {
        this.error = error;
    }

    public int getError() {
        return error;
    }

    @Override
    public String toString() {
        return
                "CmMessageResponse{" +
                        "cm_message = '" + cmMessage + '\'' +
                        ",error = '" + error + '\'' +
                        "}";
    }
}