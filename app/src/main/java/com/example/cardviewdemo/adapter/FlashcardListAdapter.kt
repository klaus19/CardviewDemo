package com.example.cardviewdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cardviewdemo.R
import com.example.cardviewdemo.db.FlashCardPair

class FlashcardListAdapter: ListAdapter<FlashCardPair, FlashcardListAdapter.FlashViewHolder>(FlashCardPairComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):FlashViewHolder {
        return FlashViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder:FlashViewHolder, position: Int) {
        val current = getItem(position)
        val current1 = getItem(position)
        holder.bind(current.front,current1.back)
    }

    class FlashViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val fronttext: TextView = itemView.findViewById(R.id.textFront)
        private val backtext: TextView = itemView.findViewById(R.id.textBack)

        fun bind(text: String?,text1:String?) {
            fronttext.text = text
            backtext.text = text1
        }

        companion object {
            fun create(parent: ViewGroup):FlashViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_view, parent, false)
                return FlashViewHolder(view)
            }
        }
    }

    class FlashCardPairComparator : DiffUtil.ItemCallback<FlashCardPair>() {
        override fun areItemsTheSame(oldItem: FlashCardPair, newItem:FlashCardPair): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem:FlashCardPair, newItem:FlashCardPair): Boolean {
            return oldItem.front == newItem.front && oldItem.back==newItem.back
        }
    }

}