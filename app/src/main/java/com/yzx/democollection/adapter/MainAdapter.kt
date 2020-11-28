package com.yzx.democollection.adapter

import android.widget.Button
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yzx.democollection.R

class MainAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_main) {

    init {
        addChildClickViewIds(R.id.tvTitle)
    }

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.getView<Button>(R.id.tvTitle).text = item
    }
}