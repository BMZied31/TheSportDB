package zied.ben.mohamed.fdj.sportdb.features.leagues.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import zied.ben.mohamed.fdj.sportdb.core.BaseListAdapter
import zied.ben.mohamed.fdj.sportdb.databinding.ItemLeagueBinding
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.model.LeagueModel

/**
 * [LeaguesAdapter] Is An adapter for displaying a list of [LeagueModel] items in a RecyclerView.
 *
 * @param onClick lambda callback to handle clicks on items
 */
class LeaguesAdapter(
    private val onClick: (String) -> Unit
) : BaseListAdapter<LeagueModel>(
    itemsSame = { old, new -> old == new },
    contentsSame = { old, new -> old == new }
) {

    /**
     * Binds a single item in the list to a [LeaguesViewHolder], updating the view with the item's data.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding = ItemLeagueBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LeaguesViewHolder(binding, onClick)
    }

    /**
     * Binds a single item in the list to a TeamsViewHolder, updating the view with the item's data.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as LeaguesViewHolder).bind(getItem(position))
    }

    /**
     * A ViewHolder for holding the views of a single item in the list.
     */
    private class LeaguesViewHolder(
        private val itemBinding: ItemLeagueBinding,
        private val onClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: LeagueModel) {
            itemBinding.tvLeagueName.text = item.name
            itemBinding.root.setOnClickListener {
                onClick(item.name)
            }
        }
    }
}
