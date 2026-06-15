package com.app.akademikapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.akademikapp.databinding.ItemMenuAkademikCardBinding

class MenuAkademikCardAdapter(
    private val items: List<MenuAkademik>,
    private val isListMode: Boolean, // Penanda: true jika LIST, false jika CARD
    private val onClick: (MenuAkademik) -> Unit
) : RecyclerView.Adapter<MenuAkademikCardAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemMenuAkademikCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MenuAkademik) {
            binding.tvMenuTitle.text = item.title
            binding.tvMenuDescription.text = item.description
            binding.imgMenuIcon.setImageResource(item.iconResId)

            // KODE PERBAIKAN: Memakai fungsi standar Android visibility
            if (isListMode) {
                binding.imgMenuIcon.visibility = View.GONE
            } else {
                binding.imgMenuIcon.visibility = View.VISIBLE
            }

            binding.root.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMenuAkademikCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}