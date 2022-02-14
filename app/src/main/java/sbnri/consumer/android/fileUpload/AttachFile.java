package sbnri.consumer.android.fileUpload;

import android.app.Dialog;
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
import sbnri.consumer.android.R;
import sbnri.consumer.android.base.contract.BaseView;
import sbnri.consumer.android.base.schedulers.SchedulerProvider;
import sbnri.consumer.android.data.local.SBNRIRepository;
import sbnri.consumer.android.data.models.GenerateUploadUrl;
import sbnri.consumer.android.util.GeneralUtilsKt;
import sbnri.consumer.android.util.NetworkUtils;
import sbnri.consumer.android.webservice.ApiCallTags;
import sbnri.consumer.android.webservice.ApiParameters;
import sbnri.consumer.android.webservice.model.SBNRIResponse;


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

    public void upload(final SBNRIRepository repository, final SchedulerProvider schedulerProvider, final BaseView view, final BaseView.UploadImage callback) {
        if (view != null) {
            view.showProgress();
        }
        if (Type.equalsIgnoreCase("image")) {
            if (!FilePath.endsWith(".jpeg")) {
                FilePath = FilePath + ".jpeg";
                FileName = FileName + ".jpeg";
            }
        }

        HashMap<String, Object> params = new HashMap<>();
      //  params.put(ApiParameters.KEY, FilePath);
       // String fileType = Type + "/" + GeneralUtilsKt.getFileExtension(FileName);

        String fileType = getType();
        params.put(ApiParameters.Companion.getDOC_TYPE(), fileType);
        params.put(ApiParameters.Companion.getCONTENT_TYPE(),getContentType());

        NetworkUtils.makeNetworkCall(ApiCallTags.GET_FILE_PATH, repository.getFilePath(params), schedulerProvider, new NetworkUtils.ApiCallBackEmptyImplementer() {
            @Override
            public void onSuccess(String callTag, SBNRIResponse response, HashMap<String, Object> extras) {
                if (response.getData() != null && response.getData() instanceof GenerateUploadUrl) {
                    Logger.d(" response get file path - " + new Gson().toJson(response.getData()));
                    //TODO decrypt when you have all the necessary params
                    /*String filePath = Optional.orElse(((HashMap<String, Object>) response.getData()).get(ApiParameters.S3_URL), "").get().toString();
                    if(!TextUtils.isEmpty(filePath))
                        filePath = AuthorizationUtils.decrypt()
                    String s3url = Optional.orElse(((HashMap<String, Object>) response.getData()).get(ApiParameters.S3_URL), "").get().toString();*/

                    GenerateUploadUrl generateUploadUrl = (GenerateUploadUrl) response.getData();
                    String path =  generateUploadUrl.getUrl();
                    try {
                        uploadOnAmazon(path, repository, view, callback, schedulerProvider, fileType);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(String callTag, SBNRIResponse response, HashMap<String, Object> extras) {
                Logger.t("FILE").d("FILE PATH ERROR! ");
                if (view != null) {
                    view.hideProgress();
                }
               // retryUploadDialog(repository, schedulerProvider, view, callback, picasso);
            }

            @Override
            public void onError(String callTag, Throwable e, HashMap<String, Object> extras) {
                handleError(view, e, callback.getAppContext());
            }

            @Override
            public void onSessionExpired() {
                super.onSessionExpired();
                Logger.t("401").d("401 SESSION EXPIRED ");
                if (view != null) {
                    view.hideProgress();
                }
            }
        });
    }

    private void uploadOnAmazon(final String path, SBNRIRepository repository, final BaseView view, final BaseView.UploadImage callback, SchedulerProvider schedulerProvider, String fileType) throws IOException {

        File image = new File(localFilePath);
        Logger.d(" FilePath S3 Upload " + path);

        RequestBody reqFile = RequestBody.create(MediaType.parse(fileType), image);


        repository.uploadFileOnAmazon(reqFile.contentLength(),path, reqFile).subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Logger.t("FILE").d("FILE ATTACHED");
                        if (view != null) {
                            view.hideProgress();
                        }
                        // Update image in UI
                        callback.updateImageAfterUpload(FilePath);
                    }

                    @Override
                    public void onError(Throwable e) {
                        handleError(view, e, callback.getAppContext());
                    }
                });
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