package comp3350.smile.presentation.Adapters;

import android.content.Context;
import android.content.Intent;


import java.util.List;

import comp3350.smile.objects.Item;
import comp3350.smile.presentation.Activities.ItemActivity;

public class RelatedItemsAdapter extends BaseItemAdapter {

    public RelatedItemsAdapter(Context context, List<Item> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    protected void onItemClick(Item item) {
        Intent intent = new Intent(context, ItemActivity.class);
        intent.putExtra("ITEM_ID", item.getItemId());
        context.startActivity(intent);
    }
}
