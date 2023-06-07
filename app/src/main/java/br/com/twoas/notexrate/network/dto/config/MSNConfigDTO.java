package br.com.twoas.notexrate.network.dto.config;

import com.squareup.moshi.Json;

public class MSNConfigDTO {
    @Json(name="configs")
    private MSNConfigs configs;

    public MSNConfigDTO(MSNConfigs configs) {
        this.configs = configs;
    }

    public MSNConfigs getConfigs() {
        return configs;
    }

    public void setConfigs(MSNConfigs configs) {
        this.configs = configs;
    }
}
