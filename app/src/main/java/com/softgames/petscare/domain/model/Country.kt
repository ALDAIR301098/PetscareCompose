package com.softgames.petscare.domain.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    val name: String,
    val code: String,
    @DrawableRes val flag: Int,
) : Parcelable
