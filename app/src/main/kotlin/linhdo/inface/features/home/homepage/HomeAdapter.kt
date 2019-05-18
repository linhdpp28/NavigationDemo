package linhdo.inface.features.home.homepage

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_home_grid.view.*
import linhdo.inface.R
import linhdo.inface.core.InFaceViewHolder
import linhdo.inface.extensions.inflateLayout

/***
 * @author: Lac
 * 17-May-19 - 10:08 PM
 */

data class HomeMenuModel(val title: String, @DrawableRes val iconIdRes: Int) {
    val type: HomeMenuType = when (iconIdRes) {
        R.drawable.ic_shopping_basket -> HomeMenuType.SHOPPING_LIST
        R.drawable.ic_stars -> HomeMenuType.TOP100
        else -> HomeMenuType.NOTHING
    }
}

enum class HomeMenuType { SHOPPING_LIST, TOP100, NOTHING }

class HomeAdapter constructor(private val listener: (type: HomeMenuType) -> Unit) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    @SuppressLint("PrivateResource")
    private val list = arrayListOf(
            HomeMenuModel("Shopping Lists", R.drawable.ic_shopping_basket),
            HomeMenuModel("Top 100 Items", R.drawable.ic_stars),
            HomeMenuModel("Nothing", R.drawable.navigation_empty_icon),
            HomeMenuModel("Nothing", R.drawable.navigation_empty_icon)
    )

    override fun getItemCount(): Int = list.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder = HomeViewHolder(inflateLayout(parent, R.layout.item_home_grid))

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class HomeViewHolder(itemView: View) : InFaceViewHolder(itemView) {
        fun bind(item: HomeMenuModel?) {
            if (item == null) return
            itemView.iconHomeMenu?.setImageResource(item.iconIdRes)
            itemView.tvTitleHomeMenu?.text = item.title
            itemView.itemHomeMenu?.setOnClickListener {
                listener.invoke(item.type)
            }
        }
    }
}