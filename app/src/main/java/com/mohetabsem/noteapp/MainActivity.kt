package com.mohetabsem.noteapp

import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Build
import android.util.Log.wtf
import androidx.annotation.RequiresApi
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    var myRef: DatabaseReference? =null
    var mTodo :ArrayList<Todo>?=null
    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mTodo= ArrayList()
        //db
        val database = FirebaseDatabase.getInstance()
         myRef = database.getReference("nots")


        //var arrayAdapter= arrayListOf<String>()

        //list.adapter=myAddapter(applicationContext,arrayAdapter)

        add.setOnClickListener {
            var id =myRef!!.push().key
            var inputVal=input.text.toString()
            var currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE hh:mm:ss a"))
           // var nowTime= ServerValue.TIMESTAMP
            var isDone = false
            if (inputVal.isEmpty()){
                Toast.makeText(applicationContext, "empty", Toast.LENGTH_SHORT).show()
            }else{
                var addedTodo = Todo(id!!,inputVal,"$currentDateTime",false)
                //arrayAdapter.add("${inputVal}")
                //list.adapter=myAddapter(applicationContext, mTodo!!)
                input.setText("")
                myRef!!.child(id!!).setValue(addedTodo)
//                myRef.child("$currentDateTime").child("txt").setValue("${inputVal}")
//                myRef.child("$currentDateTime").child("isDone").setValue(false)
                wtf("@@","$currentDateTime")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // get data from dp
        myRef?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "no zeft", Toast.LENGTH_SHORT).show()
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.children==null){
                    Toast.makeText(applicationContext,"is loading",Toast.LENGTH_SHORT).show()
                }
                mTodo?.clear()
                for (m in snapshot!!.children ){ // add keys
                    var todo =m.getValue(Todo::class.java)
                    mTodo?.add(0,todo!!)
                    //wtf("++","${m.getValue(Todo::class.java)}")
                }
                list.adapter=myAddapter(applicationContext, mTodo!!)
            }
        })

    }

}
