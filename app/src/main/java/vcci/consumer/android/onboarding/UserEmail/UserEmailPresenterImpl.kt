package vcci.consumer.android.onboarding.UserEmail

import android.content.Context
import com.orhanobut.hawk.Hawk
import vcci.consumer.android.base.contract.BaseView
import vcci.consumer.android.base.schedulers.SchedulerProvider
import vcci.consumer.android.data.local.SBNRIPref
import vcci.consumer.android.data.source.SBNRIDataSource
import vcci.consumer.android.qualifiers.ApplicationContext
import vcci.consumer.android.qualifiers.SBNRIRepositoryQualifier
import vcci.consumer.android.util.NetworkUtils
import vcci.consumer.android.webservice.ApiCallTags
import vcci.consumer.android.webservice.ApiParameters
import vcci.consumer.android.webservice.model.SBNRIResponse
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