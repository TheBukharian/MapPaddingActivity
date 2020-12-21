package com.example.mappaddingactivity

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class BottomSheetDialog:BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.ThemeOverlay_Demo_BottomSheetDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.language_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rad1 = view.findViewById<RadioButton>(R.id.ratio1)
        val rad2 = view.findViewById<RadioButton>(R.id.ratio2)
        val rad3 = view.findViewById<RadioButton>(R.id.ratio3)




        rad2.setOnCheckedChangeListener { _, _ ->
            if (rad2.isChecked){
                val sharedPreferences = activity?.getSharedPreferences("Settings", Activity.MODE_PRIVATE)
                val language = sharedPreferences?.getString("My_Lang", "")
                setLocate(language!!)
                // Here i need to update activity_language layout, but i don`t know how:



                //dismiss bottom sheet dialog
                dismiss()

            }

        }
    }





    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): BottomSheetDialog {
            val fragment = BottomSheetDialog()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var mListener: ItemClickListener? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ItemClickListener) {
            mListener = context as ItemClickListener
        } else {
            throw RuntimeException(context.toString() + " must implement ItemClickListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface ItemClickListener {
        fun onItemClick(view: View){

        }
    }
    fun setLocate(Lang: String) {

        val locale = Locale(Lang)

        Locale.setDefault(locale)

        val config = Configuration()

        val baseContext = context?.applicationContext
        config.locale = locale
        baseContext?.resources?.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor: SharedPreferences.Editor? = activity?.getSharedPreferences("Settings", Context.MODE_PRIVATE)?.edit()
        editor?.putString("My_Lang", Lang)
        editor?.apply()
    }

}
