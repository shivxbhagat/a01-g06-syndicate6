package comp3350.smile.presentation;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import comp3350.smile.R;
import comp3350.smile.objects.Item;
import comp3350.smile.logic.ItemService;
import comp3350.smile.presentation.Adapters.BaseItemAdapter;

public class EditItemAdapter extends BaseItemAdapter {

    private final ItemService itemService;

    public EditItemAdapter(Context context, List<Item> items, int layoutResId) {
        super(context, items, layoutResId);
        itemService = new ItemService();
    }

    @Override
    protected void onItemClick(Item item) {
        Intent intent = new Intent(context, EditItemActivity.class);
        intent.putExtra("ITEM_ID", item.getItemId());
        context.startActivity(intent);

    }



    public void onBindViewHolder(ViewHolder holder, int position) {
        final Item item = items.get(position);



        // Handle the item click.
        holder.itemView.setOnClickListener(view -> onItemClick(item));
    }

    // Method to update the list of items and notify the adapter.
    public void updateList(List<Item> newList) {
        this.items = newList;
        notifyDataSetChanged();
    }

    // ViewHolder class to hold views for each item.
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bookmarkIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            bookmarkIcon = itemView.findViewById(R.id.bookmark_icon);
        }
    }
}
