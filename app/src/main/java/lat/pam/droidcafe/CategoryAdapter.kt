package lat.pam.droidcafe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.core.content.ContextCompat

class CategoryAdapter(
    private val categories: List<String>,
    private val onCategoryClick: (String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    // 🔹 Simpan posisi kategori yang sedang dipilih
    private var selectedPosition = 0

    // 🔹 Fungsi supaya bisa diset dari luar (dipanggil dari MenuFragment)
    fun setSelectedCategory(category: String) {
        val newPosition = categories.indexOf(category)
        if (newPosition != -1) {
            val prevPos = selectedPosition
            selectedPosition = newPosition
            notifyItemChanged(prevPos)
            notifyItemChanged(selectedPosition)
        }
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardCategory: CardView = itemView.findViewById(R.id.cardCategory)
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.tvCategory.text = category

        val context = holder.itemView.context

        // 🔹 Ganti warna saat kategori aktif
        if (position == selectedPosition) {
            holder.cardCategory.setCardBackgroundColor(ContextCompat.getColor(context, R.color.coffee_light))
            holder.tvCategory.setTextColor(ContextCompat.getColor(context, R.color.white))
        } else {
            holder.cardCategory.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
            holder.tvCategory.setTextColor(ContextCompat.getColor(context, R.color.coffee_dark))
        }

        // 🔹 Klik kategori → update UI dan panggil callback
        holder.cardCategory.setOnClickListener {
            val prevPos = selectedPosition
            selectedPosition = position
            notifyItemChanged(prevPos)
            notifyItemChanged(selectedPosition)
            onCategoryClick(category)
        }
    }

    override fun getItemCount(): Int = categories.size
}
