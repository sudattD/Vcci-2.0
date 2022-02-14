package sbnri.consumer.android.onboarding

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.viewpager.widget.PagerAdapter
import sbnri.consumer.android.R
import sbnri.consumer.android.util.extensions.ShowView
import sbnri.consumer.android.util.extensions.hideView

class OnBoardingViewPagerAdapter constructor(val context: Context, val mOnBoardingItem: MutableList<OnBoardingItem>)  : PagerAdapter() {


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutScreen = inflater.inflate(R.layout.onboarding_info, null)

        val title = layoutScreen.findViewById<TextView>(R.id.intro_title)
        val description = layoutScreen.findViewById<TextView>(R.id.intro_description)


        val title_one = layoutScreen.findViewById<TextView>(R.id.tv_title_item_one)
        val description_one = layoutScreen.findViewById<TextView>(R.id.tv_subtitle_item_one)

        //setItemOneLa  youtDesign(position,title,description)
       // setItemOneLayoutDesign(position,title_one,description_one);
        title.setText(mOnBoardingItem.get(position).title)
        description.setText(mOnBoardingItem.get(position).subTitle)

        container.addView(layoutScreen)

        return layoutScreen
    }

    private fun setItemOneLayoutDesign(position: Int, title: TextView?, description: TextView?) {

        when(position)
        {
            0 -> {
                title?.ShowView()
                description?.ShowView()
                title?.setText(mOnBoardingItem.get(position).title)
                description?.setText(mOnBoardingItem.get(position).subTitle)
            }
            else ->
            {
                title?.hideView()
                description?.hideView()
            }

        }

    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return mOnBoardingItem.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        container.removeView(`object` as View)
    }
}