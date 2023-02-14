package com.example.moviesapp.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.moviesapp.model.Movie
import com.example.moviesapp.utils.Constants.POSTER_BASE_URL
import com.squareup.picasso.Picasso
import com.example.moviesapp.R


@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, movie: Movie) {
    val context = imageView.context
    if (movie.poster_path.isNotBlank()) {
        Picasso.get()
            .load(POSTER_BASE_URL + movie.poster_path)
            .placeholder(R.drawable.placeholder_picture)
            .error(R.drawable.no_img_avail)
            .fit()
            .centerCrop()
            .into(imageView)
    } else {
        imageView.setImageResource(R.drawable.no_img_avail)
        imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
    }
}

@BindingAdapter("fadeVisible")
fun setFadeVisible(view: View, visible: Boolean? = true) {
//    if (view.tag == null) {
//        view.tag = true
//        view.visibility = if (visible == true) View.VISIBLE else View.GONE
//    } else {
//        view.animate().cancel()
//        if (visible == true) {
//            if (view.visibility == View.GONE)
//                view.fadeIn()
//        } else {
//            if (view.visibility == View.VISIBLE)
//                view.fadeOut()
//        }
//    }
    if (visible == true)
        view.visibility = View.VISIBLE
    else
        view.visibility = View.GONE
}
