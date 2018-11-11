package mnf.future.talk.Tools

import android.content.Context
import android.graphics.Typeface
import android.support.v4.content.res.ResourcesCompat
import mnf.future.talk.R

class Misc {
    companion object {
        fun getFont(context: Context, fontRes: Int) : Typeface? {
            return ResourcesCompat.getFont(context, fontRes)
            /*when(fontName){
                "bai" -> return ResourcesCompat.getFont(context, R.font.bai)
                "roboto" -> return ResourcesCompat.getFont(context, R.font.roboto)
                "ss" -> return ResourcesCompat.getFont(context, R.font.ss)
                else -> return ResourcesCompat.getFont(context, R.font.roboto)


            }*/
        }
    }

}