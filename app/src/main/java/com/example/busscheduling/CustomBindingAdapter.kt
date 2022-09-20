package com.example.busscheduling

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:setFavourite")
fun setFavourite(imageView: ImageView,favourite:Boolean){
    when(favourite){
        true -> imageView.setImageResource(R.drawable.ic_baseline_favorite_24)
        false -> imageView.setImageResource(R.drawable.ic_baseline_favorite_24_gray)
    }

}