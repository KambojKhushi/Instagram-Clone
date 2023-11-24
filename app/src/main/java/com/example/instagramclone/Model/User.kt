package com.example.instagramclone.Model

class User {
    private var Username:String=""
    private var fullname:String=""
    private var bio:String=""
    private var image:String=""
    private var uid:String=""

    constructor()

    constructor(Username: String,fullname:String,bio:String,image:String,uid:String){
        this.Username=Username
        this.fullname=fullname
        this.bio=bio
        this.image=image
        this.uid=uid
    }

    fun getUsername():String{
        return Username
    }
    fun setUsername(Username: String){
        this.Username=Username
    }

    fun getfullname():String{
        return fullname
    }
    fun setfullname(fullname: String){
        this.fullname=fullname
    }

    fun getbio():String{
        return bio
    }
    fun setbio(bio: String){
        this.bio=bio
    }

    fun getimage():String{
        return image
    }
    fun setimage(image: String){
        this.image=image
    }

    fun getuid():String{
        return uid
    }
    fun setuid(uid: String){
        this.uid=uid
    }

}