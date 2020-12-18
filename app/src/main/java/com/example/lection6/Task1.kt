package com.example.lection6

import com.google.gson.Gson
import java.io.FileReader
import java.io.FileWriter

data class Person(
        val name: String,
        val surname: String,
        val age: Int,
        val isAdult: Boolean,
        var mother: Person? = null,
        var father: Person? = null,
        var relatives: Array<Person>? = null
) {
    var numberOfRelatives = 0
    var listOfRelatives: MutableList<Person> = mutableListOf()



    fun countRelatives(person: Person){
        this.mother?.let {
            person.listOfRelatives.add(it)
            person.numberOfRelatives++
            it.countRelatives(person)
        }
        this.father?.let {
            person.listOfRelatives.add(it)
            person.numberOfRelatives++
            it.countRelatives(person)
        }
        this.relatives?.let {
            it.forEach {
                person.listOfRelatives.add(it)
                person.numberOfRelatives++
                it.countRelatives(person)
            }
        }
    }
}

fun main() {
    val me = getMe()
    val jsonString = Gson().toJson(me)
    val filePathJson = "app/src/main/java/com/example/lection6/lection6.json"
    val readJson = FileReader(filePathJson)

    FileWriter(filePathJson).use {
        it.write(jsonString)

    }
    println(jsonString)

    val meNew: Person = Gson().fromJson(readJson, Person::class.java)
    println("$meNew")
}

fun getMe(): Person {
    var me = Person("Dmitry", "Hrybkov", 19, true)

    me.mother = Person("Tatiana", "Organa", 46, true)
    me.father = Person("Igor", "Kenobi", 47, true)
    me.mother!!.mother = Person("Tamara", "Amidala", 75, true)
    me.mother!!.father = Person("Viktor", "Vader", 80, true)
    me.father!!.mother = Person("Nina", "Naberrie", 86, true)
    me.father!!.father = Person("Anatoly", "Jarrus", 84, true)

    me.countRelatives(me)

    return me
}
