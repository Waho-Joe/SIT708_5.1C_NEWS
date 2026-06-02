package com.example.a51c;

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

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private RecyclerView rvFeatured, rvLatestNews;
    private ArrayList<News> featuredList, latestNewsList, filteredFeaturedList, filteredLatestNewsList;
    private Button btnAll, btnFootball, btnBasketball, btnCricket, btnOpenBookmarks;
    private RecyclerViewAdapter featuredAdapter, latestAdapter;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvFeatured = view.findViewById(R.id.featuredRecyclerView);
        rvLatestNews = view.findViewById(R.id.latestRecyclerView);

        btnAll = view.findViewById(R.id.btnAll);
        btnFootball = view.findViewById(R.id.btnFootball);
        btnBasketball = view.findViewById(R.id.btnBasketball);
        btnCricket = view.findViewById(R.id.btnCricket);
        btnOpenBookmarks = view.findViewById(R.id.btnOpenBookmarks);

        createDummyData();
        setupRecyclerViews(view);
        setupFilterButtons(view);

        // Inflate the layout for this fragment
        return view;
    }
    private void createDummyData() {
        featuredList = new ArrayList<>();
        latestNewsList = new ArrayList<>();

        String imagePath = "android.resource://"
                + requireContext().getPackageName()
                + "/"
                + R.drawable.img;

        String[][] topStoriesData = {
                {"Top Story 1", imagePath, "This is a sports news description.", "Football"},
                {"Top Story 2", imagePath, "This is a sports news description.", "Basketball"},
                {"Top Story 3", imagePath, "This is a sports news description.", "Cricket"},
                {"Top Story 4", imagePath, "This is a sports news description.", "Football"},
                {"Top Story 5", imagePath, "This is a sports news description.", "Basketball"}
        };

        String[][] newsData = {
                {"Sports News 1", imagePath, "This is a sports news description.", "Football"},
                {"Sports News 2", imagePath, "This is a sports news description.", "Basketball"},
                {"Sports News 3", imagePath, "This is a sports news description.", "Cricket"},
                {"Sports News 4", imagePath, "This is a sports news description.", "Football"},
                {"Sports News 5", imagePath, "This is a sports news description.", "Basketball"},
                {"Sports News 6", imagePath, "This is a sports news description.", "Cricket"},
                {"Sports News 7", imagePath, "This is a sports news description.", "Football"},
                {"Sports News 8", imagePath, "This is a sports news description.", "Basketball"},
                {"Sports News 9", imagePath, "This is a sports news description.", "Cricket"},
                {"Sports News 10", imagePath, "This is a sports news description.", "Football"}
        };

        for (int i = 0; i < topStoriesData.length; i++) {
            featuredList.add(new News(
                    i + 1,
                    topStoriesData[i][0],
                    topStoriesData[i][1],
                    topStoriesData[i][2],
                    topStoriesData[i][3]
            ));
        }

        for (int i = 0; i < newsData.length; i++) {
            latestNewsList.add(new News(
                    i + 100,
                    newsData[i][0],
                    newsData[i][1],
                    newsData[i][2],
                    newsData[i][3]
            ));
        }

        filteredFeaturedList = new ArrayList<>(featuredList);
        filteredLatestNewsList = new ArrayList<>(latestNewsList);
    }

    private void setupRecyclerViews(View view) {
        rvFeatured.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        );

        featuredAdapter = new RecyclerViewAdapter(
                filteredFeaturedList,
                requireContext(),
                news -> {
                    openDetailPage(view, news);
                },
                R.layout.news_cardview
        );

        rvFeatured.setAdapter(featuredAdapter);

        rvLatestNews.setLayoutManager(
                new GridLayoutManager(requireContext(), 2)
        );

        latestAdapter = new RecyclerViewAdapter(
                filteredLatestNewsList,
                requireContext(),
                news -> {
                    openDetailPage(view, news);
                },
                R.layout.latest_news_cardview
        );

        rvLatestNews.setAdapter(latestAdapter);
    }

    private void setupFilterButtons(View view) {
        btnAll.setOnClickListener(v -> filterNews("All"));
        btnFootball.setOnClickListener(v -> filterNews("Football"));
        btnBasketball.setOnClickListener(v -> filterNews("Basketball"));
        btnCricket.setOnClickListener(v -> filterNews("Cricket"));
        btnOpenBookmarks.setOnClickListener(v -> {
            Navigation.findNavController(view)
                    .navigate(R.id.action_homeFragment_to_bookmarkFragment);
        });
    }

    private void filterNews(String category) {
        filteredFeaturedList.clear();
        filteredLatestNewsList.clear();

        if (category.equals("All")) {
            filteredFeaturedList.addAll(featuredList);
            filteredLatestNewsList.addAll(latestNewsList);
        } else {
            for (News news : featuredList) {
                if (news.getCategory().equals(category)) {
                    filteredFeaturedList.add(news);
                }
            }

            for (News news : latestNewsList) {
                if (news.getCategory().equals(category)) {
                    filteredLatestNewsList.add(news);
                }
            }
        }

        featuredAdapter.notifyDataSetChanged();
        latestAdapter.notifyDataSetChanged();
    }

    private void openDetailPage(View view, News news) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", news.getId());
        bundle.putString("title", news.getTitle());
        bundle.putString("imageUri", news.getImageUri());
        bundle.putString("description", news.getDescription());
        bundle.putString("category", news.getCategory());

        Navigation.findNavController(view)
                .navigate(R.id.action_homeFragment_to_detailFragment, bundle);
    }
}