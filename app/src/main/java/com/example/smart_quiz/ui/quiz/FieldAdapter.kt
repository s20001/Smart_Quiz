package com.example.smart_quiz.ui.quiz

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.smart_quiz.R
import com.example.smart_quiz.model.Field
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController

class FieldAdapter(private val FieldList: MutableList<Field>)
    : RecyclerView.Adapter<FieldAdapter.ViewHolder>(){

        inner class ViewHolder(ListItemView: View): RecyclerView.ViewHolder(ListItemView){
                val field_name = ListItemView.findViewById<TextView>(R.id.tx_field)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.field_row, parent, false)

        val holder = ViewHolder(view)
        //クリック処理
        view.setOnClickListener {
            itemClickListener?.onItemClick(holder)
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("FieldAdapter", "===>$FieldList")
        holder.field_name.text = FieldList[position].name

    }

    override fun getItemCount() = FieldList.size

    var itemClickListener: OnItemClickListener? = null
    interface OnItemClickListener {
        fun onItemClick(holder: ViewHolder)
    }
}