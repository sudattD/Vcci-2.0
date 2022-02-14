package sbnri.consumer.android.bottomsheetDialoguesFrags

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.user_email_bottomsheet.*
import sbnri.consumer.android.DaggerDependencyInjectorComponent
import sbnri.consumer.android.DependencyInjectorComponent
import sbnri.consumer.android.EmailConfirmation.EmailConfirmationActivity
import sbnri.consumer.android.R
import sbnri.consumer.android.base.activity.BaseActivityComponent
import sbnri.consumer.android.base.activity.BaseFragment
import sbnri.consumer.android.base.activity.BaseViewModule
import sbnri.consumer.android.base.contract.BaseView
import sbnri.consumer.android.constants.Constants.EMAIL_ADDRESS_PATTERN
import sbnri.consumer.android.databinding.UserEmailBottomsheetBinding
import sbnri.consumer.android.onboarding.UserEmail.UserEmailContract
import sbnri.consumer.android.onboarding.UserEmail.UserEmailPresenterImpl
import sbnri.consumer.android.util.extensions.disable
import sbnri.consumer.android.util.extensions.enable
import sbnri.consumer.android.util.extensions.hideKeyboard
import sbnri.consumer.android.util.extensions.showKeyboard
import javax.inject.Inject


class UserEmailBottomSheetFragment : BottomSheetDialogFragment(),UserEmailContract.View
{
    private var mContext: Context? = null
    private var baseFragmentContract: BaseFragment.BaseFragmentContract? = null

    private lateinit var binding:UserEmailBottomsheetBinding


    //https://stackoverflow.com/questions/45614024/dagger2-inject-a-presenter-into-a-kotlin-activity-error
    @Inject internal lateinit var
     presenterImpl : UserEmailPresenterImpl


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)

        this.mContext = activity
        if (activity is BaseFragment.BaseFragmentContract) {
            baseFragmentContract = activity as BaseFragment.BaseFragmentContract?
        }
        if (baseFragmentContract != null) initialiseDaggerDependencies(baseFragmentContract?.getBaseActivityComponent())

    }

    private fun initialiseDaggerDependencies(baseActivityComponent: BaseActivityComponent?) {
        callDependencyInjector(DaggerDependencyInjectorComponent.builder().baseActivityComponent(baseActivityComponent)
                .baseViewModule(BaseViewModule(getBaseView())).build())
    }

    private fun callDependencyInjector(injectorComponent: DependencyInjectorComponent) {
        injectorComponent.injectDependencies(this)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = UserEmailBottomsheetBinding.inflate(inflater,container,false)
        val view:View = binding.root
        initView()
        return view
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { dialog1: DialogInterface ->
            val d = dialog1 as BottomSheetDialog
            val bottomSheet = d.findViewById<FrameLayout>(R.id.design_bottom_sheet)
            if (bottomSheet != null) {
                BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }


    override fun initView() {

        binding.root.showKeyboard()
        binding.btnContinue.disable()
        binding.progressBar.visibility = View.GONE
        setUpEditText();
        binding.btnContinue.setOnClickListener {

            presenterImpl.generateLinkForEmail(binding.etEmail.text.toString())
           /* (activity as OnBoardingActivity).onItemClick(binding.btnContinue, binding.etEmail.text)
                dismiss()*/
        }


    }




    private fun setUpEditText() {

        binding.etEmail.addTextChangedListener(textWatcherForEmail)

    }

    override fun showToastMessage(toastMessage: String?, isErrortoast: Boolean) {
        TODO("Not yet implemented")
    }

    override fun hideProgress() {
        binding.progressBar.visibility = View.GONE
        binding.root.hideKeyboard()
    }

    override fun accessTokenExpired() {
        TODO("Not yet implemented")
    }

    override fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE
    }
    private fun getBaseView(): BaseView? {
        return this
    }

    interface OnCommonItemClickListener
    {
        fun onItemClick(view: View?, `object`: Any?)
    }

    private val textWatcherForEmail: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            if (!TextUtils.isEmpty(etEmail.text)) {
                if (!s.toString().matches(Regex(EMAIL_ADDRESS_PATTERN))) {

                    binding.btnContinue.disable()
                }
                else
                    binding.btnContinue.enable()
            }
        }
        override fun afterTextChanged(s: Editable) {


        }
    }



    override fun generateLinkForEmailSuccess(status: Int) {
        when (status)
        {
            1 -> startActivity(EmailConfirmationActivity.createInstance(context))
        }
    }

    override fun dismissBottomSheet() {
        dismiss()
    }
}

