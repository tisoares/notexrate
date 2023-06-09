package br.com.twoas.notexrate.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter;

import java.util.Date;

import br.com.twoas.notexrate.BuildConfig;
import br.com.twoas.notexrate.network.adapter.BigDecimalAdapter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * This is responsible to config Retrofit and return a connector service
 *
 * Created by TIAGO SOARES on 06/06/2023.
 */
public class RestClient {

    /**
     * This is our main backend/server URL.
     */
    private static final String REST_API_URL = BuildConfig.SERVER_BASE_URL;
    private static final String REST_CONFIG_API_URL = BuildConfig.CONFIG_SERVER_BASE_URL;

    private static final Retrofit s_retrofit;
    private static final Retrofit s_config_retrofit;


    static {
        // enable logging
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);

        Moshi moshi = new Moshi.Builder()
                .add(new BigDecimalAdapter())
                .add(Date.class, new Rfc3339DateJsonAdapter().nullSafe())
                .build();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();

            Request request = original.newBuilder()
                    .addHeader("Accept", "application/json")
                    .header("User-Agent", "NOTEXRATE")
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        });
        httpClient.addInterceptor(interceptor);
        httpClient.addNetworkInterceptor(new StethoInterceptor());

        OkHttpClient client = httpClient.build();

        s_retrofit = new Retrofit.Builder()
                .baseUrl(REST_API_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(client)
                .build();

        s_config_retrofit = new Retrofit.Builder()
                .baseUrl(REST_CONFIG_API_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(client)
                .build();
    }

    public static <T> T getService(Class<T> serviceClass) {
        return s_retrofit.create(serviceClass);
    }

    public static <T> T getConfigService(Class<T> serviceClass) {
        return s_config_retrofit.create(serviceClass);
    }
}
