package com.example.newsly;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsly.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVAdaptor.CategoryClickInterface{


    private RecyclerView newRV, categoryRV;
    private ProgressBar loadingPB;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryRVModel> categoryRVModelArrayList;
    private CategoryRVAdaptor categoryRVAdaptor;
    private NewsRVAdaptor newsRVAdaptor;


//5219c91a94d6434ab824f906bda4f774
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
       categoryRV=findViewById((R.id.idRVCategories));
       loadingPB=findViewById(R.id.idPBLoading);
       articlesArrayList = new ArrayList<>();
       categoryRVModelArrayList = new ArrayList<>();
       newsRVAdaptor = new NewsRVAdaptor(articlesArrayList, this);
       categoryRVAdaptor =  new CategoryRVAdaptor(categoryRVModelArrayList, this, this::onCategoryClick);
       newRV.setLayoutManager(new LinearLayoutManager(this));
       newRV.setAdapter(newsRVAdaptor);
       categoryRV.setAdapter(categoryRVAdaptor);
       getCategories();
       getNews("All");
       newsRVAdaptor.notifyDataSetChanged();

    }

    private void getCategories(){
        categoryRVModelArrayList.add(new CategoryRVModel("All", "\n"));
        categoryRVModelArrayList.add(new CategoryRVModel("Technology", "https://images.unsplash.com/photo-1461749280684-dccba630e2f6?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MzF8fHRlY2hub2xvZ3l8ZW58MHx8MHx8&auto=format&fit=crop&w=900&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Science", "https://images.unsplash.com/photo-1507668077129-56e32842fceb?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8c2NpZW5jZXxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=900&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Sports", "https://images.unsplash.com/photo-1606925797300-0b35e9d1794e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjN8fHNwb3J0c3xlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("General", ""));
        categoryRVModelArrayList.add(new CategoryRVModel("Business", "https://images.unsplash.com/photo-1600880292203-757bb62b4baf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nnx8YnVzaW5lc3N8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Entertainment", "https://images.unsplash.com/photo-1519758965401-328f73031806?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTN8fGVudGVydGFpbm1lbnR8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Health", "https://images.unsplash.com/photo-1505576399279-565b52d4ac71?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8aGVhbHRofGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));

    }

    private void getNews(String category)
    {
        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryURL = "https://newsapi.org/v2/top-headlines?country=in&category="+category+ "&apikey=5219c91a94d6434ab824f906bda4f774";
        String url = "https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=5219c91a94d6434ab824f906bda4f774";
        String BASE_URL= "https://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModel> call;
        if(category.equals("All")){
            call = retrofitAPI.getAllMews(url);
        }
        else {
            call = retrofitAPI.getNewsByCategory(categoryURL);
        }

        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                NewsModel newsModel = response.body();
                loadingPB.setVisibility(View.GONE);
                ArrayList<Articles> articles = newsModel.getArticles();
                for (int i=0; i<articles.size(); i++)
                {
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(),articles.get(i).getDesciption(), articles.get(i).getUrlToImage(),
                           articles.get(i).getUrl(), articles.get(i).getContent()));
                }
                newsRVAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fail to get news", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCategoryClick(int position) {
        String category = categoryRVModelArrayList.get(position).getCategory();
        getNews(category);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}