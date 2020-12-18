package com.mohetabsem.noteapp

import android.os.Bundle
import android.util.Log.wtf
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_deatils.*


class deatils : AppCompatActivity() {
    var myRef: DatabaseReference? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deatils)
        //var id = intent.extras?.getString("id")
        val id = intent.extras?.getString("txtId").toString()
        val database = FirebaseDatabase.getInstance()
        myRef = database.getReference("nots").child(id)
        wtf("id","$id")

        myRef!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value =
                    dataSnapshot.getValue(Todo::class.java)
                wtf("new",">> ${value?.id}")
                titleTodo.text="${value?.text}"
                deatails.setText(value?.text)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value

            }
        })

        save.setOnClickListener {
            val dtxt = deatails.text.toString()
            myRef!!.child("deatails").setValue(dtxt)

        }
    }
}
