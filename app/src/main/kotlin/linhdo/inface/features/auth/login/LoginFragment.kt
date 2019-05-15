package linhdo.inface.features.auth.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_login.*
import linhdo.inface.R
import linhdo.inface.extensions.navigationTo

class LoginFragment : Fragment() {
    companion object {
        fun newInstance(): LoginFragment = LoginFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        val view = view ?: return

        btnLogin?.setOnClickListener {
            //TODO: call firebase auth to login
            navigationTo(R.id.action_login_to_home)
        }
        tvForgotPassword?.setOnClickListener {
            navigationTo(R.id.action_login_to_forgetPassword)
        }
        tvRegister?.setOnClickListener {
            navigationTo(R.id.action_login_to_register)
        }
    }

    override fun onDetach() {
        super.onDetach()
    }
}