package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{
    var databaseReference: DatabaseReference? = null
    var eventListener: ValueEventListener? = null
    private lateinit var drawerLayout:DrawerLayout
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout= findViewById<DrawerLayout>(R.id.drawer_layout)
        val toolbar= findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        val navigationView=findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle= ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_nav,R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.addReport.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, UploadActivity::class.java)
            startActivity(intent)
    })
        binding.viewList.setOnClickListener(View.OnClickListener{
            val intent = Intent(this@MainActivity, ViewList::class.java)
            startActivity(intent)
        })
        binding.foundMissing.setOnClickListener(View.OnClickListener{
            val intent = Intent(this@MainActivity, FoundPerson::class.java)
            startActivity(intent)
        })
}
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.notifications -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,Notifications()).commit()
            R.id.about_us -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,AboutUs()).commit()
            R.id.share -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,Share()).commit()

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            onBackPressedDispatcher.onBackPressed()
        }
    }

}