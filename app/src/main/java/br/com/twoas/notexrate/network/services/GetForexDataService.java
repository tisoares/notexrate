package br.com.twoas.notexrate.network.services;

import java.util.List;

import br.com.twoas.notexrate.network.dto.forex.CurrencyDTO;
import br.com.twoas.notexrate.network.dto.forex.IdMapDTO;
import br.com.twoas.notexrate.network.dto.forex.QuoteDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetForexDataService {

    @GET("/service/Finance/Quotes?ocid=finance-utils-peregrine&cm=pt-br&it=web&wrapodata=false")
    Call<List<QuoteDTO>> getQuotes(@Query("apikey") String apikey,
                                  @Query("activityId") String activityId,
                                  @Query(value = "ids", encoded = true) String ids);
    @GET("/service/Finance/Quotes?ocid=finance-utils-peregrine&cm=pt-br&it=web&wrapodata=false")
    Call<List<List<QuoteDTO>>> getQuotesMulti(@Query("apikey") String apikey,
                                   @Query("activityId") String activityId,
                                   @Query(value = "ids", encoded = true) List<String> ids);
    @GET("/service/Finance/IdMap?ocid=finance-utils-peregrine&cm=pt-br&it=web")
    Call<List<IdMapDTO>> getCurrenciesCodes(@Query("apikey") String apikey,
                                            @Query("activityId") String activityId,
                                            @Query(value = "MStarIds", encoded = true) List<String> MStarIds);

    @GET("/service/Finance/Calculator/CurrenciesStaticData?ocid=finance-utils-peregrine&cm=pt-br&it=web")
    Call<List<CurrencyDTO>> getCurrencies(@Query("apikey") String apikey,
                                          @Query("activityId") String activityId);
}
