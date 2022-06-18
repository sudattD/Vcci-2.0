package vcci.consumer.android.fileUpload;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import androidx.room.Entity;
import io.reactivex.observers.DisposableCompletableObserver;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import vcci.consumer.android.R;
import vcci.consumer.android.base.contract.BaseView;
import vcci.consumer.android.base.schedulers.SchedulerProvider;
import vcci.consumer.android.data.local.SBNRIRepository;
import vcci.consumer.android.util.NetworkUtils;
import vcci.consumer.android.webservice.ApiCallTags;
import vcci.consumer.android.webservice.ApiParameters;
import vcci.consumer.android.webservice.model.SBNRIResponse;


/**
 */

@Entity(primaryKeys = {"consumerServiceRequestDocumentID", "consumerProductDocumentID"})
public class AttachFile implements Parcelable {

    public static final Creator<AttachFile> CREATOR = new Creator<AttachFile>() {
        @Override
        public AttachFile createFromParcel(Parcel in) {
            return new AttachFile(in);
        }

        @Override
        public AttachFile[] newArray(int size) {
            return new AttachFile[size];
        }
    };
    private int id;
    private String FileName;
    private String FilePath;
    private String Type;
    private String localFilePath;
    private String thumbnail;
    private int progress;
    private int IsIdentificationDocument;
    private String Tag;
    private int PendingDocID;
    private int DocumentID;
    private int brandID;
    private boolean IsAddFilepath;
    private int shouldBeDeleted;



    private String contentType;

    @SerializedName("ConsumerServiceRequestDocumentID")
    private int consumerServiceRequestDocumentID;

    @SerializedName("ConsumerProductDocumentID")
    private int consumerProductDocumentID;

    @SerializedName("ConsumerServiceRequestID")
    private int consumerServiceRequestId;

    @SerializedName("ConsumerProductID")
    private int consumerProductID;

    @SerializedName("ProductOfflineID")
    private long productOfflineID;

    @SerializedName("SyncStatus")
    private boolean syncStatus;

    @SerializedName("CreatedDate")
    private String createdDate;

    public AttachFile() {

    }

    protected AttachFile(Parcel in) {
        id = in.readInt();
        FileName = in.readString();
        FilePath = in.readString();
        Type = in.readString();
        localFilePath = in.readString();
        thumbnail = in.readString();
        progress = in.readInt();
        IsIdentificationDocument = in.readInt();
        Tag = in.readString();
        PendingDocID = in.readInt();
        DocumentID = in.readInt();
        brandID = in.readInt();
        IsAddFilepath = in.readByte() != 0;
        shouldBeDeleted = in.readInt();
        consumerServiceRequestDocumentID = in.readInt();
        consumerProductDocumentID = in.readInt();
        consumerServiceRequestId = in.readInt();
        consumerProductID = in.readInt();
        productOfflineID = in.readLong();
        syncStatus = in.readByte() != 0;
        createdDate = in.readString();
        contentType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(FileName);
        dest.writeString(FilePath);
        dest.writeString(Type);
        dest.writeString(localFilePath);
        dest.writeString(thumbnail);
        dest.writeInt(progress);
        dest.writeInt(IsIdentificationDocument);
        dest.writeString(Tag);
        dest.writeInt(PendingDocID);
        dest.writeInt(DocumentID);
        dest.writeInt(brandID);
        dest.writeByte((byte) (IsAddFilepath ? 1 : 0));
        dest.writeInt(shouldBeDeleted);
        dest.writeInt(consumerServiceRequestDocumentID);
        dest.writeInt(consumerProductDocumentID);
        dest.writeInt(consumerServiceRequestId);
        dest.writeInt(consumerProductID);
        dest.writeLong(productOfflineID);
        dest.writeByte((byte) (syncStatus ? 1 : 0));
        dest.writeString(createdDate);
        dest.writeString(contentType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getLocalFilePath() {
        return localFilePath;
    }

    public void setLocalFilePath(String localFilePath) {
        this.localFilePath = localFilePath;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getIsIdentificationDocument() {
        return IsIdentificationDocument;
    }

    public void setIsIdentificationDocument(int isIdentificationDocument) {
        IsIdentificationDocument = isIdentificationDocument;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public int getPendingDocID() {
        return PendingDocID;
    }

    public void setPendingDocID(int pendingDocID) {
        PendingDocID = pendingDocID;
    }

    public int getDocumentID() {
        return DocumentID;
    }

    public void setDocumentID(int documentID) {
        DocumentID = documentID;
    }

    public boolean isAddFilepath() {
        return IsAddFilepath;
    }

    public void setAddFilepath(boolean addFilepath) {
        IsAddFilepath = addFilepath;
    }

    public int getShouldBeDeleted() {
        return shouldBeDeleted;
    }

    public void setShouldBeDeleted(int shouldBeDeleted) {
        this.shouldBeDeleted = shouldBeDeleted;
    }

    public int getConsumerServiceRequestDocumentID() {
        return consumerServiceRequestDocumentID;
    }

    public void setConsumerServiceRequestDocumentID(int consumerServiceRequestDocumentID) {
        this.consumerServiceRequestDocumentID = consumerServiceRequestDocumentID;
    }

    public int getConsumerProductDocumentID() {
        return consumerProductDocumentID;
    }

    public void setConsumerProductDocumentID(int consumerProductDocumentID) {
        this.consumerProductDocumentID = consumerProductDocumentID;
    }

    public int getConsumerServiceRequestId() {
        return consumerServiceRequestId;
    }

    public void setConsumerServiceRequestId(int consumerServiceRequestId) {
        this.consumerServiceRequestId = consumerServiceRequestId;
    }

    public int getConsumerProductID() {
        return consumerProductID;
    }

    public void setConsumerProductID(int consumerProductID) {
        this.consumerProductID = consumerProductID;
    }

    public long getProductOfflineID() {
        return productOfflineID;
    }

    public void setProductOfflineID(long productOfflineID) {
        this.productOfflineID = productOfflineID;
    }

    public boolean isSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(boolean syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getBrandID() {
        return brandID;
    }

    public void setBrandID(int brandID) {
        this.brandID = brandID;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }


    private void handleError(BaseView view, Throwable e, Context context) {
        if (view != null) {
            view.showToastMessage(context.getString(R.string.something_went_wrong), true);
            view.hideProgress();
        }
        Logger.d(e.getMessage());
    }

    private void retryUploadDialog(SBNRIResponse repository, SchedulerProvider schedulerProvider, BaseView view, BaseView.UploadImage callback, Picasso picasso) {
/*        ServifyDialog.with(callback.getAppContext(), picasso).setProcessDialog("Document Upload Failed")
                .setButtonOneText("Try Again")
                .setButtonTwoText("OK")
                .setClickListener(new ServifyDialogClick() {
                    @Override
                    protected void buttonOneClick(Dialog dialogInstance) {
                        upload(repository, schedulerProvider, view, callback, picasso);
                    }

                    @Override
                    protected void buttonTwoClick(Dialog dialogInstance) {
                        ServifyDialog.with(callback.getAppContext(), picasso).dismiss();
                    }
                }).show();*/
    }

}