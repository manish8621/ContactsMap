package com.mk.contactsmap.model.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts_table")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0L,
    var name:String,
    var gender:String="MALE",
    var number:String?=null,
    var email:String?=null,
    val photoPath:String?=null,
    @Embedded
    val location: Location?=null
){
    companion object{
        const val MALE = "MALE"
        const val FEMALE = "FEMALE"
    }
}