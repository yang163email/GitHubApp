package com.yan.github.utils

import cn.carbs.android.avatarimageview.library.AvatarImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 *  @author : yan
 *  @date   : 2018/6/20 14:42
 *  @desc   : glide扩展
 */
fun AvatarImageView.loadWithGlide(url: String, textPlaceholder: Char, requestOptions: RequestOptions = RequestOptions()) {
    textPlaceholder.toString().let {
        setTextAndColorSeed(it.toUpperCase(), it.hashCode().toString())
    }

    Glide.with(this)
            .load(url)
            .apply(requestOptions)
            .into(this)
}