package dev.abdulrafay.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import dev.abdulrafay.notes.databinding.ActivityMainBinding
import dev.abdulrafay.notes.databinding.ActivitySecondLandingBinding

class SecondLanding : AppCompatActivity() {

    lateinit var binding : ActivitySecondLandingBinding
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            toggle = ActionBarDrawerToggle(this@SecondLanding, drawerLayout, R.string.open_nav, R.string.close_nav)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            navView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.nav_account -> {
                        Toast.makeText(this@SecondLanding, "First Item Clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.nav_settings -> {
                        Toast.makeText(this@SecondLanding, "Second Item Clicked", Toast.LENGTH_SHORT).show()
                    }

                    R.id.add -> {
                        val i = Intent(this@SecondLanding,MainActivity::class.java)
                        startActivity(i)
                    }

                    R.id.nav_logout -> {
                        Toast.makeText(this@SecondLanding, "third Item Clicked", Toast.LENGTH_SHORT).show()
                    }

                    R.id.feedback->{
                        val i =Intent(this@SecondLanding,Feedback::class.java)
                        startActivity(i)
                    }
                }
                true
            }
        }

        val addnotes  = findViewById<CardView>(R.id.addnotes)
        addnotes.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        val logout = findViewById<CardView>(R.id.logout)

        logout.setOnClickListener {
            Toast.makeText(this, "you have successfully logged off", Toast.LENGTH_SHORT).show()
            FirebaseAuth.getInstance().signOut();
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }
}