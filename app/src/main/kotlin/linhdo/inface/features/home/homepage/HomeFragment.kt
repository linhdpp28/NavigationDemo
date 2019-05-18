package linhdo.inface.features.home.homepage

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.*
import linhdo.inface.R
import linhdo.inface.extensions.error
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
                HomeMenuType.SHOPPING_LIST -> error("navigationTo(ShoppingListFragment)")
                HomeMenuType.TOP100 -> error("navigationTo(Top100Fragment)")
                HomeMenuType.NOTHING -> error("Not found Menu Item")
            }
        }
        recyclerView?.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                val position = parent.getChildLayoutPosition(view)
                if (position == 0 || position == 1) outRect.bottom = 8
                if (position == 0 || position == 2) outRect.right = 8
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }
}