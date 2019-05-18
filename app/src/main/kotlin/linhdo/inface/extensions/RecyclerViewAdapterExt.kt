package linhdo.inface.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/***
 * @author: Lac
 * 18-May-19 - 11:18 AM
 */

inline fun <reified VH : RecyclerView.ViewHolder> RecyclerView.Adapter<VH>.inflateLayout(parent: ViewGroup, @LayoutRes layoutId: Int): View = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)