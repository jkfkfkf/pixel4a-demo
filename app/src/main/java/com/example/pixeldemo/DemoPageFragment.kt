package com.example.pixeldemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class DemoPageFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_demo_page, container, false)
        val image = view.findViewById<ImageView>(R.id.bgImage)
        val title = view.findViewById<TextView>(R.id.titleText)
        val desc = view.findViewById<TextView>(R.id.descText)

        val args = requireArguments()
        val imageRes = args.getInt("imageRes")
        val titleText = args.getString("title")
        val descText = args.getString("desc")

        image.setImageResource(imageRes)
        title.text = titleText
        desc.text = descText

        return view
    }
}
