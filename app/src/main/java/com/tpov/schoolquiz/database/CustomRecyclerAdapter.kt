package com.tpov.schoolquiz.database

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tpov.schoolquiz.Quiz
import com.tpov.schoolquiz.R
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class CustomRecyclerAdapter(
    private val names: List<String>,
    var varCloseList: UpdateData,
    var codeAnswer2Array: MutableList<Char>,
    var currentIndex2: Int,
    var QUESTION_BANK: MutableList<Quiz>
) : RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var num_question_view: TextView? = null
        var short_question_text_view: TextView? = null

        init {
            num_question_view = itemView.findViewById<EditText>(R.id.num_question_view) as? TextView
            short_question_text_view =
                itemView.findViewById(R.id.short_question_text_view) as? TextView
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_question_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int){

        holder.num_question_view?.text = "$position"
        holder.short_question_text_view?.text = names[position].toString()

        if (currentIndex2 == position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#009999"))
        } else {
            when {
                codeAnswer2Array[position] == '0' -> {
                    holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"))
                }
                codeAnswer2Array[position] == '1' -> {
                    holder.itemView.setBackgroundColor(Color.parseColor("#FF3300"))
                }
                codeAnswer2Array[position] == '2' -> {
                    holder.itemView.setBackgroundColor(Color.parseColor("#00FF00"))
                }
            }
        }
        holder.itemView.setOnClickListener {
            varCloseList.closeList(position)
        }
    }

    interface UpdateData {
        fun closeList(position: Int)
    }

    override fun getItemCount(): Int {
        return QUESTION_BANK.size
    }
}