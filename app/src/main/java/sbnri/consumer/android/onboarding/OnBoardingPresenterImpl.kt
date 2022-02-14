package sbnri.consumer.android.onboarding

import android.content.Context
import com.orhanobut.hawk.Hawk
import sbnri.consumer.android.base.contract.BaseView
import sbnri.consumer.android.base.schedulers.SchedulerProvider
import sbnri.consumer.android.data.local.SBNRIPref
import sbnri.consumer.android.data.source.SBNRIDataSource
import sbnri.consumer.android.qualifiers.ApplicationContext
import sbnri.consumer.android.qualifiers.SBNRIRepositoryQualifier
import sbnri.consumer.android.util.NetworkUtils
import sbnri.consumer.android.webservice.ApiParameters
import sbnri.consumer.android.webservice.model.SBNRIResponse
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