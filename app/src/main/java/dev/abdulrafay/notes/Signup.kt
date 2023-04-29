package dev.abdulrafay.notes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import dev.abdulrafay.notes.databinding.ActivitySignupBinding


class Signup : AppCompatActivity() {

    private lateinit var binding : ActivitySignupBinding
    private lateinit var firebaseAuth : FirebaseAuth
    lateinit var sharedpreferences: SharedPreferences
    val mypreference = "myprefer"
    val Name = "nameKey"
    val Email = "emailKey"
    val passi = "passi"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding  = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.login.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

        binding.register.setOnClickListener{
            val name = binding.name.text.toString()
            val email =binding.email.text.toString()
            val pass = binding.password.text.toString()
            val confpass = binding.confirmPassword.text.toString()

            sharedpreferences = getSharedPreferences(mypreference,Context.MODE_PRIVATE)

            if(email.isNotEmpty() && pass.isNotEmpty() && confpass.isNotEmpty()){
                if(pass==confpass){
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener {
                        if(it.isSuccessful){

                            val n = name
                            val e = email
                            val p = pass
                            val editor= sharedpreferences.edit()
                            editor.putString(Name,n)
                            editor.putString(Email,e)
                            editor.putString(passi,p)
                            editor.apply()

                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else{
                            Toast.makeText(this,"Some Error Occured ", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else{
                    Toast.makeText(this,"Password not matching ", Toast.LENGTH_SHORT).show()
                }
            }

            else{
                Toast.makeText(this,"Field cannot be Empty !! ", Toast.LENGTH_SHORT).show()
            }
        }



    }
}