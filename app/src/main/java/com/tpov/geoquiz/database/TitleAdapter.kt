package com.tpov.geoquiz.database

import android.annotation.SuppressLint
import android.graphics.Color
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tpov.geoquiz.R
import com.tpov.geoquiz.databinding.MainTitleBinding
import com.tpov.geoquiz.entities.FrontList

class TitleAdapter(private val listener: Listener) :
    ListAdapter<FrontList, TitleAdapter.ItemHolder>(ItemComparater()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }

    class ItemComparater : DiffUtil.ItemCallback<FrontList>() {
        override fun areItemsTheSame(oldItem: FrontList, newItem: FrontList): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FrontList, newItem: FrontList): Boolean {
            return oldItem == newItem
        }
    }

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = MainTitleBinding.bind(view)

        @SuppressLint("ResourceAsColor")
        fun setData(frontList: FrontList, listener: Listener) = with(binding) {
            tvNumQuestion.text = frontList.numQ.toString()
            tvNumAnswer.text = frontList.numA.toString()
            tvNumHardQuiz.text = frontList.numHQ.toString()

            tvAllStars.text =
                String.format("%.2f", (frontList.starsAll.toFloat() * 0.83333) / frontList.numA)
            if (frontList.stars >= 100) {
                mainTitleButton.setBackgroundColor(R.color.tvHardQuestion)
            } else mainTitleButton.setBackgroundColor(R.color.tvLightQuestion)
            imDeleteQuiz.setOnClickListener {
                listener.deleteItem(frontList.id!!)
            }

            var goHardQuiz =
                "${this.root.context.getString(R.string.go_hard_question)} - ${frontList.nameQuestion}"
            if (frontList.stars == 100) {
                Toast.makeText(binding.root.context, goHardQuiz, Toast.LENGTH_SHORT).show()
            }
            if (frontList.stars >= 100) {
                tvHardQuiz.text = "Hard quiz!"
                tvHardQuiz.setBackgroundColor(Color.parseColor("#7A0000"))
            } else {
                tvHardQuiz.text = "Light quiz!"
                tvHardQuiz.setBackgroundColor(Color.parseColor("#167A00"))
            }
            if (frontList.stars <= 100) ratingBar.rating = (frontList.stars.toFloat() / 50)
            else ratingBar.rating = (((frontList.stars.toFloat() - 100) / 20) + 2)
            tvStars.text = String.format("%.2f", (frontList.stars.toFloat() * 0.83333))

            Log.d("ShopingListAdapter", "${frontList.stars.toFloat()} ")
            Log.d("ShopingListAdapter", "${frontList.stars.toFloat() * 0.8}")
            tvTime.text = frontList.data
            mainTitleButton.text = frontList.nameQuestion
            mainTitleButton.setOnClickListener {
                listener.onClick(frontList.nameQuestion, frontList.stars)
            }
            imShare.setOnClickListener {
                listener.shareItem("shareQuiz", frontList.id!!)
            }
            tvName.text = frontList.userName
        }


        companion object {
            fun create(parent: ViewGroup): ItemHolder {
                Log.d("ShopingListAdapter", "create ")
                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.main_title, parent, false)
                )
            }
        }
    }
    interface Listener {
        fun deleteItem(id: Int)
        fun onClick(name: String, stars: Int)
        fun shareItem(name: String, stars: Int)
    }
}