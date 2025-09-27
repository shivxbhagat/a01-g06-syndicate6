package comp3350.smile.presentation.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import comp3350.smile.R;
import comp3350.smile.objects.Item;

public abstract class BaseItemAdapter extends RecyclerView.Adapter<BaseItemAdapter.BaseItemViewHolder> {
    protected List<Item> items;
    protected Context context;
    protected int layoutResId; // To handle different layouts

    // Constructor with layout resource ID and itemService
    public BaseItemAdapter(Context context, List<Item> items, int layoutResId) {
        this.context = context;
        this.items = items;
        this.layoutResId = layoutResId;
    }

    @NonNull
    @Override
    public BaseItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(layoutResId, parent, false);
        return new BaseItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseItemViewHolder holder, int position) {
        Item item = items.get(position);

        holder.itemName.setText(item.getName());
        holder.itemPrice.setText(String.format("$%.2f", item.getPrice()));

        // Set image for the item (ensure image exists in the resources)
        String imagePath = item.getImgPath();

        if (imagePath.contains(context.getString(R.string.image_directory))) {
            // Handle camera/gallery images
            File imageFile = new File(context.getFilesDir(), imagePath);
            if (imageFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                holder.itemImage.setImageBitmap(bitmap);
            } else {
                holder.itemImage.setImageResource(R.drawable.default_image);
            }
        } else {
            // Handle drawable resources
            int resID = context.getResources().getIdentifier(
                    imagePath,
                    "drawable",
                    context.getPackageName()
            );
            holder.itemImage.setImageResource(resID != 0 ? resID : R.drawable.default_image);
        }

        // Set the bookmark icon based on whether the item is saved
        boolean isSaved = item.isSaved();
        if (isSaved) {
            holder.bookmarkIcon.setImageResource(R.drawable.bookmark_filled);
        } else {
            holder.bookmarkIcon.setImageResource(R.drawable.bookmark);
        }

        // Handle item click (you can add more logic here, like opening a detailed page)
        holder.itemView.setOnClickListener(v -> {
            onItemClick(item);
        });

        // Handle bookmark icon click (toggle saved state)
        holder.bookmarkIcon.setOnClickListener(view -> {
            onItemBookmarkClick(view, item);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // Abstract method to be implemented in subclasses
    protected abstract void onItemClick(Item item);

    // Toggle the saved state of the item and update the bookmark icon
    protected void onItemBookmarkClick(View view, Item item) {
        ImageView bookmarkIcon = (ImageView) view;
        boolean isSaved = item.isSaved();

        if (isSaved) {
            item.setSaved(false);
            bookmarkIcon.setImageResource(R.drawable.bookmark);
        } else {
            item.setSaved(true);
            bookmarkIcon.setImageResource(R.drawable.bookmark_filled);
        }
    }

    // ViewHolder class to hold the item views
    public static class BaseItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemPrice;
        ImageView itemImage, bookmarkIcon;

        public BaseItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.item_price);
            itemImage = itemView.findViewById(R.id.item_photo);
            bookmarkIcon = itemView.findViewById(R.id.bookmark_icon);
        }
    }

}
