package vcci.consumer.android.onboarding.UserEmail

import android.content.Context
import vcci.consumer.android.base.contract.BasePresenterImp
import vcci.consumer.android.base.contract.BaseView
import vcci.consumer.android.base.schedulers.SchedulerProvider
import vcci.consumer.android.data.local.SBNRIPref
import vcci.consumer.android.data.source.SBNRIDataSource

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