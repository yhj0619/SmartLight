package mobile.smartteam.smartlight.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import mobile.smartteam.smartlight.BaseFragment;
import mobile.smartteam.smartlight.MainActivity;
import mobile.smartteam.smartlight.databinding.FragmentDeviceConnectionBinding;
import mobile.smartteam.smartlight.databinding.ItemDeviceBinding;

public class DeviceConnectionFragment extends BaseFragment {
    private FragmentDeviceConnectionBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDeviceConnectionBinding.inflate(inflater, container, false);
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

        ArrayList<String> devices = new ArrayList<>();
        devices.add("K-1098AD122");
        devices.add("R-1092DK112");
        devices.add("K-2093ZA231");
        devices.add("K-80984KK11");
        devices.add("Z-ZZK133329");
        devices.add("A-12309KKAD");
        devices.add("B-99809WKL");
        devices.add("A-1098787K");

        DeviceAdapter adapter = new DeviceAdapter(devices);
        adapter.setOnItemClickListener(name -> {
            new NoticeDialog(requireContext())
                    .setMessage(String.format("%s\n기기와 연결을 원하시면\n'yes'를 선택해 주세요.", name))
                    .setOnPositiveListener((dialog) -> {
                        connect(name);
                    })
                    .show();
        });

        binding.recyclerView.setAdapter(adapter);
    }

    private void connect(String name) {
        Bundle arguments = new Bundle();
        arguments.putString(MainActivity.ARG_DEVICE_NAME, name);

        Toast.makeText(requireContext(), String.format("%s 와 연결되었습니다.", name), Toast.LENGTH_SHORT).show();

        requireActivity().getSupportFragmentManager().setFragmentResult(MainActivity.KEY_CONNECTION, arguments);
        requireActivity().getOnBackPressedDispatcher().onBackPressed();
    }


    private static class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceItemViewHolder> {
        private final List<String> items;
        private Consumer<String> onItemClickListener;

        private DeviceAdapter(List<String> items) {
            this.items = items;
        }

        public void setOnItemClickListener(Consumer<String> onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @NonNull
        @Override
        public DeviceItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            ItemDeviceBinding binding = ItemDeviceBinding.inflate(inflater, parent, false);
            return new DeviceItemViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull DeviceItemViewHolder holder, int position) {
            String item = items.get(position);
            holder.binding.nameTextView.setText(item);
            holder.binding.getRoot().setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.accept(item);
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        static class DeviceItemViewHolder extends RecyclerView.ViewHolder {
            final ItemDeviceBinding binding;

            DeviceItemViewHolder(ItemDeviceBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}
