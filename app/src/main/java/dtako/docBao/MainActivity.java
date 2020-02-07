package dtako.docBao;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mikepenz.itemanimators.ScaleUpAnimator;
import com.mikepenz.itemanimators.SlideDownAlphaAnimator;
import com.mikepenz.itemanimators.SlideInOutLeftAnimator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<News> listNews = new ArrayList<>();


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_recycler_view);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CustomAdapter(listNews,MainActivity.this);
        autoRefresh();
        new CustomTask().execute("https://vnexpress.net/rss/tin-moi-nhat.rss");
    }
    public void autoRefresh(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(adapter);
//                autoRefresh();
            }
        },1000);
    }

    class CustomTask extends AsyncTask<String, Void, ArrayList<News>> {

        @Override
        protected ArrayList<News> doInBackground(String... strings) {
            try {
                Document document = Jsoup.connect(strings[0]).get();
                Elements element =  document.select("item");
                News news;
                for (Element element1 : element) {
                    news = new News(element1.select("title").text(), element1.select("pubDate").text(), element1.select("link").text(),
                            Jsoup.parse(element1.select("description").text()).select("img").attr("src"));
                    listNews.add(news);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return listNews;
        }

        @Override
        protected void onPostExecute(ArrayList<News> news) {
            super.onPostExecute(news);
            adapter = new CustomAdapter(news);
        }
    }
}
