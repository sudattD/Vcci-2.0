package sbnri.consumer.android.onboarding.UserEmail

import android.content.Context
import com.orhanobut.hawk.Hawk
import sbnri.consumer.android.base.contract.BaseView
import sbnri.consumer.android.base.schedulers.SchedulerProvider
import sbnri.consumer.android.data.local.SBNRIPref
import sbnri.consumer.android.data.source.SBNRIDataSource
import sbnri.consumer.android.qualifiers.ApplicationContext
import sbnri.consumer.android.qualifiers.BaseUrl
import sbnri.consumer.android.qualifiers.SBNRIRepositoryQualifier
import sbnri.consumer.android.util.NetworkUtils
import sbnri.consumer.android.webservice.ApiCallTags
import sbnri.consumer.android.webservice.ApiParameters
import sbnri.consumer.android.webservice.model.SBNRIResponse
import javax.inject.Inject

class UserEmailPresenterImpl @Inject constructor(@SBNRIRepositoryQualifier val sbnriDataSource: SBNRIDataSource,
schedulerProvider: SchedulerProvider,
baseView: BaseView,
sbnriPref: SBNRIPref,
@ApplicationContext context: Context)
    : UserEmailContract.Presenter(sbnriDataSource,schedulerProvider,baseView, sbnriPref, context) {

    val view: UserEmailContract.View? = baseView as UserEmailContract.View


    override fun generateLinkForEmail(email: String) {
      val params = HashMap<String,Any?>()
        params[ApiParameters.EMAIL] = email

        view?.showProgress()
        val extras = HashMap<String,Any?>()
        extras.put(ApiParameters.EMAIL,email)
        NetworkUtils.makeNetworkCall(ApiCallTags.GENERATE_LINK_FOR_EMAIL,sbnriDataSource.generateLinkForEmail(params),
        mSchedulerProvider,this,extras)
    }


    override fun onSuccess(callTag: String?, response: SBNRIResponse<*>?, extras: java.util.HashMap<String, Any>?) {
        view?.hideProgress()
        if(response?.data == null)
        {
            onFailure(callTag, response, extras)
            return
        }
        when (callTag){
            ApiCallTags.GENERATE_LINK_FOR_EMAIL -> handleSuccess(response,extras)
        }


    }

    private fun handleSuccess(sbnriResponse: SBNRIResponse<*>, extras: HashMap<String, Any>?) {

        view?.dismissBottomSheet()
        Hawk.put("email",extras?.get(ApiParameters.EMAIL))
        Hawk.put("emailLink",sbnriResponse.data)
        view?.generateLinkForEmailSuccess(sbnriResponse.status!!)
    }




    override fun onFailure(callTag: String?, response: SBNRIResponse<*>?, extras: java.util.HashMap<String, Any>?) {
        view?.hideProgress()
        super.onFailure(callTag, response, extras)
    }

    override fun onError(callTag: String?, e: Throwable?, extras: java.util.HashMap<String, Any>?) {
        view?.hideProgress()
        super.onError(callTag, e, extras)
    }
}