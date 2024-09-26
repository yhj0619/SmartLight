package mobile.smartteam.smartlight.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import abak.tr.com.boxedverticalseekbar.BoxedVertical;
import mobile.smartteam.smartlight.BaseFragment;
import mobile.smartteam.smartlight.MainActivity;
import mobile.smartteam.smartlight.databinding.FragmentRoomBinding;

public class RoomFragment extends BaseFragment {
    private FragmentRoomBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRoomBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.statusTextView.setText("현재 당신의 기기는 방에 연결되어 있습니다. ");

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String page = "http://3.36.75.237/test.php";
                        URL url = new URL(page);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                        final StringBuilder sb = new StringBuilder();
                        String a = "";
                        if (conn != null) {
                            Log.i("tag", "conn 연결");
                            conn.setRequestProperty("Accept", "application/json");
                            conn.setConnectTimeout(10000);
                            conn.setRequestMethod("POST");
                            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                                String line;
                                while ((line = br.readLine()) != null) {
                                    sb.append(line);
                                }
                                br.close();
                                Log.i("tag", "결과 문자열 :" + sb.toString());
                                String level = "";
                                for (int i = 0; i < sb.length(); i++) {
                                    char ch = sb.charAt(i);
                                    if (48 <= ch && ch <= 57) {
                                        level += ch;
                                    } else {
                                        a += ch;
                                    }
                                }

                                binding.boxedVertical.setValue(Integer.parseInt(level.toString()));
                                binding.currentActionTextView.setText("현재 상태 : " + a);
                                conn.disconnect();
                            }
                        }

                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(),"밝기가 자동으로 조절되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }, 0);
                        Thread.sleep(60000);
                    } catch (Exception e) {
                        Log.i("tag", "error :" + e);
                    }
                }
            }
        });

        th.start();

        binding.boxedVertical.setOnBoxedPointsChangeListener(new BoxedVertical.OnValuesChangeListener() {
            @Override
            public void onPointsChanged(BoxedVertical boxedPoints, int points) {
                binding.currentLevelTextView.setText(String.format("Level %d", points));
            }

            @Override
            public void onStartTrackingTouch(BoxedVertical boxedPoints) {
            }

            @Override
            public void onStopTrackingTouch(BoxedVertical boxedPoints) {
            }
        });

        getConnectedDevice().observe(getViewLifecycleOwner(), name -> {
            if (name == null || name.trim().isEmpty()) {
                binding.connectedDeviceTextView.setText("연결된 기기 없음");
            } else {
                binding.connectedDeviceTextView.setText(name);
            }
        });
    }
}
