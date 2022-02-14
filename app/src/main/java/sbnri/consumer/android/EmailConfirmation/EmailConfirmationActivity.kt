package sbnri.consumer.android.EmailConfirmation

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.core.text.HtmlCompat
import butterknife.BindView
import butterknife.OnClick
import com.orhanobut.logger.Logger
import sbnri.consumer.android.DependencyInjectorComponent
import sbnri.consumer.android.R
import sbnri.consumer.android.base.activity.BaseActivity
import sbnri.consumer.android.base.contract.BaseView


class EmailConfirmationActivity : BaseActivity() {


    companion object {
        fun createInstance(context: Context?): Intent? {
            val intent = Intent(context, EmailConfirmationActivity::class.java)

            return intent
        }
    }


    @BindView(R.id.tv_email_detail)
    internal lateinit var tv_email_detail: TextView

    @BindView(R.id.tv_open_gmail)
    internal lateinit var tv_open_gmail: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_email_confirmation)

        initView()
    }


    private fun initView() {

        baseToolbar.visibility = View.GONE
        tv_email_detail.setText(String.format(getString(R.string.confirm_email_address), " your email address here"))
        tv_open_gmail.setText(HtmlCompat.fromHtml(context.getString(R.string.open_gmail), HtmlCompat.FROM_HTML_MODE_LEGACY))
    }

    override fun getBaseView(): BaseView? {
        return null
    }

    override fun callDependencyInjector(injectorComponent: DependencyInjectorComponent?) {
        injectorComponent?.injectDependencies(this)
    }


    @OnClick(R.id.tv_open_gmail)
    fun openGmailApp() {
        //GMAIL NOT GETTING OPEN
        //TO-DO OPTIMISE THE METHOD

        val intent = Intent(Intent.ACTION_SEND)
        intent.setPackage("com.google.android.gm")
        startActivity(Intent.createChooser(intent, ""))
    }

}