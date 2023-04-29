package dev.abdulrafay.notes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import dev.abdulrafay.notes.databinding.ActivityLoginBinding
import org.w3c.dom.Text

class Login : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    lateinit var nametv : TextView
    lateinit var emailtv:TextView
    lateinit var passtv :TextView
    lateinit var sharedpreferences: SharedPreferences
    val mypreference = "myprefer"
    val Name = "nameKey"
    val Email = "emailKey"
    val passi = "passi"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        nametv = findViewById(R.id.name)
        emailtv =findViewById(R.id.email)
        passtv = findViewById(R.id.password)

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE)
        nametv.text= sharedpreferences.getString(Name,"");
        emailtv.text= sharedpreferences.getString(Email,"");
        passtv.text = sharedpreferences.getString(passi,"");


        binding.register.setOnClickListener {
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
            finish()
        }

        binding.loginNew.setOnClickListener {

            val email =binding.email.text.toString()
            val pass = binding.password.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty() ){

                firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
                    if(it.isSuccessful){
                        Log.i("working","pauch rha h")
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Log.i("not working"," nih pauch rha h")

                        Toast.makeText(this,"Some Error Occured ", Toast.LENGTH_SHORT).show()
                    }

                    Log.i("gadbad","gadbada rha h")

                }
            }

            else{
                Toast.makeText(this,"Field cannot be Empty !! ", Toast.LENGTH_SHORT).show()
            }
        }

    }
}