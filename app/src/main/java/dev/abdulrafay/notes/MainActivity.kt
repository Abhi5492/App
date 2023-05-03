package dev.abdulrafay.notes

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import dev.abdulrafay.notes.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyRecyclerViewAdapter
    private lateinit var searchView: SearchView
    private var arrayOfNotes: ArrayList<Notes> = ArrayList()
    var arrayList: ArrayList<Notes> = ArrayList()
    lateinit var sharedpreferences: SharedPreferences
    lateinit var name :TextView
    lateinit var email:TextView
    val mypreference = "myprefer"
    val Name = "nameKey"
    val Email = "emailKey"

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)
        searchView.clearFocus()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                filteredList(p0)
                return true
            }
        })

        val database = NotesDBHelper.getInstance(this)
        database.noteDao().getNotes().observe(this, Observer {
            arrayList = it as ArrayList<Notes>
            showAllNotes(it as ArrayList)
        })

        findViewById<FloatingActionButton>(R.id.floatingBtnAdd)
            .setOnClickListener {
                startActivity(Intent(this@MainActivity, AddNotesActivity::class.java))
            }
    }




    private fun filteredList(text: String?) {
        val listOfFilteredNoted: ArrayList<Notes> = ArrayList()
        for (i in arrayList) {
            if (text != null) {
                if (i.title.lowercase().contains(text.trim().lowercase()) || i.body.lowercase().contains(text.trim().lowercase())) {
                    listOfFilteredNoted.add(i)
                }
            }

            if (text != null) {
                if (text.isEmpty()) {
                    adapter.setFilteredList(arrayList)
                }
            }
            adapter.setFilteredList(listOfFilteredNoted)

        }
    }

    private fun showAllNotes(arrayListNotes: ArrayList<Notes>) {
        if (arrayListNotes.isEmpty()) {
            findViewById<TextView>(R.id.textViewIfEmpty).visibility = View.VISIBLE
            searchView.visibility = View.GONE
            recyclerView.visibility = View.GONE
        } else {
            findViewById<TextView>(R.id.textViewIfEmpty).visibility = View.INVISIBLE
            arrayListNotes.reverse()
            adapter = MyRecyclerViewAdapter(this, arrayListNotes)
            recyclerView.visibility = View.VISIBLE
            recyclerView.adapter = adapter
            searchView.visibility = View.VISIBLE
            recyclerView.layoutManager = LinearLayoutManager(this)
        }
    }

    override fun onResume() {
        super.onResume()
        searchView.clearFocus()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.delete_all, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.delete_all -> {
                if (arrayList.isEmpty()) {
                    Toast.makeText(this, "No notes to delete", Toast.LENGTH_SHORT).show()
                } else {

                    val dialog = AlertDialog.Builder(this)
                        .setTitle("Delete all")
                        .setMessage("Are you sure you want to delete all notes")
                        .setIcon(R.drawable.delete)
                        .setPositiveButton(
                            "Yes",
                            DialogInterface.OnClickListener { dialogInterface, i ->
                                GlobalScope.launch {
                                    val database = NotesDBHelper.getInstance(this@MainActivity)
                                    database.noteDao().deleteAllNotes()
                                }
                            })
                        .setNegativeButton(
                            "No",
                            DialogInterface.OnClickListener { dialogInterface, i -> })
                        .show()
                }
            }
            R.id.logout-> {
                Toast.makeText(this, "you have successfully logged off", Toast.LENGTH_SHORT).show()
                FirebaseAuth.getInstance().signOut();
                val intent = Intent(this,Login::class.java)
                startActivity(intent)
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
