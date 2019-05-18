package linhdo.inface.core

import androidx.recyclerview.widget.DiffUtil
import java.util.*

/***
 * @author: Lac
 * 17-May-19 - 10:41 PM
 */
class CoreModel : DiffUtil.Callback() {
    var index: Long = UUID.randomUUID().timestamp()

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getOldListSize(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getNewListSize(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}