package pan.smart.smart.Network;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import pan.smart.smart.Utills.Constant;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

import static pan.smart.smart.Utills.Constant.BASE_URL_HTTP;


public class NetworkUtil {

    public static RetrofitInterface getRetrofit(){



            return RetrofitClient.getClient(BASE_URL_HTTP).create(RetrofitInterface.class);



      /*  RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

       return new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL_HTTP)
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build().create(RetrofitInterface.class);*/

    }

    public static RetrofitInterface getRetrofit2() {

        //String credentials = email + ":" + password;
        //UserLogin user =new UserLogin() ;
        //user.setEmailEncoded(Base64.encodeToString(email.getBytes(),Base64.NO_WRAP));
        //user.setPasswordEncoded(Base64.encodeToString(password.getBytes(),Base64.NO_WRAP));
        //String emailEn = Base64.encodeToString(lang.getBytes(), Base64.NO_WRAP);
        //String passwordEn = Base64.encodeToString(password.getBytes(), Base64.NO_WRAP);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(chain -> {

            Request original = chain.request();
            Request.Builder builder = original.newBuilder()
                    .method(original.method(),original.body());
            return  chain.proceed(builder.build());

        });

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        return new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL_HTTP)
                .client(httpClient.build())
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitInterface.class);
    }

    public static RetrofitInterface getRetrofit3(){

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        return new Retrofit.Builder()
                .baseUrl(BASE_URL_HTTP)
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitInterface.class);

    }

}