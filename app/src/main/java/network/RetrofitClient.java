package network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
  private static final String BASE_URL = "https://restcountries.eu/rest/v2/";

  private static RetrofitClient instance;

  private Retrofit retrofit;

  CountriesDataService countriesService;

  private RetrofitClient() {
    if (retrofit == null) {
      OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
      Gson gson = new GsonBuilder()
              .setLenient()
              .create();
//      HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//      loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//      clientBuilder.addInterceptor(loggingInterceptor);



      retrofit = new Retrofit.Builder()
              .baseUrl(BASE_URL)
              .client(clientBuilder.build())
              .addConverterFactory(GsonConverterFactory.create(gson))
              .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
              .build();

      countriesService = retrofit.create(CountriesDataService.class);
    }
  }


  public static RetrofitClient getInstance() {
    if (instance == null) {
      instance = new RetrofitClient();
    }
    return instance;
  }
}