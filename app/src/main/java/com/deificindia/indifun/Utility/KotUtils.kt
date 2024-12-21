package com.deificindia.indifun.Utility

import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import com.deificindia.indifun.bindingmodals.otheruserprofile.TopFans
import com.deificindia.indifun.deificpk.utils.AudioModel
import com.deificindia.indifun.ui.imagelistview.MyImageModal

class KotUtils {

    companion object obj{

        fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
            val spannableString = SpannableString(this.text)
            for (link in links) {
                val clickableSpan = object : ClickableSpan() {

                    override fun updateDrawState(textPaint: TextPaint) {
                        // use this to change the link color
                        textPaint.color = textPaint.linkColor
                        // toggle below value to enable/disable
                        // the underline shown below the clickable text
                        textPaint.isUnderlineText = true
                    }

                    override fun onClick(view: View) {
                        Selection.setSelection((view as TextView).text as Spannable, 0)
                        view.invalidate()
                        link.second.onClick(view)
                    }
                }
                val startIndexOfLink = this.text.toString().indexOf(link.first)
                spannableString.setSpan(clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            this.movementMethod = LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
            this.setText(spannableString, TextView.BufferType.SPANNABLE)
        }
        public fun List<TopFans>.filter():List<MyImageModal>{
            return this.map{
                MyImageModal( URLs.UserImageBaseUrl+it.profilepic)
            }
        }

        public fun List<String>.filterImage():List<String>{
            return this.map{
                URLs.UserPostImagesBaseUrl+it
            }
        }


        public fun List<AudioModel>.filter1():List<String>{
            return this.map{
//                MyImageModal( URLs.UserImageBaseUrl+it.profilepic)
                it.getName();
            }
        }
    }


}