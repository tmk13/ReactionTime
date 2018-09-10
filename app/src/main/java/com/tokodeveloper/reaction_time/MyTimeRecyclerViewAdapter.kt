package com.tokodeveloper.reaction_time


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tokodeveloper.reaction_time.HistoryFragment.OnListFragmentInteractionListener
import com.tokodeveloper.reaction_time.data.BestTime
import kotlinx.android.synthetic.main.fragment_time.view.*

class MyTimeRecyclerViewAdapter(
        private var mValues: List<BestTime>,
        private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<MyTimeRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as BestTime
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_time, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mIdView.text = item.date.toLocalDate().toString()
        holder.mContentView.text = item.bestTime.toString()

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    fun setData(bestTimes: List<BestTime>) {
        mValues = bestTimes
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.itemDate
        val mContentView: TextView = mView.time

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
