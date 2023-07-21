package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Fadapter(private val context: Context, private var fdataList: List<FdataClass>) : RecyclerView.Adapter<FViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.f_recycleritem, parent, false)
        return FViewHolder(view)
    }
    override fun onBindViewHolder(holder: FViewHolder, position: Int) {
        Glide.with(context).load(fdataList[position].fImage).into(holder.rImage)
        holder.rName.text = fdataList[position].fname
        holder.rcontact.text = fdataList[position].contactnmbr
        holder.rDesc.text = fdataList[position].fdescription
        holder.status.text = fdataList[position].status
    }

    override fun getItemCount(): Int {
        return fdataList.size
    }
}
class FViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var rImage: ImageView
    var rName: TextView
    var rcontact: TextView
    var status: TextView
    var rDesc: TextView
    var rCard: CardView

    init {
        rImage = itemView.findViewById(R.id.rImage)
        rName = itemView.findViewById(R.id.rName)
        rcontact = itemView.findViewById(R.id.rcontact)
        rDesc = itemView.findViewById(R.id.rDesc)
        status = itemView.findViewById(R.id.status)
        rCard = itemView.findViewById(R.id.rCard)
    }
}
