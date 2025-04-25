package com.example.healthcare;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class ArticleAdapter extends BaseAdapter {

    Context context;
    List<HashMap<String, String>> articles;
    LayoutInflater inflater;

    public ArticleAdapter(Context context, List<HashMap<String, String>> articles) {
        this.context = context;
        this.articles = articles;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public Object getItem(int position) {
        return articles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.article_item, null);

        TextView title = view.findViewById(R.id.articleTitle);
        ImageView image = view.findViewById(R.id.articleImage);

        title.setText(articles.get(i).get("title"));

        // Load image from internal storage path
        String imagePath = articles.get(i).get("image_path");
        Bitmap bmp = BitmapFactory.decodeFile(imagePath);
        image.setImageBitmap(bmp);

        return view;
    }
}
