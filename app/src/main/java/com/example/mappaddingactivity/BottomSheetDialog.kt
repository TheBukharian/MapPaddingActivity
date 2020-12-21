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
import com.uniquestudio.library.CircleCheckBox
import java.util.*

class BottomSheetDialog:Bottom() {

    val baseContext = context?.applicationContext

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setStyle(DialogFragment.STYLE_NORMAL, R.style.ThemeOverlay_Demo_BottomSheetDialog)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.language_bottom_sheet, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rad1 = view.findViewById<CircleCheckBox>(R.id.ratio1)
        val rad2 = view.findViewById<CircleCheckBox>(R.id.ratio2)
        val rad3 = view.findViewById<CircleCheckBox>(R.id.ratio3)





        rad1.setListener(CircleCheckBox.OnCheckedChangeListener {
            rad1.isChecked = true
            rad2.isChecked = false
            rad3.isChecked = false

            if (rad1.isChecked){
                setLocate("en")

            }
        })
        rad2.setListener(CircleCheckBox.OnCheckedChangeListener {
            rad1.isChecked = false
            rad2.isChecked = true
            rad3.isChecked = false
            if (rad2.isChecked){
                setLocate("ru")


            }
        })
        rad3.setListener(CircleCheckBox.OnCheckedChangeListener {
            rad1.isChecked = false
            rad2.isChecked = false
            rad3.isChecked = true
            if (rad3.isChecked){
                setLocate("uz")


            }
        })
    }

//    override fun onDismiss(dialog: DialogInterface) {
//        super.onDismiss(dialog)
//        mListener?.onItemClick()
//
//    }





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
            throw RuntimeException("$context must implement ItemClickListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface ItemClickListener {
        fun onItemClick(){}
    }
    fun setLocate(Lang: String) {

        val locale = Locale(Lang)
        Locale.setDefault(locale)
        val config = Configuration()

        config.locale = locale
        baseContext?.resources?.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor: SharedPreferences.Editor? = activity?.getSharedPreferences("Settings", Context.MODE_PRIVATE)?.edit()
        editor?.putString("My_Lang", Lang)
        editor?.apply()
    }

}
