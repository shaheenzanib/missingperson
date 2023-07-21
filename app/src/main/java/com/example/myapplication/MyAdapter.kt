package com.example.myapplication
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.firebase.database.core.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyAdapter(private val context: android.content.Context, private var dataList: List<DataClass>) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MyViewHolder(view)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(dataList[position].dataImage).into(holder.recImage)
        holder.recName.text = dataList[position].Name
        holder.reccontact.text = dataList[position].contactnum
        holder.recDesc.text = dataList[position].description
        holder.recAge.text = dataList[position].age
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var recImage: ImageView
    var recName: TextView
    var reccontact: TextView
    var recAge: TextView
    var recDesc: TextView
    var recCard: CardView

    init {
        recImage = itemView.findViewById(R.id.recImage)
        recName = itemView.findViewById(R.id.recName)
        reccontact = itemView.findViewById(R.id.reccontact)
        recDesc = itemView.findViewById(R.id.recDesc)
        recAge = itemView.findViewById(R.id.recAge)
        recCard = itemView.findViewById(R.id.recCard)
    }
}
