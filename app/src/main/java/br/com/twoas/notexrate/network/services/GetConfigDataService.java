package br.com.twoas.notexrate.network.services;

import br.com.twoas.notexrate.network.dto.config.MSNConfigDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetConfigDataService {
    @GET("/resolver/api/resolve/v3/config/?expType=AppConfig&expInstance=default&apptype=finance&v=20230602.223&targetScope=%7B%22audienceMode%22%3A%22none%22,%22browser%22%3A%7B%22browserType%22%3A%22chrome%22,%22version%22%3A%22113%22,%22ismobile%22%3A%22true%22%7D,%22deviceFormFactor%22%3A%22mobile%22,%22domain%22%3A%22www.msn.com%22,%22locale%22%3A%7B%22content%22%3A%7B%22language%22%3A%22pt%22,%22market%22%3A%22br%22%7D,%22display%22%3A%7B%22language%22%3A%22pt%22,%22market%22%3A%22br%22%7D%7D,%22os%22%3A%22android%22,%22platform%22%3A%22web%22,%22pageType%22%3A%22finance%3A%3Aportfolio%22,%22pageExperiments%22%3A%5B%5D%7D")
    Call<MSNConfigDTO> getConfig();
}
