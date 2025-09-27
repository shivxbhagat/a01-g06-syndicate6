package comp3350.smile.presentation.Adapters;

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
import comp3350.smile.presentation.Activities.ItemActivity;

public class ItemAdapter extends BaseItemAdapter {

    private final ItemService itemService;

    public ItemAdapter(Context context, List<Item> items, int layoutResId) {
        super(context, items, layoutResId);
        itemService = new ItemService();
    }

    @Override
    protected void onItemClick(Item item) {
        Intent intent = new Intent(context, ItemActivity.class);
        intent.putExtra("ITEM_ID", item.getItemId());
        context.startActivity(intent);
    }

    // This method handles the bookmark icon click, toggling the save state.
    protected void onItemBookmarkClick(View view, Item item) {
        ImageView bookmarkIcon = (ImageView) view;
        boolean isSaved = itemService.isItemSaved(item);

        if (isSaved) {
            itemService.removeItemFromSaved(item);
            bookmarkIcon.setImageResource(R.drawable.bookmark); // Change to unfilled bookmark icon
            Toast.makeText(context, "Item removed from saved", Toast.LENGTH_SHORT).show();
        } else {
            itemService.addItemToSaved(item);
            bookmarkIcon.setImageResource(R.drawable.bookmark_filled); // Change to filled bookmark icon
            Toast.makeText(context, "Item saved", Toast.LENGTH_SHORT).show();
        }
    }


    public void onBindViewHolder(ViewHolder holder, int position) {
        final Item item = items.get(position);
        final ImageView bookmarkIcon = holder.bookmarkIcon;

        // Set the correct bookmark icon based on the saved state.
        boolean isSaved = itemService.isItemSaved(item);
        if (isSaved) {
            bookmarkIcon.setImageResource(R.drawable.bookmark_filled);
        } else {
            bookmarkIcon.setImageResource(R.drawable.bookmark);
        }

        // Set the bookmark click listener.
        bookmarkIcon.setOnClickListener(view -> onItemBookmarkClick(view, item));

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
