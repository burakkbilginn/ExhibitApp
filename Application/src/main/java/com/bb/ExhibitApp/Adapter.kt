package com.bb.ExhibitApp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bb.model.Exhibit
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.example_item.view.*
import java.util.ArrayList


class ExampleAdapter(
    private val exampleList: MutableList<Exhibit>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.example_item,
            parent,
            false
        )
        return ExampleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = exampleList[position]

        holder.textView1.text = currentItem.title
//        holder.textView3.text = currentItem.images

        holder.bind(exampleList[position])

//        VİEWPAGER İÇİN HEPSİ BU KADAR:
        val viewPager: ViewPager = holder.itemView.findViewById(R.id.viewPager)
        val imageAdapter: ImageViewPagerAdapter = ImageViewPagerAdapter(holder.imageView.context, exampleList[position].images)
        viewPager.adapter = imageAdapter

////        To transfer the data to new activity:
//        holder.itemView.setOnClickListener {
//            val intent = Intent(it.context, SecondActivity::class.java)
//            intent.putExtra("addonNameKey", exampleList[position].title)
//            intent.putExtra("addonDescriptionKey", exampleList[position].images)
////            intent.putExtra("addonImagepathKey", exampleList[position].imageLink)
//            intent.putExtra("favIconTag", holder.favIcon.tag?.toString())
//            it.context.startActivity(intent)
//        }

    }


    override fun getItemCount(): Int = exampleList.size

    //inner class = static class equivalent of Java
    inner class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val imageView: ImageView = itemView.row_img
        val textView1: TextView = itemView.row_tv_title  //name
//        var favIcon: ImageView = itemView.favIcon




        init {
            itemView.setOnClickListener(this)
//            BURASI ÇOK ÖNEMLİ!! Onclick listener on a specific item in recyclerview:
//            itemView.favIcon.setOnClickListener {
////                val position = adapterPosition
//                listener.favIconClick(this.position)
//             }

        }


        override fun onClick(v: View?) {
            val position = adapterPosition
//            val link = exampleList[position].imageLink
//            println("val link = " + link)


            if (position != RecyclerView.NO_POSITION) {  //if this line not added, then crashes while item animating
                listener.onItemClick(position)  //bunu yazmazsan itemclick çalışmaz
            }
        }

        fun bind(exhibit: Exhibit) {
            textView1.text = exhibit.title
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    //exampleList class içindeki val değerlerini silmeyince unresolved reference hatası veriyor:
    fun set(list: MutableList<Exhibit>) {
        this.exampleList.clear()
        this.exampleList.addAll(list)
        notifyDataSetChanged()
    }
}

class ExhibitViewModel: ViewModel() {
}



