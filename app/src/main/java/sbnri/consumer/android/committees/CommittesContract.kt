package sbnri.consumer.android.committees

import android.content.Context
import sbnri.consumer.android.base.contract.BasePresenterImp
import sbnri.consumer.android.base.contract.BaseView
import sbnri.consumer.android.base.schedulers.SchedulerProvider
import sbnri.consumer.android.data.source.SBNRIDataSource

interface CommittesContract {

    interface View : BaseView {

    }

    abstract class Presenter(sbnriDataSource: SBNRIDataSource,
                             schedulerProvider: SchedulerProvider,
                             baseView: BaseView,
                             context: Context) : BasePresenterImp(sbnriDataSource,
    schedulerProvider,baseView,
    context) {

    }
}