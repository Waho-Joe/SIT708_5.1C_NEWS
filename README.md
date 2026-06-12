# SIT708_5.1C_NEWS

## Overview

This project is an Android Sports News App developed for SIT708 Task 5.1C. The app allows users to browse sports news, view detailed news content, check related news, and save news articles as bookmarks.

The application uses fragments and Jetpack Navigation Component to manage different screens. RecyclerView is used to display news cards in different sections, including home news, latest news, related news, and bookmarked news.

## Features

- Sports news home page
- Top or latest news display
- News detail page
- Related news section
- Bookmark news function
- Bookmark page
- RecyclerView news list
- Card-based news layout
- Fragment-based navigation
- Image display for news articles
- Simple and clear sports news user interface

## Project Structure

```text
app/src/main/java/com.example.a51c/
├── MainActivity.java
├── HomeFragment.java
├── DetailFragment.java
├── BookmarkFragment.java
├── News.java
└── RecyclerViewAdapter.java

app/src/main/res/layout/
├── activity_main.xml
├── fragment_home.xml
├── fragment_detail.xml
├── fragment_bookmark.xml
├── news_cardview.xml
├── latest_news_cardview.xml
└── relate_news_cardview.xml

app/src/main/
└── AndroidManifest.xml
