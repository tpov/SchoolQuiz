package com.tpov.schoolquiz.database

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tpov.schoolquiz.R
import com.tpov.schoolquiz.databinding.MainTitleBinding
import com.tpov.schoolquiz.entities.Quiz

class TitleAdapter(private val listener: Listener) :
    ListAdapter<Quiz, TitleAdapter.ItemHolder>(ItemComparater()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }

    class ItemComparater : DiffUtil.ItemCallback<Quiz>() {
        override fun areItemsTheSame(oldItem: Quiz, newItem: Quiz): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Quiz, newItem: Quiz): Boolean {
            return oldItem == newItem
        }
    }

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = MainTitleBinding.bind(view)

        @SuppressLint("ResourceAsColor", "ResourceType")
        fun setData(quiz: Quiz, listener: Listener) = with(binding) {
            tvNumQuestion.text = quiz.numQ.toString()
            tvNumAnswer.text = quiz.numA.toString()
            tvNumHardQuiz.text = quiz.numHQ.toString()

            tvAllStars.text =
                String.format("%.2f", (quiz.starsAll.toFloat() * 0.83333) / quiz.numA)

            if (quiz.stars >= 100) {
                mainTitleButton.setBackgroundResource(R.color.num_chack_norice_red)
            } else mainTitleButton.setBackgroundResource(R.color.num_chack_norice_green)
            imDeleteQuiz.setOnClickListener {
                listener.deleteItem(quiz.id!!)
            }

            var goHardQuiz =
                "${this.root.context.getString(R.string.go_hard_question)} - ${quiz.nameQuestion}"

            if (quiz.stars == 100) {
                Toast.makeText(binding.root.context, goHardQuiz, Toast.LENGTH_SHORT).show()
            }

            if (quiz.stars >= 100) {
                tvHardQuiz.text = "Hard quiz!"
                tvHardQuiz.setBackgroundResource(R.color.num_chack_norice_red)
            } else {
                tvHardQuiz.text = "Light quiz!"
                tvHardQuiz.setBackgroundResource(R.color.num_chack_norice_green)
            }

            if (quiz.stars <= 100) ratingBar.rating = (quiz.stars.toFloat() / 50)
            else ratingBar.rating = (((quiz.stars.toFloat() - 100) / 20) + 2)

            tvStars.text = String.format("%.2f", (quiz.stars.toFloat() * 0.83333))
            tvTime.text = quiz.data
            mainTitleButton.text = quiz.nameQuestion
            mainTitleButton.setOnClickListener {
                listener.onClick(quiz.nameQuestion, quiz.stars)
            }
            imShare.setOnClickListener {
                listener.shareItem("shareQuiz", quiz.id!!)
            }
            tvName.text = quiz.userName

            tvVisibleOrGone()
        }

        private fun MainTitleBinding.tvVisibleOrGone() {
            if (tvAllStars.text == "0,00" || tvAllStars == null) tvAllStars.visibility = View.GONE
            else tvAllStars.visibility = View.VISIBLE

            if (tvNumAnswer.text == "0") tvNumAnswer.visibility = View.GONE
            else tvNumAnswer.visibility = View.VISIBLE

            if (tvNumHardQuiz.text == "0") tvNumHardQuiz.visibility = View.GONE
            else tvNumHardQuiz.visibility = View.VISIBLE

            if (tvNumQuestion.text == "0") tvNumQuestion.visibility = View.GONE
            else tvNumQuestion.visibility = View.VISIBLE

            if (tvStars.text == "0") tvStars.visibility = View.GONE
            else tvStars.visibility = View.VISIBLE
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