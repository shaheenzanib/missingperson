package com.example.myapplication

class  DataClass{
    var Name: String? = null
    var contactnum: String? = null
    var age: String? = null
    var description: String? = null
    var dataImage: String? = null

    constructor(Name: String?="", contactnum: String?="", age: String?="",description: String?="", dataImage: String?="") {
        this.Name = Name
        this.contactnum = contactnum
        this.age = age
        this.description = description
        this.dataImage = dataImage
    }

}
