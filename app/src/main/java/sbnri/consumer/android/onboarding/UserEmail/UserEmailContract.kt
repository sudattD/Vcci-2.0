package sbnri.consumer.android.onboarding.UserEmail

import android.content.Context
import sbnri.consumer.android.base.contract.BasePresenterImp
import sbnri.consumer.android.base.contract.BaseView
import sbnri.consumer.android.base.schedulers.SchedulerProvider
import sbnri.consumer.android.data.local.SBNRIPref
import sbnri.consumer.android.data.source.SBNRIDataSource

interface UserEmailContract
{

    interface View:BaseView
    {
        fun generateLinkForEmailSuccess(status:Int)

        fun dismissBottomSheet()
    }



    abstract class Presenter(sbnridatasource: SBNRIDataSource, schedulerProvider: SchedulerProvider, baseView: BaseView, sbnriPref: SBNRIPref, context: Context) :
            BasePresenterImp(sbnridatasource,schedulerProvider,baseView,context)
    {
       abstract fun generateLinkForEmail(str:String)
    }
}