package com.bb.fileexhibitsloader

import android.app.Application
import android.content.Context
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.ArrayList
import com.bb.fileexhibitsloader.JsonParsing
import com.bb.model.Exhibit


class JsonParsing {
    private var json: String? = null
    private val list = ArrayList<Exhibit>()
    private var mContext: Context? = null

    private fun MyHelper(context: Context) {
        mContext = context.applicationContext
    }


    fun getExhibitlist() {
        try {
            var reader: BufferedReader? = null
            try {
                reader =
                    BufferedReader(InputStreamReader(mContext?.assets!!.open("JsonContent.json")))
                json = reader.use { it.readText() }
            } catch (e: IOException) {
            } finally {
                reader?.close()
            }


            val jsonArray = JSONObject(json!!).getJSONArray("list")

            for (i in 0 until jsonArray.length()) {


//                array.add(jsonobject.getJSONObject("$i").toString())

                val title = jsonArray.getJSONObject(i).getString("title")
                val imagesJsonArray = jsonArray.getJSONObject(i).getJSONArray("images")



                println("for i = $i")
                println("title = " + title)


//                for (x in 0 until images.size) {
//                    println("images[$x] = " + images.get(x))
//                }

//                var list = ArrayList<String?>()
//                val jsonArray = jsonObject as JSONArray?
//                if (jsonArray != null) {
//                    val len = jsonArray.length()
//                    for (i in 0 until len) {
//                        list.add(jsonArray[i].toString())
//                    }
//                }

                val images = ArrayList<String>()
                for (a in 0 until imagesJsonArray.length()) {
                    //                        images.add(imagesJsonArray?.get(a).toString())
                    images += imagesJsonArray.get(a).toString()


                }
                println("images = " + images)
                println("Fragman içi images[0] = " + images.get(0))


                val item = Exhibit(
                    title, images
                )
                list += item


            }


////         BURAYI EKLE:
//            adapter.set(list)

        }     catch(e: IOException) {
            }

        }


    }