package pan.smart.smart.Network;

/**
 * Created by ahmed on 8/31/17.
 */


import retrofit2.Retrofit;

import static pan.smart.smart.Utills.Constant.BASE_URL_HTTP;


public class RetrofitClient {

    private static Retrofit retrofit = null;


    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_HTTP)
                    .build();
        }
        return retrofit;
    }
}