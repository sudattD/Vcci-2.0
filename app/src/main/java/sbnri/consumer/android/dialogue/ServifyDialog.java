package sbnri.consumer.android.dialogue;

import android.content.Context;

import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;

/**
 * Created by yashThakur on 07/11/16.
 */

public class ServifyDialog {

    private String title;
    private String image;
    private String description;
    private String processingText;
    private boolean isCancelable = true;
    private String buttonOneText;
    private String buttonTwoText;
    private ServifyDialogClick clickListener;
    @Nullable
    private Picasso picasso;

    private static ServifyDialog instance;
    private Context builderContext;

    public static ServifyDialog with(Context context, Picasso picasso) {
        instance = new ServifyDialog();
        instance.builderContext = context;
        instance.picasso = picasso;
        return instance;
    }

    public static ServifyDialog with(Context context) {
        instance = new ServifyDialog();
        instance.builderContext = context;
        return instance;
    }

    public ServifyDialog setImageUrl(String image) {
        this.image = image;
        return this;
    }

    public ServifyDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public ServifyDialog setIsCancelable(boolean isCancelable) {
        this.isCancelable = isCancelable;
        return this;
    }

    public ServifyDialog setDescription(String description) {
        this.description = description;
        return this;
    }

    public ServifyDialog setProcessDialog(String processingText) {
        this.processingText = processingText;
        return this;
    }

    public ServifyDialog setButtonOneText(String buttonOneText) {
        this.buttonOneText = buttonOneText;
        return this;
    }

    public ServifyDialog setButtonTwoText(String buttonTwoText) {
        this.buttonTwoText = buttonTwoText;
        return this;
    }

    public ServifyDialog setClickListener(ServifyDialogClick clickListener) {
        this.clickListener = clickListener;
        return this;
    }

    public static ServifyDialog getInstance(){
        return instance;
    }

    @SuppressWarnings("UnusedReturnValue")
    public ServifyDialogBuilder show() {
        return new ServifyDialogBuilder(builderContext, title, description, processingText, image, isCancelable, buttonOneText, buttonTwoText, clickListener,picasso);
    }

    public void dismiss() {
        try {
            if (ServifyDialogBuilder.dialogInstance != null && ServifyDialogBuilder.dialogInstance.isShowing()) {
                ServifyDialogBuilder.dialogInstance.dismiss();
            }
        } catch (Exception e){
            Logger.d("Exception in dismissing dialog");
        }
    }
}
