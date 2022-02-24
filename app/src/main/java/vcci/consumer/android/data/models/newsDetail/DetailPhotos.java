package vcci.consumer.android.data.models.newsDetail;

import com.google.gson.annotations.SerializedName;

public class DetailPhotos {

    @SerializedName("gallery_photo_id")
    private String gallery_photo_id;

    @SerializedName("gallery_image")
    private String gallery_image;

    @SerializedName("gallery_thumb")
    private String gallery_thumb;


    public String getGallery_photo_id() {
        return gallery_photo_id;
    }

    public void setGallery_photo_id(String gallery_photo_id) {
        this.gallery_photo_id = gallery_photo_id;
    }

    public String getGallery_image() {
        return gallery_image;
    }

    public void setGallery_image(String gallery_image) {
        this.gallery_image = gallery_image;
    }

    public String getGallery_thumb() {
        return gallery_thumb;
    }

    public void setGallery_thumb(String gallery_thumb) {
        this.gallery_thumb = gallery_thumb;
    }
}
