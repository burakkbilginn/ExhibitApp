package com.bb.ExhibitApp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_first.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*
import com.bb.fileexhibitsloader.JsonParsing
import com.bb.model.Exhibit


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class FirstFragment : Fragment(), ExampleAdapter.OnItemClickListener {

    private val exampleList = mutableListOf<Exhibit>()
    private val adapter = ExampleAdapter(exampleList, this)
    private lateinit var viewModel: ExhibitViewModel
    private lateinit var url: String
//    private lateinit var mainActivity: MainActivity
    private var isParsed: Boolean = false  //to stop reloading when resume
    private var favIconCounter = 0

    private lateinit var mainActivity: MainActivity

    private lateinit var favoriteList: ArrayList<Exhibit>
    private val list = ArrayList<Exhibit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        mainActivity = this.activity as MainActivity  //to initialize lateinit property MainActivity
        mainActivity.supportActionBar?.title = "Homepage"



    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        viewModel = ViewModelProviders.of(this).get(ExhibitViewModel::class.java)

        recycler_view.layoutManager = LinearLayoutManager(this.context)
        recycler_view.setHasFixedSize(true)
//        adapter = ExampleAdapter()
        recycler_view.adapter = adapter

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v: View
        v = inflater.inflate(R.layout.fragment_first, container, false)

        //buraya koymazsan null pointer exception oluyor!!! Inflate'ten sonra koycaksın!
        val recycler_view = v.findViewById<RecyclerView>(R.id.recycler_view)


////        VİEWPAGER İÇİN HEPSİ BU KADAR:
//        val viewPager: ViewPager = requireActivity().findViewById(R.id.viewPager)
//        val imageAdapter: ImageViewPagerAdapter = ImageViewPagerAdapter(requireActivity())
//        viewPager.adapter = imageAdapter


            val inputMethodManager = mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)


        if (!isParsed) {
            Thread(Runnable {
                requireActivity().runOnUiThread(java.lang.Runnable {
                    getExhibitList()
                })
            }).start()

        }


        return v
    }

    fun getExhibitList() {
        var json: String? = null

        try {
            var reader: BufferedReader? = null
            try {
                reader = BufferedReader(InputStreamReader(requireActivity().assets.open("JsonContent.json")))
                json = reader.use { it.readText() }
            } catch (e: IOException) {
            } finally {
                reader?.close()
            }

//                val inputStream: InputStream = requireActivity().assets.open("JsonContent.json")
//                json = inputStream.bufferedReader().use { it.readText() }

//            println(json)
            val jsonArray = JSONObject(json!!).getJSONArray("list")

            for (i in 0 until jsonArray.length()) {


//                array.add(jsonobject.getJSONObject("$i").toString())

                val title = jsonArray.getJSONObject(i).getString("title")
                val imagesJsonArray = jsonArray.getJSONObject(i).getJSONArray("images")



                println("for i = $i")
                println("title = " + title)

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

            adapter.set(list)

        } catch (e: IOException) {

        }

        isParsed = true
    }

    override fun onResume() {
        super.onResume()
        mainActivity.supportActionBar?.title = "Homepage"
    }



    override fun onItemClick(position: Int) {
        Utils.showToast(this.context, "Item $position clicked");
        println("Item at $position clicked")
//        val clickedItem = exampleList[position]
//        clickedItem.title = "Clicked"
        adapter.notifyItemChanged(position)
    }

//    fun JSONArray.toList(): List<JSONObject> = List(length(), this::getJSONObject)

    fun readJson() {

    }

}
