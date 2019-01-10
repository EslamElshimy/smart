package pan.smart.smart.Search;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import pan.smart.smart.Models.CountryModelResponse;
import pan.smart.smart.Network.NetworkUtil;
import pan.smart.smart.R;
import pan.smart.smart.Utills.Validation;
import pan.smart.smart.adapters.SearchAdapter;
import pan.smart.smart.adapters.searchModelRecycler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static pan.smart.smart.Utills.Constant.buildDialog;


public class searchFragment extends Fragment implements SearchInterface {



    EditText search_txt;
    SearchPresenter searchPresenter;
    RecyclerView category_recyclerView;
    SearchAdapter adapter;
    private ArrayList<searchModelRecycler> originalList = new ArrayList<>();
    private ArrayList<searchModelRecycler> searchList = new ArrayList<>();
    private CompositeSubscription mSubscriptions;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the lyout for this fragment
        View view = inflater.inflate(R.layout.search, container, false);
        search_txt                         = view.findViewById(R.id.Search_txt);
        category_recyclerView              =  view.findViewById(R.id.Recycler_category);
        category_recyclerView.setHasFixedSize(true);
        category_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));



        mSubscriptions = new CompositeSubscription();
        adapter  = new SearchAdapter (originalList,getContext());


        search_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String txt =  search_txt.getText().toString();
                filterData(txt);

            }

            @Override
            public void afterTextChanged(Editable editable) {




            }
        });



        if (Validation.isConnected(getContext()))
        {

        }
        else
        {
            buildDialog(getActivity()).show().setCanceledOnTouchOutside(false);

        }

        loadList();

       return view;
    }

    private  void filterData(String textToSearch){

        searchList.clear();

        if (textToSearch.isEmpty()){

            adapter = new SearchAdapter(searchList,getContext());
            adapter.notifyDataSetChanged();
            category_recyclerView.setAdapter(adapter);
        }
        else{
            for ( int iterator = 0 ;iterator < originalList.size();iterator++){
                // categoryName.get(iterator).contains(textToSearch);
                if(originalList.get(iterator).getCountryName().toLowerCase().contains(textToSearch.toLowerCase())||originalList.get(iterator).getCapital().toLowerCase().contains(textToSearch.toLowerCase()))
                {
                    searchList.add(originalList.get(iterator));

                    adapter = new SearchAdapter(searchList,getContext());
                    adapter.notifyDataSetChanged();
                    category_recyclerView.setAdapter(adapter);
                }
            }
            adapter.update(searchList);
        }

    }
    @Override
    public ArrayList<searchModelRecycler> loadCountryList() {
        return null;
    }
    public void loadList()
    {



        mSubscriptions.add(NetworkUtil.getRetrofit2().getCountries()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this :: handleResponse, this :: handleError));


    }

    private void handleResponse(List<CountryModelResponse> countryModelResponses) {

        for (int i = 0 ; i<countryModelResponses.size();i++)
        {
            if (countryModelResponses.isEmpty())
            {
                Toast.makeText(getContext(),"No data found",Toast.LENGTH_LONG).show();
            }
            else
            {
                originalList.add(new searchModelRecycler(countryModelResponses.get(i).getName(),countryModelResponses.get(i).getCapital(),0));
            }

        }


    }

    private void handleError(Throwable throwable) {

        Toast.makeText(getContext(),throwable.toString(),Toast.LENGTH_LONG).show();

    }


}
