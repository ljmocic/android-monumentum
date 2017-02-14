package eu.execom.monumentum;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import eu.execom.monumentum.adapters.MonumentsAdapter;
import eu.execom.monumentum.repository.MonumentDAORepository;
import eu.execom.monumentum.repository.MonumentTypeDAORepository;

@EActivity(R.layout.fragment_all_monuments)
public class MonumentByTypesActivity extends AppCompatActivity {

    @ViewById
    SwipeRefreshLayout swipeRefresh;

    @ViewById
    ListView listView;

    @Extra
    String monumentType;

    @Bean
    MonumentsAdapter monumentsAdapter;

    @Bean
    MonumentDAORepository monumentDAORepository;

    @Bean
    MonumentTypeDAORepository monumentTypeDAORepository;

    @AfterViews
    @UiThread(delay = 100)
    void setAdapter() {
        listView.setAdapter(monumentsAdapter);
        monumentsAdapter.setMonuments(monumentDAORepository.findByType(monumentTypeDAORepository
                .getMonumentTypeByName(monumentType)));

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                monumentsAdapter.setMonuments(monumentDAORepository.findByType(monumentTypeDAORepository
                        .getMonumentTypeByName(monumentType)));
                swipeRefresh.setRefreshing(false);
            }
        });
    }

}
