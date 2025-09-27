package comp3350.smile.presentation;

import android.content.Context;
import android.os.Build;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.PopupMenu;
import java.util.function.Consumer;

public class CategoryPopupMenuBuilder {
    private final Context context;
    private final View anchorView;
    private int menuResource;
    private Consumer<MenuItem> categorySelectedListener;

    public CategoryPopupMenuBuilder(Context context, View anchorView) {
        this.context = context;
        this.anchorView = anchorView;
    }

    public CategoryPopupMenuBuilder setMenuResource(int menuResource) {
        this.menuResource = menuResource;
        return this;
    }

    public CategoryPopupMenuBuilder setCategorySelectedListener(Consumer<MenuItem> listener) {
        this.categorySelectedListener = listener;
        return this;
    }

    public void show() {
        final PopupMenu popupMenu = new PopupMenu(context, anchorView);
        popupMenu.getMenuInflater().inflate(menuResource, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            if (categorySelectedListener != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    categorySelectedListener.accept(item);
                }
            }
            return true;
        });

        popupMenu.show();
    }
}