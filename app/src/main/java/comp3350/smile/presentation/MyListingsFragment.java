package comp3350.smile.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import comp3350.smile.R;
import comp3350.smile.objects.Item;
import comp3350.smile.logic.ItemService;
import comp3350.smile.presentation.Fragments.SearchableFragment;

public class MyListingsFragment extends Fragment implements SearchableFragment {

    private RecyclerView recyclerView;
    private EditItemAdapter editItemAdapter;
    private List<Item> myListedItems = new ArrayList<>();
    private List<Item> filteredItems = new ArrayList<>();
    private ItemService itemService;
    private String query = "";
    private TextView noResultsText;

    public MyListingsFragment() {
    }

    public static MyListingsFragment newInstance(String query) {
        MyListingsFragment fragment = new MyListingsFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lists, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewLists);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        noResultsText = view.findViewById(R.id.noResultsText);

        loadMyListedItems();

        // Initialize adapter only if myListedItems are available
        editItemAdapter = new EditItemAdapter(requireActivity(), filteredItems, R.layout.item_card);
        recyclerView.setAdapter(editItemAdapter);

        applySearchQuery(query);

        return view;
    }

    private void loadMyListedItems() {
        myListedItems = itemService.getListedItems();
        if (myListedItems == null) {
            myListedItems = new ArrayList<>();
        }
        Log.d("MyListingsFragment", "User's Listed Items: " + myListedItems.size());

        filteredItems.clear();
        filteredItems.addAll(myListedItems);

        updateNoResultsText();
    }

    @Override
    public void applySearchQuery(String query) {
        if (query == null) query = "";
        filteredItems.clear();

        if (query.trim().isEmpty()) {
            filteredItems.addAll(myListedItems);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (Item item : myListedItems) {
                if (item.getName().toLowerCase().contains(lowerCaseQuery) ||
                        item.getCategory().toLowerCase().contains(lowerCaseQuery)) {
                    filteredItems.add(item);
                }
            }
        }

        if (editItemAdapter != null) {
            editItemAdapter.updateList(filteredItems);
        }

        updateNoResultsText();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshListedItems();
    }

    private void refreshListedItems() {
        loadMyListedItems();

        if (editItemAdapter != null) {
            editItemAdapter.updateList(filteredItems);
        }
    }

    private void updateNoResultsText() {
        if (filteredItems.isEmpty()) {
            noResultsText.setVisibility(View.VISIBLE);
        } else {
            noResultsText.setVisibility(View.GONE);
        }
    }

    private void onItemEdited(Item item) {
        refreshListedItems();
    }

    private void onItemDeleted(Item item) {
        itemService.deleteItem(item);
        refreshListedItems();
    }
}
