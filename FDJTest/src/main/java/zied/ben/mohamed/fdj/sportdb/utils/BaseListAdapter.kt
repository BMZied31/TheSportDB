package zied.ben.mohamed.fdj.sportdb.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * This is an abstract base class for creating ListAdapters that can be used with RecyclerViews.
 *
 * @param T the type of the data items in the adapter
 * @property itemsSame a lambda function that takes two items of type T and returns true if they represent the same item
 * @property contentsSame a lambda function that takes two items of type T and returns true if their contents are the same
 * @constructor Creates a new BaseListAdapter with the specified item comparison lambda functions
 */
abstract class BaseListAdapter<T>(
    itemsSame: (T, T) -> Boolean,
    contentsSame: (T, T) -> Boolean
) : ListAdapter<T, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<T>() {

    /**
     * Checks whether two items are the same item.
     *
     * @param oldItem the old item
     * @param newItem the new item
     * @return true if the two items represent the same item, false otherwise
     */
    override fun areItemsTheSame(oldItem: T & Any, newItem: T & Any): Boolean =
        itemsSame(oldItem, newItem)

    /**
     * Checks whether two items have the same contents.
     *
     * @param oldItem the old item
     * @param newItem the new item
     * @return true if the two items have the same contents, false otherwise
     */
    override fun areContentsTheSame(oldItem: T & Any, newItem: T & Any): Boolean =
        contentsSame(oldItem, newItem)
}) {

    /**
     * Creates a new RecyclerView ViewHolder for the list item view.
     *
     * @param parent the parent ViewGroup
     * @param inflater the LayoutInflater used to inflate the view
     * @param viewType the type of the view
     * @return a new RecyclerView ViewHolder for the list item view
     */
    abstract fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder

    /**
     * Called by the RecyclerView to create a new ViewHolder for the list item view.
     *
     * @param parent the parent ViewGroup
     * @param viewType the type of the view
     * @return a new RecyclerView ViewHolder for the list item view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        onCreateViewHolder(
            parent = parent,
            inflater = LayoutInflater.from(parent.context),
            viewType = viewType
        )
}
