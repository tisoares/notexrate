package br.com.twoas.notexrate.network.services;

import br.com.twoas.notexrate.network.dto.config.MSNConfigDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetConfigDataService {
    @GET("/resolver/api/resolve/v3/config/")
    Call<MSNConfigDTO> getConfig(@Query("expType") String expType,
                                 @Query("expInstance") String expInstance,
                                 @Query("apptype") String apptype,
                                 @Query("v") String version,
                                 @Query("targetScope") String targetScope);
}
