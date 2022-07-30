package com.tpov.schoolquiz.presentation.mainactivity

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tpov.schoolquiz.R
import com.tpov.schoolquiz.data.database.entities.Quiz
import com.tpov.schoolquiz.databinding.ActivityMainBinding
import com.tpov.schoolquiz.databinding.ActivityMainItemBinding

class MainActivityAdapter(private val listener: Listener) :
    ListAdapter<Quiz, MainActivityAdapter.ItemHolder>(ItemComparator()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }

    class ItemComparator : DiffUtil.ItemCallback<Quiz>() {
        override fun areItemsTheSame(oldItem: Quiz, newItem: Quiz): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Quiz, newItem: Quiz): Boolean {
            return oldItem == newItem
        }
    }

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ActivityMainItemBinding.bind(view)

        @SuppressLint("ResourceAsColor", "ResourceType", "SetTextI18n")
        fun setData(quiz: Quiz, listener: Listener) = with(binding) {
            tvNumQuestion.text = quiz.numQ.toString()
            tvNumAnswer.text = quiz.numA.toString()
            tvNumHardQuiz.text = quiz.numHQ.toString()

            tvAllStars.text =
                String.format("%.2f", (quiz.starsAll.toFloat() * PERCENT_TWO_STARS) / quiz.numA)

            if (quiz.stars >= MAX_PERCENT) {
                mainTitleButton.setBackgroundResource(R.color.num_chack_norice_red)
            } else mainTitleButton.setBackgroundResource(R.color.num_chack_norice_green)
            imDeleteQuiz.setOnClickListener {
                listener.deleteItem(quiz.id!!)
            }

            var goHardQuiz =
                "${this.root.context.getString(R.string.go_hard_question)} - ${quiz.nameQuestion}"

            if (quiz.stars == MAX_PERCENT) {
                Toast.makeText(binding.root.context, goHardQuiz, Toast.LENGTH_SHORT).show()
            }

            if (quiz.stars >= MAX_PERCENT) {
                tvHardQuiz.setText(R.string.hard_question)
                tvHardQuiz.setBackgroundResource(R.color.num_chack_norice_red)
            } else {
                tvHardQuiz.setText(R.string.light_question)
                tvHardQuiz.setBackgroundResource(R.color.num_chack_norice_green)
            }

            if (quiz.stars <= MAX_PERCENT) ratingBar.rating = (quiz.stars.toFloat() / 50)
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

        private fun ActivityMainItemBinding.tvVisibleOrGone() {
            if (tvAllStars.text == "0.00" || tvAllStars ==  null) tvAllStars.visibility = View.GONE
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
            const val PERCENT_TWO_STARS = 0.83333
            const val MAX_PERCENT = 100

            fun create(parent: ViewGroup): ItemHolder {
                Log.d("ShopingListAdapter", "create ")
                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.activity_main_item, parent, false)
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