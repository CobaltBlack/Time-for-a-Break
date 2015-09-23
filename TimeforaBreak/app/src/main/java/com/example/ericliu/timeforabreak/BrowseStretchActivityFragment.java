package com.example.ericliu.timeforabreak;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class BrowseStretchActivityFragment extends Fragment {

    public BrowseStretchActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("BrowseStretchFragment", "Program here");
        return inflater.inflate(R.layout.fragment_browse_stretch, container, false);
    }
}
