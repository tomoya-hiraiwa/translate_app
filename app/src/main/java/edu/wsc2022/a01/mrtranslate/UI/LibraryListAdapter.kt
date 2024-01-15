package edu.wsc2022.a01.mrtranslate.UI

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.wsc2022.a01.mrtranslate.databinding.LibraryListItemBinding

class LibraryListAdapter(private val dataList: MutableList<LibraryData>): RecyclerView.Adapter<LibraryListAdapter.LibraryListViewHolder>() {

    inner class LibraryListViewHolder(private val binding: LibraryListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bindData(data: LibraryData){
            binding.fromText.text = data.fromText
            binding.toText.text = data.toText
            binding.fromLg.text = "from: ${data.toLg}"
            binding.toLg.text = "to: ${data.fromLg}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryListViewHolder {
        return LibraryListViewHolder(LibraryListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: LibraryListViewHolder, position: Int) {
        val data = dataList[position]
        holder.bindData(data)
    }
}