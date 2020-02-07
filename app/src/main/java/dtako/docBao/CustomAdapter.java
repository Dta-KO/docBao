package dtako.docBao;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private ArrayList<News> dataSet;
    Activity activity;

    public CustomAdapter(ArrayList<News> dataSet,Activity activity) {
        this.dataSet = dataSet;
        this.activity = activity;
    }
    public CustomAdapter(ArrayList<News> dataSet){
        this.dataSet = dataSet;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_view, parent, false);
        CustomViewHolder holder = new CustomViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        News news = dataSet.get(position);
        holder.title.setText(Html.fromHtml(news.getTitle()));
        holder.timeUpdate.setText(Html.fromHtml(news.getPubDate()));
        //get img by picasso
        Picasso.get().load(news.getImg()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView title, timeUpdate;
        private ImageView img;

        private CustomViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTextView);
            timeUpdate = itemView.findViewById(R.id.timeUpdate);
            img = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity,DetailActivity.class);
                    activity.startActivity(intent);

                }
            });
        }
    }
}
