package com.agusgarcia.geonotes;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment implements View.OnClickListener {

    private FirstFragmentListener mListener;

    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        Button mapButton = (Button) view.findViewById(R.id.map_button);
        mapButton.setOnClickListener(this);

        Button listButton = (Button) view.findViewById(R.id.list_button);
        listButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FirstFragmentListener) {
            mListener = (FirstFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.map_button:
                if (mListener != null)
                    mListener.onButtonMapClick();
                break;
            case R.id.list_button:
                if (mListener != null)
                    mListener.onButtonListClick();
                break;
        }

    }

    public interface FirstFragmentListener {
        void onButtonMapClick();
        void onButtonListClick();
    }

}
