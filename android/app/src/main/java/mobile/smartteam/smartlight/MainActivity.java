package mobile.smartteam.smartlight;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import mobile.smartteam.smartlight.databinding.ActivityMainBinding;
import mobile.smartteam.smartlight.ui.MainFragment;

public class MainActivity extends AppCompatActivity {
    public static String KEY_CONNECTION = "connection";
    public static String ARG_DEVICE_NAME = "device_name";

    private ActivityMainBinding binding;
    private final MutableLiveData<String> connectedDevice = new MutableLiveData<>(null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().setFragmentResultListener(KEY_CONNECTION, this, (requestKey, result) -> {
            String deviceName = result.getString(ARG_DEVICE_NAME);
            if (deviceName != null) {
                connectedDevice.postValue(deviceName);
            }
        });

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new MainFragment())
                .commit();
    }

    public LiveData<String> getConnectedDevice() {
        return connectedDevice;
    }
}

//public class MainActivity extends AppCompatActivity {
//
//    final static String TAG = "MainActivity";
//
//    private Retrofit retrofit;
//    private LightAPIService lightAPIService;
//
//    String apiUrl;
//
//    TextView tvResult;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        tvResult = findViewById(R.id.tv);
//
//        apiUrl = getResources().getString(R.string.api_url);
//
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(150, TimeUnit.SECONDS)
//                .readTimeout(100, TimeUnit.SECONDS)
//                .writeTimeout(100, TimeUnit.SECONDS)
//                .build();
//
//        if(retrofit == null){
//            try {
//                retrofit = new Retrofit.Builder()
//                        .baseUrl(apiUrl)
//                        .client(client)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//        lightAPIService = retrofit.create(LightAPIService.class); //retrofit 실제 객체 만들기
//
//    }
//
//    public void onClick(View v){
//        switch (v.getId()){
//            case R.id.bnt_living_room:
//                Intent intent = new Intent(this, LivingRoomActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.btn_room:
//                Intent intent1 = new Intent(this, RoomActivity.class);
//                startActivity(intent1);
//                break;
//
//            case R.id.btn_test:
//                Call<HomeResult> apiCall = lightAPIService.getSituationResult();
//                apiCall.enqueue(apiCallback);
//                break;
//        }
//    }
//
//    Callback<HomeResult> apiCallback = new Callback<HomeResult>() {
//        @Override
//        public void onResponse(Call<HomeResult> call, Response<HomeResult> response) {
//            if(response.isSuccessful()){
//                HomeResult boxOfficeRoot = response.body();
//                List<RoomResult> list = boxOfficeRoot.getRoomResultList();
//                List<TimeResult> timeResultList = list.get(0).getTimeResultList();
//
//                tvResult.setText(timeResultList.get(0).getSituationResult().toString());
//            }
//        }
//
//        @Override
//        public void onFailure(Call<HomeResult> call, Throwable t) {
//            Log.e(TAG,t.toString());
//        }
//    };
//}
