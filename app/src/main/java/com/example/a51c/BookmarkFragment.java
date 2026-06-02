package com.example.a51c;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;


public class BookmarkFragment extends Fragment {

    private RecyclerView bookmarkRecyclerView;
    private ArrayList<News> bookmarkList;
    private Button btnBackHome;


    public BookmarkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);

        bookmarkRecyclerView = view.findViewById(R.id.bookmarkRecyclerView);
        btnBackHome = view.findViewById(R.id.btnBackHome);

        btnBackHome.setOnClickListener(v -> {
            Navigation.findNavController(view)
                    .navigate(R.id.homeFragment);
        });


        bookmarkList = getBookmarks();

        bookmarkRecyclerView.setLayoutManager(
                new GridLayoutManager(requireContext(), 2)
        );

        bookmarkRecyclerView.setAdapter(
                new RecyclerViewAdapter(
                        bookmarkList,
                        requireContext(),
                        news -> {
                            Bundle bundle = new Bundle();
                            bundle.putInt("id", news.getId());
                            bundle.putString("title", news.getTitle());
                            bundle.putString("imageUri", news.getImageUri());
                            bundle.putString("description", news.getDescription());
                            bundle.putString("category", news.getCategory());

                            Navigation.findNavController(view)
                                    .navigate(R.id.action_bookmarkFragment_to_detailFragment, bundle);
                        },
                        R.layout.relate_news_cardview
                )
        );
        return view;
    }
    private ArrayList<News> getBookmarks() {
        ArrayList<News> list = new ArrayList<>();

        SharedPreferences sharedPreferences =
                requireContext().getSharedPreferences("bookmarks", Context.MODE_PRIVATE);

        String savedData = sharedPreferences.getString("bookmark_items", "");

        if (savedData.isEmpty()) {
            return list;
        }

        String[] items = savedData.split("##");

        for (String item : items) {
            String[] parts = item.split("\\|\\|");

            if (parts.length == 5) {
                int id = Integer.parseInt(parts[0]);
                String title = parts[1];
                String imageUri = parts[2];
                String description = parts[3];
                String category = parts[4];

                list.add(new News(id, title, imageUri, description, category));
            }
        }

        return list;
    }
}