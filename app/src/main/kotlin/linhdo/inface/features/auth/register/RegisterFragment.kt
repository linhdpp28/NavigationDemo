package linhdo.inface.features.auth.register


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import linhdo.inface.LoginErrMessage
import linhdo.inface.R
import linhdo.inface.features.auth.AuthViewModel
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
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.errorLiveData().observe(this, Observer { message ->
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
        })
    }
}