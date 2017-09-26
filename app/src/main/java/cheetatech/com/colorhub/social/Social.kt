package cheetatech.com.colorhub.social

import android.app.Activity
import android.content.Intent
import android.net.Uri

/**
 * Created by coderkan on 26.09.2017.
 */
class Social {

    companion object{
        fun openUrl(url: String, activity: Activity){
            var uri = Uri.parse(url);
            activity.startActivity(Intent(Intent.ACTION_VIEW, uri))
        }

        fun shareApp(activity: Activity){
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out Color Hub app at: " + Links.APP_URL)
            sendIntent.type = "text/plain"
            activity.startActivity(sendIntent)
        }

    }
}