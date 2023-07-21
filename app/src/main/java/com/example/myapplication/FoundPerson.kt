package com.example.myapplication

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myapplication.databinding.ActivityFoundPersonBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.text.DateFormat
import java.util.*

class FoundPerson : AppCompatActivity() {

    private lateinit var binding: ActivityFoundPersonBinding
    var imageURL: String? = null
    var uri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoundPersonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val activityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                uri = data!!.data
                binding.fImage.setImageURI(uri)
            } else {
                Toast.makeText(this@FoundPerson, "No Image Selected", Toast.LENGTH_SHORT).show()
            }
        }
        binding.fImage.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            activityResultLauncher.launch(photoPicker)
        }
        binding.save.setOnClickListener {
            saveData()
        }
    }
    private fun saveData() {
        val storageReference = FirebaseStorage.getInstance().reference.child("Task Images")
            .child(uri!!.lastPathSegment!!)
        val builder = AlertDialog.Builder(this@FoundPerson)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()
        storageReference.putFile(uri!!).addOnSuccessListener { taskSnapshot ->
            val uriTask = taskSnapshot.storage.downloadUrl
            while (!uriTask.isComplete);
            val urlImage = uriTask.result
            imageURL = urlImage.toString()
            uploadData()
            dialog.dismiss()
        }.addOnFailureListener {
            dialog.dismiss()
        }
    }

    private fun uploadData(){
        val fname = binding.fname.text.toString()
        val contactnmbr = binding.contactnmbr.text.toString()
        val status = binding.status.text.toString()
        val description = binding.fdescription.text.toString()
        val dataClass = FdataClass(fname, contactnmbr, status,description, imageURL)
        val currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().time)
        FirebaseDatabase.getInstance().getReference("My Application").child(currentDate)
            .setValue(dataClass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@FoundPerson, "Saved", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }.addOnFailureListener { e ->
                Toast.makeText(
                    this@FoundPerson, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }

}