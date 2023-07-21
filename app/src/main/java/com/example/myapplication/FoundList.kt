package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.databinding.ActivityFoundListBinding
import com.google.firebase.database.*

class FoundList : AppCompatActivity() {
    var databaseReference: DatabaseReference? = null
    var eventListener: ValueEventListener? = null
    private lateinit var dataList: ArrayList<FdataClass>
    private lateinit var adapter: Fadapter
    private lateinit var binding: ActivityFoundListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoundListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val gridLayoutManager = GridLayoutManager(this@FoundList, 1)
        binding.recyclerView.layoutManager = gridLayoutManager
        val builder = AlertDialog.Builder(this@FoundList)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()
        dataList = ArrayList()
        adapter = Fadapter(this@FoundList, dataList)
        binding.recyclerView.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance().getReference("My Application")
        dialog.show()
        eventListener = databaseReference!!.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (itemSnapshot in snapshot.children) {
                    val fdataClass = itemSnapshot.getValue(FdataClass::class.java)
                    if (fdataClass != null) {
                        dataList.add(fdataClass)
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