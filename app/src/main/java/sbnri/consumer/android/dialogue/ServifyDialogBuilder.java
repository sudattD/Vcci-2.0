package sbnri.consumer.android.dialogue;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import sbnri.consumer.android.R;

/**
 * Created by yashThakur on 07/11/16.
 */

class ServifyDialogBuilder {

    private final Context builderContext;
    static Dialog dialogInstance;
    private final String title;
    private final String description;
    private final String processingText;
    private final String image;
    private final boolean isCancelable;
    private final String buttonOneName;
    private final String buttonTwoName;
    private final ServifyDialogClick clickListener;

    @Nullable
    private final Picasso picasso;

    ServifyDialogBuilder(
            final Context newBuilderContext,
            final String newTitle,
            final String newDescription,
            final String newProcessingText,
            final String newImage,
            final boolean newIsCancelable,
            final String newButtonOneName,
            final String newButtonTwoName,
            final ServifyDialogClick newClickListener,
            @Nullable final Picasso picasso
    ) {

        this.builderContext = newBuilderContext;
        this.title = newTitle;
        this.description = newDescription;
        this.processingText = newProcessingText;
        this.image = newImage;
        this.isCancelable = newIsCancelable;
        this.buttonOneName = newButtonOneName;
        this.buttonTwoName = newButtonTwoName;
        this.clickListener = newClickListener;
        this.picasso = picasso;

        setDialog();

    }


    private void setDialog() {
        if (dialogInstance != null && dialogInstance.isShowing()) {
            dialogInstance.dismiss();
        }
        dialogInstance = new Dialog(builderContext);
        dialogInstance.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogInstance.setContentView(R.layout.onedialog_layout);
        ImageView ivDialogIcon = dialogInstance.findViewById(R.id.ivDialogIcon);
        TextView tvDialogTitle = dialogInstance.findViewById(R.id.tvDialogTitle);
        TextView tvDialogContent = dialogInstance.findViewById(R.id.tvDialogContent);
        ProgressBar pbLoading = dialogInstance.findViewById(R.id.pbLoading);

        Button btnOne = dialogInstance.findViewById(R.id.btnOne);
        Button btnTwo = dialogInstance.findViewById(R.id.btnTwo);

        btnTwo.setVisibility(View.VISIBLE);

        dialogInstance.setCancelable(isCancelable);

        if (!TextUtils.isEmpty(title)) {
            tvDialogTitle.setVisibility(View.VISIBLE);
            tvDialogTitle.setText(title);
        } else {
            tvDialogTitle.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(description)) {
            Spanned sp = Html.fromHtml("<br/> " + description + " <br/>");
            tvDialogContent.setText(sp);
        }
        if (!TextUtils.isEmpty(processingText)) {
            Spanned sp = Html.fromHtml("<br/> " + processingText + " <br/>");
            tvDialogContent.setText(sp);
            pbLoading.setVisibility(View.VISIBLE);
            dialogInstance.setCancelable(false);
        } else {
            pbLoading.setVisibility(View.GONE);
            dialogInstance.setCancelable(isCancelable);
        }
        if (!TextUtils.isEmpty(buttonOneName)) {
            btnOne.setText(buttonOneName);
        } else {
            btnOne.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(buttonTwoName)) {
            btnTwo.setText(buttonTwoName);
        } else {
            btnTwo.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(image) && picasso != null) {
            picasso.load(image).centerCrop().fit().into(ivDialogIcon);
        } else {
            ivDialogIcon.setVisibility(View.GONE);
        }

        if (clickListener != null) {
            btnOne.setOnClickListener(clickListener);
            btnTwo.setOnClickListener(clickListener);
        }

        try {
            dialogInstance.show();
        }catch (Exception e){
            Logger.d(e.getLocalizedMessage());
        }
    }


}
