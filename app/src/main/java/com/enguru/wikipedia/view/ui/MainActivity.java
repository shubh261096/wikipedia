package com.enguru.wikipedia.view.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enguru.wikipedia.R;
import com.enguru.wikipedia.service.model.search.PagesItem;
import com.enguru.wikipedia.service.model.search.SearchResponseModel;
import com.enguru.wikipedia.service.repo.Events;
import com.enguru.wikipedia.view.adapter.MainActivityAdapter;
import com.enguru.wikipedia.view.receiver.NetworkChangeReceiver;
import com.enguru.wikipedia.viewModel.MainActivityViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity implements MainActivityAdapter.OnItemClickListener {

    MainActivityViewModel mainActivityViewModel;
    private static final String TAG = "MainActivity";
    @BindView(R.id.rvWikipedia)
    RecyclerView rvWikipedia;
    private List<PagesItem> pagesItemList = new ArrayList<>();
    MainActivityAdapter mainActivityAdapter;
    private String lastSearchText;
    private NetworkChangeReceiver networkChangeReceiver;
    private boolean networkStatus;
    private static Snackbar snackbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        /* Starting observer of Internet change*/
//        networkChangeReceiver = new NetworkChangeReceiver();
//        registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
//        observeInternetChange();

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        observeSearchResult();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvWikipedia.setLayoutManager(layoutManager);
        mainActivityAdapter = new MainActivityAdapter(pagesItemList, this);
        rvWikipedia.setAdapter(mainActivityAdapter);
    }

//    private void observeInternetChange() {
//        NetworkChangeReceiver.networkChange.observe(this, new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean status) {
//
//            }
//        });
//    }

    @Override
    public void getNetworkStatus(boolean status) {
        networkStatus = status;
    }

    private void subscribe(String searchText) {
        mainActivityViewModel.sendRequest(searchText);
    }

    private void observeSearchResult() {
        mainActivityViewModel.getSearchResult().observe(this, new Observer<Events.SearchResponseEvent>() {
            @Override
            public void onChanged(@Nullable Events.SearchResponseEvent responseEvent) {
                if (responseEvent != null) {
                    if (mainActivityAdapter != null) {
                        mainActivityAdapter.clearData();
                    }

                    if (responseEvent.isSuccess()) {
                        SearchResponseModel searchResponseModel = responseEvent.getSearchResponseModel();
                        List<PagesItem> pagesItems = searchResponseModel.getQuery().getPages();
                        pagesItemList.addAll(pagesItems);
                        mainActivityAdapter.notifyDataSetChanged();
                    } else {
                        showInformativeDialog(MainActivity.this, responseEvent.getErrorModel().getError().getInfo());
                    }
                }
            }
        });
    }

    public void showInformativeDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.msg_retry), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mainActivityAdapter != null) {
                            mainActivityAdapter.clearData();
                        }
                        subscribe(lastSearchText);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return super.onCreateOptionsMenu(menu);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    lastSearchText = newText;
                    if (networkStatus) {
                        subscribe(newText);
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        String pageID = String.valueOf(pagesItemList.get(position).getPageid());
        Intent intent = new Intent(this, WikipediaPage.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("pageID", pageID);
        startActivity(intent);
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unregisterReceiver(networkChangeReceiver);
//    }
}
