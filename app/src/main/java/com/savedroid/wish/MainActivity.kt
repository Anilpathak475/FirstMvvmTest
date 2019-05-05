package com.savedroid.wish

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.savedroid.wish.database.entities.Wish
import com.savedroid.wish.network.errorhandler.WishErrorHandler
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_wish.view.*

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    private val wishAdapter by lazy { WishAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wishRecyclerView.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = wishAdapter
        }

        viewModel.exception.observe(this, Observer { errorHandler(it) })
        viewModel.wishes.observe(this, Observer { setData(it) })
        viewModel.loadWishes()
    }

    private fun setData(words: List<Wish>) {
        wishAdapter.wishes = words
        wishAdapter.notifyDataSetChanged()
    }

    private fun errorHandler(exception: Exception) {
        when (exception) {
            is WishErrorHandler.ErrorConfig.NetworkException -> {
                Toast.makeText(this@MainActivity, exception.message, Toast.LENGTH_LONG).show()
            }
            is WishErrorHandler.ErrorConfig.AlbumException -> {
                Toast.makeText(this@MainActivity, exception.message, Toast.LENGTH_LONG).show()
            }
            else -> Toast.makeText(this@MainActivity, "Oops! Something went wrong.", Toast.LENGTH_LONG).show()
        }
    }

    class WishAdapter :
        RecyclerView.Adapter<WishAdapter.WishViewHolder>() {
        var wishes: List<Wish> = mutableListOf()


        override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): WishViewHolder {
            val layoutInflater =
                viewGroup.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val convertView = layoutInflater.inflate(R.layout.item_wish, viewGroup, false)
            return WishViewHolder(convertView)
        }

        override fun onBindViewHolder(holder: WishViewHolder, position: Int) {
            holder.bind(wishes[position])
        }

        override fun getItemCount(): Int {
            return wishes.size
        }

        inner class WishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(wish: Wish) {
                itemView.wishName.text = wish.name
                itemView.userId.text = wish.userId
                itemView.targetBalance.text = wish.name
                itemView.targetDate.text = wish.targetDate
                itemView.wishType.text = wish.isGroupWish.toString()
            }
        }
    }
}
