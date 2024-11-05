package test1a.c14220280.recycleview;

import android.util.Log
import android.view.LayoutInflater
import android.view.View;
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso

import java.util.ArrayList;

class adapterRecView(private val listWayang:ArrayList<wayang>):
    RecyclerView.Adapter<adapterRecView.ListViewHolder>() {

    inner class ListViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {

        var _namaWayang = itemView.findViewById<TextView>(R.id.tvNamaWayang)
        var _karakterWayang = itemView.findViewById<TextView>(R.id.tvKarakterWayang)
        var _deskripsiWayang = itemView.findViewById<TextView>(R.id.tvDeskripsiWayang)
        var _gambarWayang = itemView.findViewById<ImageView>(R.id.ivGambarWayang)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {

        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler, parent, false)
        return ListViewHolder(view)

    }

    override fun getItemCount(): Int {

        return listWayang.size

    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        var wayang = listWayang[position]

        holder._namaWayang.text = wayang.nama
        holder._deskripsiWayang.text = wayang.deskripsi
        holder._karakterWayang.text = wayang.karakter

        Log.d("TEST", wayang.foto)

        Picasso.get()
            .load(wayang.foto)
            .into(holder._gambarWayang)

    }

}

