package com.mkpatir.spacedelivery.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mkpatir.spacedelivery.api.models.SpaceStationModel
import com.mkpatir.spacedelivery.databinding.ItemStationBinding

class StationAdapter: RecyclerView.Adapter<StationAdapter.StationViewHolder>() {

    private var stationList: ArrayList<SpaceStationModel> = arrayListOf()
    internal var onFavoriteClick:(spaceStationItem: SpaceStationModel) -> Unit = {}

    inner class StationViewHolder(private val dataBinding: ItemStationBinding): RecyclerView.ViewHolder(dataBinding.root){

        fun bind(spaceStationModel: SpaceStationModel){
            dataBinding.apply {
                viewState = StationViewState(dataBinding.root.context,spaceStationModel)
                imageFavorite.setOnClickListener {
                    onFavoriteClick(spaceStationModel)
                    spaceStationModel.isFavorite = spaceStationModel.isFavorite.not()
                    notifyItemChanged(bindingAdapterPosition)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return StationViewHolder(ItemStationBinding.inflate(layoutInflater))
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        holder.bind(stationList[position])
    }

    override fun getItemCount(): Int = stationList.size

    fun updateAdapter(items: ArrayList<SpaceStationModel>){
        stationList.clear()
        stationList.addAll(items)
        notifyDataSetChanged()
    }

}