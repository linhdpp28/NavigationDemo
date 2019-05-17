package linhdo.inface.features.splash

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import linhdo.inface.InApplication
import linhdo.inface.R
import linhdo.inface.extensions.getFBAuth
import linhdo.inface.extensions.navigationTo
import linhdo.inface.extensions.postDelay
import linhdo.inface.extensions.startActivity
import linhdo.inface.features.home.HomeActivity

class SplashFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        postDelay(2000) {
            if (InApplication.instance.getFBAuth().currentUser == null)
                navigationTo(R.id.action_splash_to_login)
            else startActivity<HomeActivity>()
        }
    }
}
