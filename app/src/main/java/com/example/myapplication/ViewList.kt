package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.databinding.ActivityViewListBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*

class ViewList : AppCompatActivity() {
    var databaseReference: DatabaseReference? = null
    var eventListener: ValueEventListener? = null
    private lateinit var dataList: ArrayList<DataClass>
    private lateinit var adapter: MyAdapter
    private lateinit var binding: ActivityViewListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        dataList = ArrayList()
        val gridLayoutManager = GridLayoutManager(this@ViewList, 1)
        binding.recyclerView.layoutManager = gridLayoutManager
        val builder = AlertDialog.Builder(this@ViewList)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()
        dataList = ArrayList()
        adapter = MyAdapter(this@ViewList, dataList)
        binding.recyclerView.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance().getReference("My Application")
        dialog.show()
        eventListener = databaseReference!!.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (itemSnapshot in snapshot.children) {
                    val dataClass = itemSnapshot.getValue(DataClass::class.java)
                    if (dataClass != null) {
                        dataList.add(dataClass)
                    }
                }
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            override fun onCancelled(error: DatabaseError) {
                dialog.dismiss()

            }

    })
    }
}