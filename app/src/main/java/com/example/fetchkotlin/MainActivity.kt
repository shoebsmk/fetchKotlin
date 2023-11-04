package com.example.fetchkotlin

import android.content.DialogInterface
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fetchkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var binding: ActivityMainBinding? = null

    //List
    private val itemArrayList = ArrayList<Item>()

    //RecyclerView Stuff
    var itemsAdapter: ItemsAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        if (hasNetworkConnection()) {
            Log.d(TAG, "onCreate: Connected!")
            itemArrayList.clear()

            // Set up recyclerView
            itemsAdapter = ItemsAdapter(itemArrayList, this)
            binding!!.recyclerView.layoutManager = LinearLayoutManager(this)
            binding!!.recyclerView.adapter = itemsAdapter

            // API call to get data, callback function is AcceptItems
            ItemsDownloader.downloadItems(this)
        } else {
            // No Network
            notConnectedDialog()
        }
    }

    private fun notConnectedDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton("OK") { dialog: DialogInterface?, id: Int -> }
        builder.setMessage("Data cannot be fetched without a network connection.")
        builder.setTitle("No Network Connection")
        val dialog = builder.create()
        dialog.show()
    }

    private fun hasNetworkConnection(): Boolean {
        val connectivityManager = getSystemService(ConnectivityManager::class.java)
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    fun acceptItems(_itemArrayList: ArrayList<Item>) {

        // Check if data has be received properly
        if (_itemArrayList.size == 0) {
            val builder = AlertDialog.Builder(this)
            builder.setPositiveButton("OK") { dialog: DialogInterface?, id: Int -> }
            builder.setMessage("Data could not be parsed properly.")
            builder.setTitle("Invalid Data received from API")
            val dialog = builder.create()
            dialog.show()
        }
        // Null and blanks are already filtered

        // Sort by List Id then Item Id
        _itemArrayList.sortWith<Item>(java.util.Comparator<Item> { o1: Item, o2: Item ->
            val listId1 = o1.listId.toInt()
            val listId2 = o2.listId.toInt()
            val i = Integer.compare(listId1, listId2)
            if (i == 0) {

                //If we want to use item Id instead of name
                /*
                int id1 = Integer.parseInt(o1.getId());
                int id2 = Integer.parseInt(o2.getId());
                return  Integer.compare(id1, id2);
                */

                //String manipulation
                var s1 = o1.name
                var s2 = o2.name
                s1 = s1!!.substring(s1.lastIndexOf(" ") + 1)
                s2 = s2!!.substring(s2.lastIndexOf(" ") + 1)
                val name1 = s1.toInt()
                val name2 = s2.toInt()
                return@Comparator Integer.compare(name1, name2)
            }
            i
        })
        itemArrayList.addAll(_itemArrayList)
        itemsAdapter!!.notifyItemRangeChanged(0, itemArrayList.size)
    }

    override fun onClick(v: View) {
        val pos = binding!!.recyclerView.getChildLayoutPosition(v)
        if (itemArrayList.size == 0) {
            return
        }
        Toast.makeText(this, itemArrayList[pos].name, Toast.LENGTH_SHORT).show()
    }

    companion object {
        // Tag for Debugging
        private const val TAG = "MainActivity"
    }
}