package test1a.c14220280.recycleview

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    lateinit var sp : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        sp = getSharedPreferences("dataSP", MODE_PRIVATE)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        _rvWayang = findViewById<RecyclerView>(R.id.rvWayang)

        if (_arWayang.size == 0) {
            SiapkanData()
        } else {
            _arWayang.forEach {
                _nama.add(it.nama)
                _gambar.add(it.foto)
                _deskripsi.add(it.deskripsi)
                _karakter.add(it.karakter)
            }
        }

        TambahData()
        TampilkanData()

        val gson = Gson()
        val isiSP = sp.getString("spWayang", null)
        val type = object : TypeToken<ArrayList<wayang>>() {}.type

        if (isiSP != null) {
            _arWayang = gson.fromJson(isiSP, type)
        }
    }

    private var _nama: MutableList<String> = emptyList<String>().toMutableList()
    private var _deskripsi: MutableList<String> = emptyList<String>().toMutableList()
    private var _karakter: MutableList<String> = emptyList<String>().toMutableList()
    private var _gambar: MutableList<String> = emptyList<String>().toMutableList()
    private var _arWayang = arrayListOf<wayang>()
    private lateinit var _rvWayang: RecyclerView

    fun SiapkanData() {
        _nama= resources.getStringArray(R.array.namaWayang).toMutableList()
        _deskripsi= resources.getStringArray(R.array.deskripsiWayang).toMutableList()
        _karakter= resources.getStringArray(R.array.karakterUtamaWayang).toMutableList()
        _gambar= resources.getStringArray(R.array.gambarWayang).toMutableList()
    }

    fun TambahData() {
        val gson = Gson()
        val editor = sp.edit()

        _arWayang.clear()

        for(position in _nama.indices) {
            val data= wayang(
                _gambar[position],
                _nama[position],
                _karakter[position],
                _deskripsi[position]
            )
            _arWayang.add(data)
        }

        val json = gson.toJson(_arWayang)
        editor.putString("spWayang", json)
        editor.apply()
    }

    fun TampilkanData() {
        _rvWayang.layoutManager= LinearLayoutManager(this)
        val adapterWayang = adapterRecView(_arWayang)
        _rvWayang.adapter = adapterWayang
        adapterWayang.setOnItemClickCallBack(object : adapterRecView.OnItemClickCallBack {
            override fun onItemClicked(data: wayang) {
//                Toast.makeText(this@MainActivity, data.nama, Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MainActivity, detWayang::class.java)
                intent.putExtra("kirimData", data)
                startActivity(intent)
            }
        })
        //Grid layout
//        _rvWayang.layoutManager= GridLayoutManager(this, 2)
        //Staggered layout
//        _rvWayang.layoutManager= StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
    }
}