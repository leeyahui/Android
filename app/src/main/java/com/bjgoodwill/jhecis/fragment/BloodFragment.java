package com.bjgoodwill.jhecis.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.bjgoodwill.jhecis.R;

public class BloodFragment extends Fragment {

    private static final String PVID = "pvid";

    private String pvid;

    public BloodFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pvid Parameter 1.
     * @return A new instance of fragment BloodFragment.
     */
    public static BloodFragment newInstance(String pvid) {
        BloodFragment fragment = new BloodFragment();
        Bundle args = new Bundle();
        args.putString(PVID, pvid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pvid = getArguments().getString(PVID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blood, container, false);
    }
}