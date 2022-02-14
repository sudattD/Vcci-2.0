package sbnri.consumer.android.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.flipboard.bottomsheet.BottomSheetLayout
import sbnri.consumer.android.R
import sbnri.consumer.android.base.activity.BaseActivity
import sbnri.consumer.android.qualifiers.ActivityContext

class BottomSheetUtil
{
    companion object
    {
        fun showNetworkBottomSheet(context: Context?,bottomSheetLayout: BottomSheetLayout?)
        {
            val sheetView : View = LayoutInflater.from(context).inflate(R.layout.no_internet_layout,null,false)
            bottomSheetLayout?.showWithSheetView(sheetView)

        }

        fun dismissNetworkBottomSheet(bottomSheetLayout: BottomSheetLayout?)
        {
            if (bottomSheetLayout!!.isSheetShowing)
            {
                bottomSheetLayout?.dismissSheet()
            }
        }


        fun showEmailBottomSheet(context: Context?,bottomSheetLayout: BottomSheetLayout?)
        {
            val sheetView : View = LayoutInflater.from(context).inflate(R.layout.user_email_bottomsheet,null,false)
            bottomSheetLayout?.showWithSheetView(sheetView)

        }
    }


}