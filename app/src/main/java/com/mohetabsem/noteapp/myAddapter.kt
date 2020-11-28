package com.mohetabsem.noteapp

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.item.view.*

class myAddapter (context: Context, todos:ArrayList<Todo>):
    ArrayAdapter<Todo>(context , 0,todos){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout_inflator = LayoutInflater.from(context).inflate(R.layout.item,parent,false)
        val todo: Todo? =getItem(position)
        layout_inflator.listItem.text= todo!!.text
        layout_inflator.date.text= todo!!.time

        return layout_inflator
    }



}

