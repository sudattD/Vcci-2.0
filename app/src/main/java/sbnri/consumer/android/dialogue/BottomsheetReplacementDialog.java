package sbnri.consumer.android.dialogue;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import sbnri.consumer.android.util.ActivityUtils;

/**
 * Created by Kashyap Bhat on 2019-08-12.
 */
public class BottomsheetReplacementDialog extends Dialog {

    private Context context;

    public BottomsheetReplacementDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public BottomsheetReplacementDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    @Override
    public void setContentView(@NonNull View view) {
        super.setContentView(view);
        ActivityUtils.setFlavourSpecificWindowAttributes(context, getWindow());
    }
}
