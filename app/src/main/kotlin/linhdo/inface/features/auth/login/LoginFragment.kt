package linhdo.inface.features.auth.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import linhdo.inface.LoginErrMessage
import linhdo.inface.R
import linhdo.inface.ServiceLocator
import linhdo.inface.extensions.getMainActivity
import linhdo.inface.extensions.navigationTo
import linhdo.inface.features.auth.AuthViewModel
import linhdo.inface.utils.status.Status
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton

@ExperimentalCoroutinesApi
class LoginFragment : Fragment() {
    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLogin?.setOnClickListener {
            viewModel.login(Pair(edtEmail?.text.toString(), edtPassword?.text.toString()))
        }
        tvForgotPassword?.setOnClickListener {
            navigationTo(R.id.action_login_to_forgetPassword)
        }
        tvRegister?.setOnClickListener {
            navigationTo(R.id.action_login_to_register)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        viewModel.setRepo(ServiceLocator.instance(requireContext()).getUserRepo())
        viewModel.status().observe(this, Observer { state ->
            when (state.status) {
                Status.RUNNING -> {
                    getMainActivity()?.showLoading()
                }
                Status.SUCCESS -> {
                    getMainActivity()?.hideLoading()
                    
                }
                Status.FAILED -> {
                    getMainActivity()?.hideLoading()
                    val message = state.msg ?: return@Observer
                    context.alert(message) {
                        okButton {
                            it.dismiss()
                            if (message == LoginErrMessage.EMPTY_PASSWORD) {
                                edtPassword?.setText("")
                                edtPassword?.requestFocus()
                            } else if (message == LoginErrMessage.EMPTY_EMAIL || message == LoginErrMessage.WRONG_FORMAT_EMAIL) {
                                edtEmail?.setText("")
                                edtEmail?.requestFocus()
                            }
                        }
                    }.show()
                }
            }
        })
    }
}