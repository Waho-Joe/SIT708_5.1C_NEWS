package com.example.a51c;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class DetailFragment extends Fragment {


    private ImageView detailImage;
    private TextView detailTitle, detailDescription;
    private RecyclerView relatedRecyclerView;
    private ArrayList<News> relatedNewsList;
    private Button btnBackHome, btnBookmark;
    private News currentNews;


    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        detailImage = view.findViewById(R.id.detailImage);
        detailTitle = view.findViewById(R.id.detailTitle);
        detailDescription = view.findViewById(R.id.detailDescription);
        relatedRecyclerView = view.findViewById(R.id.relatedRecyclerView);
        btnBookmark = view.findViewById(R.id.btnBookmark);
        btnBackHome = view.findViewById(R.id.btnBackHome);

        Bundle bundle = getArguments();

        int currentId = 0;
        String title = "";
        String imageUri = "";
        String description = "";
        String category = "";

        if (bundle != null) {
            currentId = bundle.getInt("id");
            title = bundle.getString("title");
            imageUri = bundle.getString("imageUri");
            description = bundle.getString("description");
            category = bundle.getString("category");
        }

        currentNews = new News(currentId, title, imageUri, description, category);

        detailTitle.setText(title);
        detailDescription.setText(description);
        detailImage.setImageURI(Uri.parse(imageUri));

        createRelatedNews(currentId, imageUri);

        if (isBookmarked(currentNews.getId())) {
            btnBookmark.setText("Bookmarked");
        } else {
            btnBookmark.setText("Bookmark");
        }

        btnBookmark.setOnClickListener(v -> {
            if (isBookmarked(currentNews.getId())) {
                removeBookmark(currentNews.getId());
                btnBookmark.setText("Bookmark");
                Toast.makeText(requireContext(), "Bookmark removed", Toast.LENGTH_SHORT).show();
            } else {
                saveBookmark(currentNews);
                btnBookmark.setText("Bookmarked");
                Toast.makeText(requireContext(), "Bookmarked", Toast.LENGTH_SHORT).show();
            }
        });

        relatedRecyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)        );

        relatedRecyclerView.setAdapter(
                new RecyclerViewAdapter(
                        relatedNewsList,
                        requireContext(),
                        news -> {
                        Bundle relatedBundle = new Bundle();
                        relatedBundle.putInt("id", news.getId());
                        relatedBundle.putString("title", news.getTitle());
                        relatedBundle.putString("imageUri", news.getImageUri());
                        relatedBundle.putString("description", news.getDescription());

                        Navigation.findNavController(view)
                                .navigate(R.id.action_detailFragment_to_detailFragment, relatedBundle);
                        },
                        R.layout.relate_news_cardview
                )
        );
        btnBackHome.setOnClickListener(v -> {
            Navigation.findNavController(view)
                    .navigate(R.id.homeFragment);
        });

        return view;
    }
    private boolean isBookmarked(int newsId) {
        SharedPreferences sharedPreferences =
                requireContext().getSharedPreferences("bookmarks", Context.MODE_PRIVATE);

        String existingData = sharedPreferences.getString("bookmark_items", "");

        return existingData.contains(newsId + "||");
    }
    private void removeBookmark(int newsId) {
        SharedPreferences sharedPreferences =
                requireContext().getSharedPreferences("bookmarks", Context.MODE_PRIVATE);

        String existingData = sharedPreferences.getString("bookmark_items", "");

        if (existingData.isEmpty()) {
            return;
        }

        String[] items = existingData.split("##");
        String newData = "";

        for (String item : items) {
            if (!item.startsWith(newsId + "||")) {
                if (newData.isEmpty()) {
                    newData = item;
                } else {
                    newData = newData + "##" + item;
                }
            }
        }

        sharedPreferences.edit()
                .putString("bookmark_items", newData)
                .apply();
    }
    private void saveBookmark(News news) {
        SharedPreferences sharedPreferences =
                requireContext().getSharedPreferences("bookmarks", Context.MODE_PRIVATE);

        String existingData = sharedPreferences.getString("bookmark_items", "");

        String item = news.getId() + "||"
                + news.getTitle() + "||"
                + news.getImageUri() + "||"
                + news.getDescription() + "||"
                + news.getCategory();

        if (!existingData.contains(news.getId() + "||")) {
            String newData;

            if (existingData.isEmpty()) {
                newData = item;
            } else {
                newData = existingData + "##" + item;
            }

            sharedPreferences.edit()
                    .putString("bookmark_items", newData)
                    .apply();

            Toast.makeText(requireContext(), "Bookmarked", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "Already bookmarked", Toast.LENGTH_SHORT).show();
        }
    }
    private void createRelatedNews(int currentId, String imageUri) {
        relatedNewsList = new ArrayList<>();

        String[][] relatedData = {
                {"Related News 1", imageUri, "This is a sports news description.","Football"},
                {"Related News 2", imageUri, "This is a sports news description.","Football"},
                {"Related News 3", imageUri, "This is a sports news description.","Football"},
                {"Related News 4", imageUri, "This is a sports news description.","Football"},
                {"Related News 5", imageUri, "This is a sports news description.","Football"},
                {"Related News 6", imageUri, "This is a sports news description.","Football"}
        };

        for (int i = 0; i < relatedData.length; i++) {
            relatedNewsList.add(new News(
                    i + 200,
                    relatedData[i][0],
                    relatedData[i][1],
                    relatedData[i][2],
                    relatedData[i][3]
            ));
        }
    }
}