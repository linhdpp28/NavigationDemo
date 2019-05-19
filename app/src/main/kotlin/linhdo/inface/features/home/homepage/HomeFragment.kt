package linhdo.inface.features.home.homepage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_home.*
import linhdo.customviews.logging.InLogging
import linhdo.inface.R
import linhdo.inface.extensions.setSubTitle

/***
 * @author: Lac
 * 15-May-19 - 10:42 PM
 */
class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSubTitle(getString(R.string.subtitle_home_page))

        recyclerView?.adapter = HomeAdapter {
            when (it) {
                HomeMenuType.SHOPPING_LIST -> InLogging.inError(context, "navigationTo(ShoppingListFragment)")
                HomeMenuType.TOP100 -> InLogging.inError(context, "navigationTo(Top100Fragment)")
                HomeMenuType.NOTHING -> InLogging.inError(context, "Not found Menu Item")
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }
}