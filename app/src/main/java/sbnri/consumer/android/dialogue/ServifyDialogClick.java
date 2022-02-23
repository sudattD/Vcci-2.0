package sbnri.consumer.android.dialogue;

import android.app.Dialog;
import android.view.View;

import com.orhanobut.logger.Logger;

import sbnri.consumer.android.R;


/**
 * Created by yashThakur on 07/12/16.
 */

public abstract class ServifyDialogClick implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOne:
                //write action for on press of btnAccept
                Logger.i("onClick: btnAllow ");
                buttonOneClick(ServifyDialogBuilder.dialogInstance);
                break;
            case R.id.btnTwo:
                //write action for on press of btnCancel
                Logger.i("onClick: btnSkip ");
                buttonTwoClick(ServifyDialogBuilder.dialogInstance);
                break;
        }
    }

    /**
     *
     * @param dialogInstance
     */
    protected abstract void buttonOneClick(Dialog dialogInstance);

    protected abstract void buttonTwoClick(Dialog dialogInstance);

}