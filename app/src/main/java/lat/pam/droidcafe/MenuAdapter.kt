package lat.pam.droidcafe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import lat.pam.droidcafe.databinding.ItemMenuBinding

class MenuAdapter(
    private val menuList: List<MenuItem>,
    private val onItemClick: ((MenuItem) -> Unit)? = null // 🔹 listener opsional
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    inner class MenuViewHolder(val binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = menuList[position]
        holder.binding.ivMenu.setImageResource(item.imageRes)
        holder.binding.tvMenuName.text = item.name

        // 🔹 Klik item (kalau listener dikasih)
        holder.binding.root.setOnClickListener {
            onItemClick?.invoke(item)
        }
    }

    override fun getItemCount(): Int = menuList.size
}
