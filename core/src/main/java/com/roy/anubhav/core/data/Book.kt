package com.roy.anubhav.core.data


class Book (val name:String,val downloadURL:String,val language:String,
            val size:String ,val author:String) {

    companion object {
        fun printBook(book: Book) {
            System.out.println("Name :- " + book.name)
            System.out.println("downloadURL :- " + book.downloadURL)
            System.out.println("language :- " + book.language)
            System.out.println("size :- " + book.size)
            System.out.println("author :- " + book.author)
        }
    }
}