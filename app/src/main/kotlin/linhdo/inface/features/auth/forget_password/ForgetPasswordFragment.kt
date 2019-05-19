package linhdo.inface.features.auth.forget_password


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_forget_password.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import linhdo.inface.R
import linhdo.inface.extensions.getMainActivity
import linhdo.inface.features.auth.AuthViewModel
import linhdo.inface.utils.status.Status
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton

/***
 * @author: Lac
 * 15-May-19 - 10:48 PM
 */

@ExperimentalCoroutinesApi
class ForgetPasswordFragment : Fragment() {
    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_forget_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnResetPassword?.setOnClickListener {
            viewModel.forgotPassword(edtEmail?.text.toString())
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        viewModel.status().observe(this, Observer { state ->
            when (state.status) {
                Status.RUNNING -> getMainActivity()?.showLoading()
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
                            edtEmail?.setText("")
                            edtEmail?.requestFocus()
                        }
                    }.show()
                }
            }
        })
    }
}