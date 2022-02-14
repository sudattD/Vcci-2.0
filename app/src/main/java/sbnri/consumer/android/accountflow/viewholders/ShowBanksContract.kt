package sbnri.consumer.android.accountflow.viewholders

import android.content.Context
import sbnri.consumer.android.base.activity.BaseActivity
import sbnri.consumer.android.base.contract.BasePresenterImp
import sbnri.consumer.android.base.contract.BaseView
import sbnri.consumer.android.base.schedulers.SchedulerProvider
import sbnri.consumer.android.data.source.SBNRIDataSource

interface ShowBanksContract
{

    interface ShowBanksView : BaseView
    {

    }

    abstract class ShowBanksPresenter(sbnriDataSource: SBNRIDataSource, schedulerProvider: SchedulerProvider
                                       , baseView: BaseView, context: Context) :
            BasePresenterImp(sbnriDataSource, schedulerProvider, baseView, context) {
        abstract fun getFireBasetokenVerified(token: String)
    }
}