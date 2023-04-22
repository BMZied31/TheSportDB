package zied.ben.mohamed.fdj.sportdb.features.leagues.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import zied.ben.mohamed.fdj.sportdb.R
import zied.ben.mohamed.fdj.sportdb.core.BaseListAdapter
import zied.ben.mohamed.fdj.sportdb.databinding.ItemTeamBinding
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.model.TeamModel

/**
 * [TeamsAdapter] Is An adapter for displaying a list of [TeamModel] items in a RecyclerView.
 */
class TeamsAdapter : BaseListAdapter<TeamModel>(
    itemsSame = { old, new -> old == new },
    contentsSame = { old, new -> old == new }
) {

    /**
     * Inflates the view for a single item
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding = ItemTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeamsViewHolder(binding)
    }

    /**
     * Binds a single item in the list to a TeamsViewHolder, updating the view with the item's data.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TeamsViewHolder).bind(getItem(position))
    }

    /**
     * A ViewHolder for holding the views of a single item in the list.
     */
    private class TeamsViewHolder(
        private val itemBinding: ItemTeamBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        /**
         * Binds the data from a TeamModel to the views in the itemBinding, updating the view.
         */
        fun bind(item: TeamModel) {
            itemBinding.ivTeamBadge.load(item.badge) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
            }
            itemBinding.tvTeamName.text = item.teamName
        }
    }
}
