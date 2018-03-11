package ie.app.william.hillforts.activities

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.card_hillforts.view.*
import ie.app.william.hillforts.R
import ie.app.william.hillforts.helpers.readImageFromPath
import ie.app.william.hillforts.models.HillfortModel


interface HillfortListener {
    fun onHillfortClick(hillforts: HillfortModel)
}

class HillfortAdapter constructor(private var hillforts: List<HillfortModel>,
                                   private val listener: HillfortListener) : RecyclerView.Adapter<HillfortAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_hillforts, parent, false))
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val hillfort = hillforts[holder.adapterPosition]
        holder.bind(hillfort, listener)
    }

    override fun getItemCount(): Int = hillforts.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(hillfort: HillfortModel,  listener : HillfortListener) {
            itemView.hillfortTitle.text = hillfort.title
            itemView.description.text = hillfort.description
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, hillfort.image))
            itemView.setOnClickListener { listener.onHillfortClick(hillfort) }
        }
    }


    /*interface HillfortListener {
        fun onHillfortClick(hillfort: HillfortModel)
        fun onHillfortLongClick(hillfort: HillfortModel)

    }

    itemView.setOnLongClickListener {
        listener.onHillfortLongClick(hillfort); true
    }

    override fun onHillfortLongClick(hillfort: HillfortModel) {
        app.hillforts.delete(hillfort)
        loadHillforts()
    }

    override fun onHillfortLongClick(hillfort: HillfortModel) {
        val title = ctx.getString(R.string.dialog_title_delete)
        val message = ctx.getString(R.string.dialog_desc_delete)

        alert(message, title) {
            positiveButton(ctx.getString(android.R.string.ok)) {
                app.hillforts.delete(hillfort)
                loadHillforts()
            }
            negativeButton(ctx.getString(android.R.string.no)) { }
        }.show()
    }
    pushed to git*/
}