package com.mohetabsem.noteapp

class Todo{
    var id:String?=null
    var text:String?=null
    var time:String?=null
    var deatails:String?=null
    var isDone:Boolean?=null
    constructor(){

    }
    constructor(
        id:String,
        text:String,
        time: String,
        deatails:String,
        isDone:Boolean){
        this.id=id
        this.text=text
        this.time=time
        this.deatails=deatails
        this.isDone=isDone

    }
}