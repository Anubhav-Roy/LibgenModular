package com.roy.anubhav.libgennavigation

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roy.anubhav.libgennavigation.adapters.BookAdapter


class search_results : Fragment() {

    val args: search_resultsArgs by navArgs()

    private lateinit var viewModel: SearchResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SearchResultViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var search_box  = view.findViewById<EditText>(R.id.search_box)
        val query = args.query

        search_box?.setText(query)

        viewModel.performQuery(query)

        setupRecyclerView()

    }

    private fun setupRecyclerView(){
        val recyclerView = view!!.findViewById<RecyclerView>(R.id.recycler_view)

        //Setup the Adapter
        val bookAdapter = BookAdapter(context!!, object : (String,String) -> Unit {
            override fun invoke(url: String, title: String) {

//                if(!checkIfAppHasWritePermission())
//                    NetworkUtils.downloadURL(applicationContext, url, title)
//                else
//                    ActivityCompat.requestPermissions(this@MainActivity,
//                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
//                        1)

            }
        })

        //Setup the recyclerview with the adapter
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = bookAdapter

        //Listen for the paged data being received
        viewModel.booksList.observe(this, Observer {
            bookAdapter.submitList(it)
        })
    }

}
