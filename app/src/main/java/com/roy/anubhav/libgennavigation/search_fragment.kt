package com.roy.anubhav.libgennavigation

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.Navigation


class search_fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        view!!.findViewById<Button>(R.id.search_button).setOnClickListener{
            val action = search_fragmentDirections.actionSearchFragmentToSearchResults(view!!.findViewById<EditText>(R.id.search_box).text.toString())
           val  navController = Navigation.findNavController(it)
            navController.navigate(action)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            search_fragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
