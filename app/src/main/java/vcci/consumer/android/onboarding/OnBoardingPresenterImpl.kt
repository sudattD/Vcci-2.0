package vcci.consumer.android.onboarding

import android.content.Context
import vcci.consumer.android.base.contract.BaseView
import vcci.consumer.android.base.schedulers.SchedulerProvider
import vcci.consumer.android.data.local.SBNRIPref
import vcci.consumer.android.data.source.SBNRIDataSource
import vcci.consumer.android.qualifiers.ApplicationContext
import vcci.consumer.android.qualifiers.SBNRIRepositoryQualifier
import vcci.consumer.android.webservice.ApiParameters
import vcci.consumer.android.webservice.model.SBNRIResponse
import java.util.HashMap
import javax.inject.Inject


class OnBoardingPresenterImpl @Inject constructor(@SBNRIRepositoryQualifier val sbnriDataSource: SBNRIDataSource,
                                                  schedulerProvider: SchedulerProvider,baseView: BaseView,@ApplicationContext context: Context,sbnriPref: SBNRIPref)
    : OnBoardingContract.OnBoardingPresenter(sbnriDataSource,schedulerProvider,baseView, context = context)

{
    val sbnriPref : SBNRIPref = sbnriPref
    companion object
    {
        val FIREBASE_TOKEN_VERIFICATION = "firebaseTokenVerification"
        val TOKEN = "token"
        val PHOTOURL = "photoURL"
        val USERNAME = "userName"
    }

    val view : OnBoardingContract.OnBoardingView? = baseView as OnBoardingContract.OnBoardingView



    override fun getFireBasetokenVerified(token:String) {
        view?.showProgress()
        val params = HashMap<String,Any?>()
        params[ApiParameters.TOKEN] = token


    }

    override fun onSuccess(callTag: String?, response: SBNRIResponse<*>?, extras: HashMap<String, Any>?) {

        if(response?.data == null) {
            onFailure(callTag, response, extras)
            return
        }
        view?.hideProgress()
        when(callTag)
        {
            //FIREBASE_TOKEN_VERIFICATION -> handleUserCreatedSuccess(response.data as UserDetails)
        }
    }



    override fun onError(callTag: String?, e: Throwable?, extras: HashMap<String, Any>?) {

        view?.hideProgress()
        when(callTag)
        {
            FIREBASE_TOKEN_VERIFICATION -> showError()
        }
    }

    override fun onFailure(callTag: String?, response: SBNRIResponse<*>?, extras: HashMap<String, Any>?) {
        view?.hideProgress()
        when(callTag)
        {
           FIREBASE_TOKEN_VERIFICATION -> showError(response)
        }

    }

    private fun showError(response: SBNRIResponse<*>?) {
        view?.showToastMessage(getErrorMsg(response), true)
    }

    private fun showError() {
        view?.showToastMessage(getErrorMsg(), true)
    }
}