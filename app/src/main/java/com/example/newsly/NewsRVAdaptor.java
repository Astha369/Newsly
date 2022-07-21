package com.example.newsly;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsRVAdaptor extends RecyclerView.Adapter<NewsRVAdaptor.ViewHolder> {

    private ArrayList<Articles> articlesArrayList;
    private Context context;

    public NewsRVAdaptor(ArrayList<Articles> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @androidx.annotation.NonNull
    @Override
    public NewsRVAdaptor.ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_rv_item,parent,false);
        return new NewsRVAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull NewsRVAdaptor.ViewHolder holder, int position) {
         Articles articles=articlesArrayList.get(position);
         holder.subTitleTV.setText(articles.getDesciption());;
         holder.titleTV.setText(articles.getTitle());
        Picasso.get().load(articles.getUrlToImage()).into(holder.newsIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,News_Detail_Activity.class);
                i.putExtra("title", articles.getTitle());
                i.putExtra("content", articles.getContent());
                i.putExtra("description", articles.getDesciption());
                i.putExtra("image", articles.getUrlToImage());
                i.putExtra("url", articles.getUrl());
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTV,subTitleTV;
        private ImageView newsIV;
        public ViewHolder(@androidx.annotation.NonNull View itemView){
            super(itemView);
            titleTV=itemView.findViewById(R.id.idTVNewsHeading);
            subTitleTV=itemView.findViewById(R.id.idTVSubTitle);
            newsIV=itemView.findViewById(R.id.idIVNews);
        }
    }
}
