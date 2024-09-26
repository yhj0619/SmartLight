package mobile.smartteam.smartlight;

import mobile.smartteam.smartlight.json.HomeResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LightAPIService {

    @GET("/server_data.json")
    Call<HomeResult> getSituationResult();
}
