package zied.ben.mohamed.fdj.sportdb.features.leagues.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import zied.ben.mohamed.fdj.sportdb.databinding.ItemLeagueBinding
import zied.ben.mohamed.fdj.sportdb.utils.BaseListAdapter

class LeaguesAdapter(
    private val onClick: (String) -> Unit
) : BaseListAdapter<String>(
    itemsSame = { old, new -> old == new },
    contentsSame = { old, new -> old == new }
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding = ItemLeagueBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LeaguesViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as LeaguesViewHolder).bind(getItem(position))
    }

    private class LeaguesViewHolder(
        private val itemBinding: ItemLeagueBinding,
        private val onClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(value: String) {
            itemBinding.tvLeagueName.text = value
            itemBinding.root.setOnClickListener {
                onClick(value)
            }
        }
    }
}
