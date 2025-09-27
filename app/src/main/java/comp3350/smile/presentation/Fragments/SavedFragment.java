package comp3350.smile.presentation.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView; // Import TextView

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import comp3350.smile.R;
import comp3350.smile.objects.Item;
import comp3350.smile.logic.ItemService;
import comp3350.smile.presentation.Adapters.ItemAdapter;

public class SavedFragment extends Fragment implements SearchableFragment {

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private List<Item> savedItems = new ArrayList<>();
    private List<Item> filteredItems = new ArrayList<>();
    private ItemService itemService;
    private String query = "";

    private TextView noResultsText;

    public SavedFragment() {
    }

    public static SavedFragment newInstance(String query) {
        SavedFragment fragment = new SavedFragment();
        Bundle args = new Bundle();
        args.putString("query", query);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            query = getArguments().getString("query", "");
        }
        itemService = new ItemService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved, container, false);


        recyclerView = view.findViewById(R.id.recyclerViewSaved);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));


        noResultsText = view.findViewById(R.id.noResultsText);


        savedItems = itemService.getSavedItems();
        Log.d("SavedFragment", "Saved items: " + savedItems);


        if (savedItems.isEmpty()) {
            noResultsText.setVisibility(View.VISIBLE);
        } else {
            noResultsText.setVisibility(View.GONE);
        }

        filteredItems.clear();
        filteredItems.addAll(savedItems);


        itemAdapter = new ItemAdapter(getContext(), filteredItems, R.layout.item_card);
        recyclerView.setAdapter(itemAdapter);


        applySearchQuery(query);

        return view;
    }

    @Override
    public void applySearchQuery(String query) {
        filteredItems.clear();
        if (query == null || query.trim().isEmpty()) {
            filteredItems.addAll(savedItems);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (Item item : savedItems) {
                if (item.getName().toLowerCase().contains(lowerCaseQuery)
                        || item.getCategory().toLowerCase().contains(lowerCaseQuery)) {
                    filteredItems.add(item);
                }
            }
        }
        if (itemAdapter != null) {
            itemAdapter.notifyDataSetChanged();
        }


        if (filteredItems.isEmpty()) {
            noResultsText.setVisibility(View.VISIBLE);
        } else {
            noResultsText.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshSavedItems();
    }

    private void refreshSavedItems() {
        savedItems.clear();
        List<Item> updatedItems = itemService.getSavedItems();
        if (updatedItems != null) {
            savedItems.addAll(updatedItems);
        }

        applySearchQuery(query);

        if (itemAdapter != null) {
            itemAdapter.notifyDataSetChanged();
        }
    }
}
