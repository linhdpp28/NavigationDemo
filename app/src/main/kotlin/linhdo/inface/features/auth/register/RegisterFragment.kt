package linhdo.inface.features.auth.register


import android.content.Context
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import linhdo.inface.LoginErrMessage
import linhdo.inface.R
import linhdo.inface.extensions.getMainActivity
import linhdo.inface.features.auth.AuthViewModel
import linhdo.inface.utils.status.Status
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton

/***
 * @author: Lac
 * 15-May-19 - 10:58 PM
 */
@ExperimentalCoroutinesApi
class RegisterFragment : Fragment() {
    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnRegister?.setOnClickListener {
            viewModel.register(
                Pair(edtUsername?.text.toString(), edtEmail?.text.toString()),
                Pair(edtPassword?.text.toString(), edtRePassword?.text.toString())
            )
        }

        btnHidePassword?.setOnClickListener {
            edtPassword?.transformationMethod =
                if (edtPassword?.transformationMethod != null) null else PasswordTransformationMethod()
            btnHidePassword?.setBackgroundResource(if (edtPassword?.transformationMethod != null) R.drawable.ic_visibility_off else R.drawable.ic_visibility)
        }
        btnHideRePassword?.setOnClickListener {
            edtRePassword?.transformationMethod =
                if (edtPassword?.transformationMethod != null) null else PasswordTransformationMethod()
            btnHideRePassword?.setBackgroundResource(if (edtPassword?.transformationMethod != null) R.drawable.ic_visibility_off else R.drawable.ic_visibility)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        viewModel.status().observe(this, Observer { state ->
            when (state.status) {
                Status.RUNNING -> {
                    getMainActivity()?.showLoading()
                }
                Status.SUCCESS -> {
                    getMainActivity()?.hideLoading()
                    Navigation.findNavController(view ?: return@Observer).popBackStack()
                }
                Status.FAILED -> {
                    getMainActivity()?.hideLoading()
                    val message = state.msg ?: return@Observer
                    context.alert(message) {
                        okButton {
                            it.dismiss()
                            when (message) {
                                LoginErrMessage.EMPTY_USERNAME -> {
                                    edtUsername?.setText("")
                                    edtUsername?.requestFocus()
                                }
                                LoginErrMessage.EMPTY_EMAIL, LoginErrMessage.WRONG_FORMAT_EMAIL -> {
                                    edtEmail?.setText("")
                                    edtEmail?.requestFocus()
                                }
                                LoginErrMessage.EMPTY_PASSWORD, LoginErrMessage.SHORT_PASSWORD, LoginErrMessage.WRONG_REPASSWORD -> {
                                    edtPassword?.setText("")
                                    if (message == LoginErrMessage.WRONG_REPASSWORD) edtRePassword?.setText("")
                                    edtPassword?.requestFocus()
                                }
                                LoginErrMessage.EMPTY_REPASSWORD -> {
                                    edtRePassword?.setText("")
                                    edtRePassword?.requestFocus()
                                }
                            }
                        }
                    }.show()
                }
            }
        })
    }
}