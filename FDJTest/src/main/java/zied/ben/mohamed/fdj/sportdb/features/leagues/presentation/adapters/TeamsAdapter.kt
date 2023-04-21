package zied.ben.mohamed.fdj.sportdb.features.leagues.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import zied.ben.mohamed.fdj.sportdb.R
import zied.ben.mohamed.fdj.sportdb.core.BaseListAdapter
import zied.ben.mohamed.fdj.sportdb.databinding.ItemTeamBinding
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.model.TeamModel

class TeamsAdapter : BaseListAdapter<TeamModel>(
    itemsSame = { old, new -> old == new },
    contentsSame = { old, new -> old == new }
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding = ItemTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeamsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TeamsViewHolder).bind(getItem(position))
    }

    private class TeamsViewHolder(
        private val itemBinding: ItemTeamBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: TeamModel) {
            itemBinding.ivTeamBadge.load(item.teamBadge) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
            }
            itemBinding.tvTeamName.text = item.teamName
        }
    }
}
