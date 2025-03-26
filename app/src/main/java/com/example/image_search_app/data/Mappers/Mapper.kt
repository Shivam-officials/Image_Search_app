package com.example.image_search_app.data.Mappers

interface Mapper<F,T> {

    fun map(from:F):T
}


// how its working => in every generic funtion we have to declare the generic type in fun<> which we are going to use in our function 1st
fun<F,T> Mapper<F,T>.mapAll(list:List<F>) = list.map { map(it) }