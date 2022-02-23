package vcci.consumer.android.onboarding

import android.content.Context
import vcci.consumer.android.base.contract.BasePresenterImp
import vcci.consumer.android.base.contract.BaseView
import vcci.consumer.android.base.schedulers.SchedulerProvider
import vcci.consumer.android.data.source.SBNRIDataSource

interface OnBoardingContract {
    interface OnBoardingView : BaseView {
    }

    abstract class OnBoardingPresenter(sbnriDataSource: SBNRIDataSource, schedulerProvider: SchedulerProvider
                                       , baseView: BaseView, context: Context) :
            BasePresenterImp(sbnriDataSource, schedulerProvider, baseView, context) {
        abstract fun getFireBasetokenVerified(token: String)
    }
}