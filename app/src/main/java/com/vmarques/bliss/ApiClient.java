package com.vmarques.bliss;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ApiClient {

//    /********
//     * URLS
//     *******/
//    public static final String BASE_URL = "https://private-bbbe9-blissrecruitmentapi.apiary-mock.com";
//    private static Retrofit retrofit = null;
//
//    /**
//     * Get Retrofit Instance
//     */
//    public static Retrofit getRetrofitInstance() {
//
//        if (retrofit==null) {
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//        }
//        return retrofit;
//    }
//
//    /**
//     * Get API Service
//     *
//     * @return API Service
//     */
//    public static ApiService getApiService() {
//        return getRetrofitInstance().create(ApiService.class);
//    }


    private static final String BASE_URL = "https://private-bbbe9-blissrecruitmentapi.apiary-mock.com";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

}
