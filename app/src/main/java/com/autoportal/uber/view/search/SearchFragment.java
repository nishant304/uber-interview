package com.autoportal.uber.view.search;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.autoportal.uber.R;
import com.autoportal.uber.model.Photo;
import com.autoportal.uber.network.Resource;
import com.autoportal.uber.repo.SearchRepo;
import com.autoportal.uber.view.search.adapter.SearchImageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchFragment extends Fragment implements TextWatcher, Observer<Resource<List<Photo>>> {


    @BindView(R.id.editText)
    EditText searchContainer;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private SearchViewModel viewModel;

    private SearchImageAdapter searchImageAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_search_screen,container,false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this,new SearchViewModel.Factory(new SearchRepo())).get(SearchViewModel.class);
        searchImageAdapter =  new SearchImageAdapter(new ArrayList<Photo>());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this,view);
        searchContainer.addTextChangedListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerView.setAdapter(searchImageAdapter);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(s.length()>0) {
            viewModel.searchImage(s.toString()).observe(this, this);
        }else {
            searchImageAdapter.setNewList(new ArrayList<Photo>());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onChanged(@Nullable Resource<List<Photo>> listResource) {
        if(listResource.getStatus() == Resource.LOADING){
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }else if(listResource.getStatus() == Resource.ERROR){
            progressBar.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            showError(listResource.getMessage());
        }else {
            progressBar.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            searchImageAdapter.setNewList(listResource.getData());
        }
    }

    private void showError(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
    }

}
