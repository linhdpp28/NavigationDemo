package linhdo.inface.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView


/***
 * @author: Lac
 * 17-May-19 - 10:29 PM
 */
abstract class InFaceAdapter<M, VH : InFaceViewHolder>(@LayoutRes private val layoutId: Int) : RecyclerView.Adapter<VH>() {
    private val list: ArrayList<M> = arrayListOf()

    override fun getItemCount(): Int = list.size

    override fun getItemId(position: Int): Long = position.toLong()

    fun getItemAt(position: Int): M? = list[position]

    fun add(item: M?) {
        if (item == null) return
        list.add(item)
        notifyDataSetChanged()
    }

    fun updateList(data: Collection<M>?) {
        if (data.isNullOrEmpty()) return
        list.addAll(data)
        notifyDataSetChanged()
    }

    fun setList(data: Collection<M>?) {
        if (data.isNullOrEmpty()) return
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        @Suppress("UNCHECKED_CAST")
        return InFaceViewHolder(view) as VH
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItemAt(position), position)
    }
}

open class InFaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun <M> bind(item: M?, position: Int) {
        if (item == null) return
    }
}