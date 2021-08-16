package com.example.baseux.rigger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.baseux.rigger.ActivityRigger
import com.example.baseux.components.AnnotationParse

open abstract class FragmentRigger: Fragment(){

    val binding by lazy {
        val viewDataBinding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(this.context), AnnotationParse.getAnnotatedLayout(this), null, false)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = this.binding.root

    fun transFragment(id: Int, bundle: Bundle? = null){ findNavController(this).navigate(id, bundle) }
    fun transFragment(node: NavDirections){ findNavController(this).navigate(node) }
    fun transActivity(id: Int, args: Bundle? = null){ (this.context as ActivityRigger).transActivity(id, args)}
    fun popBackFragment(){ findNavController(this).popBackStack() }
}