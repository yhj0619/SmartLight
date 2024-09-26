package mobile.smartteam.smartlight;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

public class BaseFragment extends Fragment {
    protected LiveData<String> getConnectedDevice() {
        try {
            return ((MainActivity) requireActivity()).getConnectedDevice();
        } catch (Exception ignore) {
            return null;
        }
    }

    protected void replaceFragment(Fragment fragment, String tag) {
        try {
            if (requireActivity().getSupportFragmentManager().findFragmentByTag(tag) != null) {
                return;
            }

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment, tag)
                    .addToBackStack(null)
                    .commit();
        } catch (Exception ignore) {
        }
    }
}
