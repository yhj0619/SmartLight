package mobile.smartteam.smartlight.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import mobile.smartteam.smartlight.BaseFragment;
import mobile.smartteam.smartlight.databinding.FragmentMainBinding;

public class MainFragment extends BaseFragment {
    private FragmentMainBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        String resultText = "[NULL]";
//
//        try {
//            resultText = new Task().execute().get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//
//        binding.statusTextView.setText(resultText);

        binding.connectButton.setOnClickListener(v -> {
            replaceFragment(new DeviceConnectionFragment(), "DeviceConnection");
        });

        binding.roomButton.setOnClickListener(v -> {
            replaceFragment(new RoomFragment(), "Room");
        });

        binding.livingRoomButton.setOnClickListener(v -> {
            replaceFragment(new RoomFragment(), "LivingRoom");
        });

        binding.testButton.setOnClickListener(v -> {
            new NoticeDialog(requireContext())
                    .setMessage(String.format("현재 시각 %s\nTV 시청 중인 것으로 감지되었습니다.\n자동 밝기 조절을 원하시면 'yes'를 선택해 주세요.",
                            new SimpleDateFormat("a hh:mm", Locale.KOREA).format(new Date())))
                    .setOnPositiveListener(dialogInterface -> {
                        replaceFragment(new RoomFragment(), "LivingRoom");

                        Toast.makeText(requireContext(), "밝기가 조절되었습니다.", Toast.LENGTH_SHORT).show();
                    })
                    .show();
        });

        getConnectedDevice().observe(getViewLifecycleOwner(), name -> {
            if (name == null || name.trim().isEmpty()) {
                binding.connectedDeviceTextView.setText("연결된 기기 없음");
                binding.livingRoomButton.setEnabled(false);
                binding.roomButton.setEnabled(false);
                binding.testButton.setEnabled(false);
            } else {
                binding.connectedDeviceTextView.setText(name);
                binding.livingRoomButton.setEnabled(true);
                binding.roomButton.setEnabled(true);
                binding.testButton.setEnabled(true);
            }
        });
    }
}
