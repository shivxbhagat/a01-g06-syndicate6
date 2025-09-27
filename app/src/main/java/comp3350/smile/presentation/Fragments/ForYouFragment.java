package comp3350.smile.presentation.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import comp3350.smile.R;
import comp3350.smile.objects.Item;
import comp3350.smile.logic.ItemService;
import comp3350.smile.presentation.Adapters.ItemAdapter;

public class ForYouFragment extends Fragment implements SearchableFragment {

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private List<Item> allItems = new ArrayList<>();
    private List<Item> filteredItems = new ArrayList<>();
    private String query = "";
    private TextView noResultsText;

    // Required empty public constructor
    public ForYouFragment() {
    }

    // Factory method to create a new instance with a search query
    public static ForYouFragment newInstance(String query) {
        ForYouFragment fragment = new ForYouFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_for_you, container, false);

        // Initialize RecyclerView with a grid layout.
        recyclerView = view.findViewById(R.id.recyclerViewForYou);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Initialize noResultsText using the inflated view.
        noResultsText = view.findViewById(R.id.noResultsTextTY);

        // Initialize ItemService using the current context.
        ItemService itemService = new ItemService();
        allItems = itemService.getRandomItems(); // Fetch random items
        Log.d("ForYouFragment", "All Items fetched: " + (allItems != null ? allItems.size() : "null"));

        // Initialize filteredItems list
        if (allItems != null && !allItems.isEmpty()) {
            filteredItems.addAll(allItems);
        } else {
            Log.d("ForYouFragment", "No items available.");
        }

        // Initialize adapter and bind data
        itemAdapter = new ItemAdapter(getActivity(), filteredItems, R.layout.item_card);
        recyclerView.setAdapter(itemAdapter);

        // Apply the search query (if any)
        applySearchQuery(query);

        return view;
    }

    @Override
    public void applySearchQuery(String query) {
        filteredItems.clear();
        if (query == null || query.trim().isEmpty()) {
            filteredItems.addAll(allItems);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (Item item : allItems) {
                if (item.getName().toLowerCase().contains(lowerCaseQuery) ||
                        item.getCategory().toLowerCase().contains(lowerCaseQuery)) {
                    filteredItems.add(item);
                }
            }
        }
        if (itemAdapter != null) {
            itemAdapter.notifyDataSetChanged();
        }

        // Show "No results found" if filtered list is empty
        if (filteredItems.isEmpty()) {
            noResultsText.setVisibility(View.VISIBLE);

        } else {
            noResultsText.setVisibility(View.GONE);
        }
    }
}
