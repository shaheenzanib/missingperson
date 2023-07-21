package com.example.myapplication

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.ActivityResult
import com.example.myapplication.databinding.ActivityUploadBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.net.URI
import java.text.DateFormat
import java.util.Calendar

class UploadActivity : AppCompatActivity() {
    private val REQUEST_PERMISSION = 100
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_PICK_IMAGE = 2
    private lateinit var binding: ActivityUploadBinding
    var imageURL: String? = null
    var uri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val activityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                uri = data!!.data
                binding.uploadImage.setImageURI(uri)
            } else {
                Toast.makeText(this@UploadActivity, "No Image Selected", Toast.LENGTH_SHORT).show()
            }
        }
        binding.uploadImage.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            activityResultLauncher.launch(photoPicker)
        }
        binding.saveButton.setOnClickListener {
            saveData()
        }
    }
        private fun saveData() {
            val storageReference = FirebaseStorage.getInstance().reference.child("Task Images")
                .child(uri!!.lastPathSegment!!)
            val builder = AlertDialog.Builder(this@UploadActivity)
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
            val name = binding.Name.text.toString()
            val contactnum = binding.contactnum.text.toString()
            val age = binding.Age.text.toString()
            val description = binding.description.text.toString()
            val dataClass = DataClass(name, contactnum, age,description, imageURL)
            val currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().time)
            FirebaseDatabase.getInstance().getReference("My Application").child(currentDate)
                .setValue(dataClass).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@UploadActivity, "Saved", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@UploadActivity,ViewList::class.java)
                        startActivity(intent)
                        finish()
                    }
                }.addOnFailureListener { e ->
                    Toast.makeText(
                        this@UploadActivity, e.message.toString(), Toast.LENGTH_SHORT).show()
                }
        }

}