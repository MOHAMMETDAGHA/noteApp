package com.mohetabsem.noteapp

import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Build
import android.util.Log.wtf
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.item.view.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

import android.widget.CompoundButton
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.yalantis.beamazingtoday.R2
import com.yalantis.beamazingtoday.R2.id.view


class MainActivity : AppCompatActivity() {
    var myRef: DatabaseReference? =null
    var mTodo :ArrayList<Todo>?=null
    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mTodo = ArrayList()
        var currentDay = LocalDateTime.now().format(DateTimeFormatter.ofPattern("d MMM hh:mm a"))
        todayDate.text = currentDay
        var currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEE"))
        today.text = currentDate
        val todo_inflator = LayoutInflater.from(applicationContext).inflate(R.layout.item, null)


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

                //arrayAdapter.add("${inputVal}")
                //list.adapter=myAddapter(applicationContext, mTodo!!)
                input.setText("")
                post2db(id!!,inputVal,"$currentDateTime","**",false)
//                myRef.child("$currentDateTime").child("txt").setValue("${inputVal}")
//                myRef.child("$currentDateTime").child("isDone").setValue(false)
                wtf("@@","$currentDateTime")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val testArray = arrayOf("555","aaaa","3232")
        val testa = ArrayAdapter(this,android.R.layout.simple_list_item_1,testArray)
        list.adapter=testa
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
                var a = myAddapter(applicationContext, mTodo!!)
                list.adapter=a

            }
        })
//        list.onItemClickListener =AdapterView.OnItemClickListener { parent, view, position, id ->
//            Toast.makeText(applicationContext,"first",Toast.LENGTH_SHORT).show()
//            wtf("##","wark")
//        }
//        var a = myAddapter(applicationContext, mTodo!!)
//        list.adapter=a
        list.setOnItemClickListener { adapterView, view, position, id ->
             Toast.makeText(applicationContext,"first",Toast.LENGTH_SHORT).show()
            var thisTodo = mTodo?.get(position)
            var id= thisTodo!!.id
            var deatilIntent=Intent(this,deatils::class.java)

            wtf("main id","${id.toString()}")
            deatilIntent.putExtra("txtId",id)
            startActivity(deatilIntent)
        }

    }

    fun post2db(id:String,text:String,time:String,deatails:String,isDone:Boolean){
        var addedTodo = Todo(id!!,text,"$time",deatails,false)
        myRef!!.child(id!!).setValue(addedTodo)
    }
}
