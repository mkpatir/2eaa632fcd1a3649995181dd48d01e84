package com.mkpatir.spacedelivery.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mkpatir.spacedelivery.cache.models.FavoritesModel
import com.mkpatir.spacedelivery.databinding.ItemFavoriteBinding

class FavoritesAdapter: RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {

    private var items: List<FavoritesModel> = listOf()
    internal var removeFromFavoriteClick:(favoritesModel: FavoritesModel) -> Unit = {}

    inner class FavoriteViewHolder(private val dataBinding: ItemFavoriteBinding): RecyclerView.ViewHolder(dataBinding.root){

        fun bind(favoritesModel: FavoritesModel){
            dataBinding.apply {
                viewState = FavoriteViewState(root.context,favoritesModel)

                imageFavorite.setOnClickListener {
                    removeFromFavoriteClick(favoritesModel)
                    notifyItemRemoved(bindingAdapterPosition)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FavoriteViewHolder(ItemFavoriteBinding.inflate(inflater,parent,false))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateAdapter(items: List<FavoritesModel>){
        this.items = items
        notifyDataSetChanged()
    }

}