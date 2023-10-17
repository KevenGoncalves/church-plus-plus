package com.example.churchplusplus.Adapter.Listeners

import android.view.View
import com.example.churchplusplus.Model.Contribution

interface ContributionClickListener {
    fun onClick(contribuicao: Contribution, view:View)
}