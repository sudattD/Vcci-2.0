package vcci.consumer.android.committees

import android.content.Context
import vcci.consumer.android.base.contract.BasePresenterImp
import vcci.consumer.android.base.contract.BaseView
import vcci.consumer.android.base.schedulers.SchedulerProvider
import vcci.consumer.android.data.source.SBNRIDataSource

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