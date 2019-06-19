package com.roy.anubhav.core.utils.network

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import com.roy.anubhav.core.data.Book
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL

class NetworkUtils {

    companion object{

        fun convertStreamToString(`is`: InputStream): StringBuilder {
            val reader = BufferedReader(InputStreamReader(`is`))
            val sb = StringBuilder()

            var line: String? = null
            line = reader.readLine()
            try {
                var isResult = false
                while ((line) != null) {

                    if(line.trim().length>=3) {

                        if(!isResult)
                            isResult = line.trim().substring(0, 3).equals("<tr")
                    }

                    if(line.trim().length>=15) {
                        if(!isResult)
                            if( line.substring(0,15).equals("<td><b>Edit</b>")){
                                isResult = true
                                line = line.substring(25)
                            }
                    }


                    if(line.trim().length>=4){
                        if (line.trim().substring(0, 4).equals("</tr")) {

                            if(line.trim().length>=13)
                                if(line.trim().equals("</tr></table>"))
                                    sb.append("</tr>").append('\n')
                                else
                                    sb.append(line).append('\n')
                            else
                                sb.append(line).append('\n')

                            isResult = false
                        }
                    }

                    if(isResult){
                        sb.append(line).append('\n')
                    }

                    line = reader.readLine()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    `is`.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
            return sb
        }

        fun createBookList(stringBuilder:StringBuilder):ArrayList<Book>{
            val books = ArrayList<Book>()
            var numberOfLines = 0
            val data = stringBuilder.toString()
            val `in`= data.byteInputStream()
            val reader = BufferedReader(InputStreamReader(`in`))
            var line: String? = null

            line = reader.readLine()
            numberOfLines = 1

            try{

                var author= ""
                var name =""
                var url=""
                var language=""
                var size=""

                while ((line) != null) {

                    when (numberOfLines) {
                        2 -> {       //Author Name ( Can be null )
                            if (line.trim().equals("<td><a href='search.php?req=&column[]=author'></a></td>"))
                                author = "" //No Author
                            else
                            {
                                val authors = line.trim().substring(4,(line.trim().length-5))
                                author = createAuthorList(authors)
                            }
                        }
                        3 -> {     //Book Name
                            name = createBookName(line)
                        }
                        7 -> {     //Language
                            language = createLanguageorSize(line)
                        }
                        8 -> {         //Size
                            size = createLanguageorSize(line)
                        }
                        11 -> {        //URL . Get the md5 for the book
                            url = getMD5ForBook(line)
                        }
                        12 -> {        //Reset
                            books.add(Book(name = name,author = author,downloadURL = url,language = language,size = size))
                            author = ""
                            name =""
                            url=""
                            language=""
                            size=""
                            numberOfLines=0
                        }
                    }
                    line = reader.readLine()
                    numberOfLines += 1
                }

            }catch (e: IOException) {
                e.printStackTrace()
            } finally {

                try {

                    `in`.close()

                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }


            return books
        }

        fun createAuthorList(authors:String):String{
            var author = ""

            var bracketCounter = 0
            var appendToAuthor = false
            for ( character in authors){
                if(character=='<'||character=='>') {

                    bracketCounter += 1

                    if(appendToAuthor)
                        author += " , "

                    appendToAuthor = (bracketCounter-2)%4==0

                    continue
                }

                if(appendToAuthor){
                    author += character
                }

            }

            return author.substring(0,author.length-2)
        }


        fun createBookName(bookName:String):String{

            var title = ""
            var bracketCounter = 0

            for ( character in bookName) {
                if (character == '<' || character == '>') {
                    bracketCounter += 1
                    if(bracketCounter==5)
                        break
                    continue
                }

                if(bracketCounter==4)
                    title += character
            }

            return title
        }

        fun createLanguageorSize(dataHTML:String):String{
            var data = ""
            var bracketCounter = 0

            for ( character in dataHTML) {
                if (character == '<' || character == '>') {
                    bracketCounter += 1
                    if(bracketCounter==3)
                        break
                    continue
                }

                if(bracketCounter==2)
                    data += character
            }

            return data
        }

        fun getMD5ForBook(dataHTML: String):String{

            return "http://libgen.io/get.php?md5="+dataHTML.trim().substring(71,103)
        }

        /**
         * @param context used to check the device version and DownloadManager information
         * @return true if the download manager is available
         */
        fun isDownloadManagerAvailable(context: Context): Boolean {

            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                true
            } else false
        }

        fun downloadURL(context: Context, url:String, title:String){
            val request = DownloadManager.Request(Uri.parse(url))
            request.setTitle(title)
            // in order for this if to run, you must use the android 3.2 to compile your app
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                request.allowScanningByMediaScanner()
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            }
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"libgen")

            // get download service and enqueue file
            val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
            manager!!.enqueue(request)
        }

        private

        val prefixURL = "http://libgen.is/search.php?req="
        val postfixURL = "&phrase=1&view=simple&column=def&sort=def&sortmode=ASC&page="

        fun createURL(query:String,pageNumber:Int): URL {

            var urlQuery = ""

            for(character in query){
                if(character==' '){
                    urlQuery +=  "+"
                    continue
                }

                urlQuery += character
            }

            return URL(prefixURL+urlQuery+ postfixURL+pageNumber)
        }
    }

}