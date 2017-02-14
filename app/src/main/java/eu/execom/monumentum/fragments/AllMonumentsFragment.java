package eu.execom.monumentum.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import eu.execom.monumentum.R;
import eu.execom.monumentum.adapters.MonumentsAdapter;
import eu.execom.monumentum.repository.MonumentDAORepository;


@EFragment(R.layout.fragment_all_monuments)
public class AllMonumentsFragment extends Fragment {

    @ViewById
    SwipeRefreshLayout swipeRefresh;

    @ViewById
    ListView listView;

    @Bean
    MonumentsAdapter monumentsAdapter;

    @Bean
    MonumentDAORepository monumentDAORepository;

    @AfterViews
    @UiThread(delay = 100)
    void setAdapter() {
        listView.setAdapter(monumentsAdapter);
        monumentsAdapter.setMonuments(monumentDAORepository.findAll());

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                monumentsAdapter.setMonuments(monumentDAORepository.findAll());
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        if (monumentsAdapter != null) {
            monumentsAdapter.setMonuments(monumentDAORepository.findAll());
        }
    }
}
