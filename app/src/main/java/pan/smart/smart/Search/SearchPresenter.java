package pan.smart.smart.Search;

import android.content.Context;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import pan.smart.smart.Models.CountryModelResponse;
import pan.smart.smart.Network.NetworkUtil;
import pan.smart.smart.Register.RegisterInterface;
import pan.smart.smart.Utills.Validation;
import pan.smart.smart.adapters.searchModelRecycler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class SearchPresenter implements SearchInterface {

    Context context;
    private CompositeSubscription mSubscriptions;
    KProgressHUD hud;
    ArrayList<searchModelRecycler> list = new ArrayList<>();


    public SearchPresenter(Context context)
    {
        this.context = context;
        mSubscriptions = new CompositeSubscription();
        hud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setMaxProgress(100);

    }

    @Override
    public ArrayList<searchModelRecycler> loadCountryList() {


        loadList();

//        for (int i = 0 ; i < list.size() ;i++)
//        {
//            originalList.add(list.get(i));
//        }

        return list;
    }

    public void loadList()
    {

        hud.show();

        mSubscriptions.add(NetworkUtil.getRetrofit2().getCountries()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this :: handleResponse, this :: handleError));


    }

    private void handleResponse(List<CountryModelResponse> countryModelResponses) {
        hud.dismiss();
        for (int i = 0 ; i<countryModelResponses.size();i++)
        {
            if (countryModelResponses.isEmpty())
            {
                Toast.makeText(context,"No data found",Toast.LENGTH_LONG).show();
            }
            else
            {
                list.add(new searchModelRecycler(countryModelResponses.get(i).getName(),countryModelResponses.get(i).getCapital(),0));
            }

        }
    }

    private void handleError(Throwable throwable) {
        hud.dismiss();
        Toast.makeText(context,throwable.toString(),Toast.LENGTH_LONG).show();

    }

    
}
