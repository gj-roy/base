package vn.loitp.a.demo.architectureComponent.room

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.core.ext.printBeautyJson
import kotlinx.android.synthetic.main.i_room_note.view.*
import vn.loitp.R
import vn.loitp.a.demo.architectureComponent.room.md.Word

class WordListAdapter(val callback: Callback?) :
    RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {

    interface Callback {
        fun onDelete(word: Word)
        fun onUpdate(word: Word)
    }

    private var words = emptyList<Word>()

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(word: Word) {
            itemView.tvNote.printBeautyJson(o = word)

            itemView.ivDelete.setOnClickListener {
                callback?.onDelete(word = word)
            }
            itemView.cardView.setOnClickListener {
                callback?.onUpdate(word = word)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WordViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.i_room_note, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: WordViewHolder,
        position: Int
    ) {
        holder.bind(words[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setWords(words: List<Word>) {
        this.words = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = words.size
}
