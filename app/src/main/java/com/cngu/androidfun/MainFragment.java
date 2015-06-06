package com.cngu.androidfun;


import android.os.Bundle;;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * View {@link Fragment} that is intended to be attached to {@link MainActivity}.
 */
public class MainFragment extends BaseFragment implements IMainFragment {

    /**
     * Factory method to instantiate a new instance of this fragment.
     *
     * <p>This factory method is used instead of overloading {@link MainFragment#MainFragment()}
     * because when the system needs to recreate this fragment, it will call the default no-arg
     * constructor, and the arguments passed to the overloaded constructor will be lost.
     * This factory method will persist all arguments in a Bundle, and be restored when the system
     * invokes the default no-arg constructor for fragment recreation.
     *
     * @return New object instance of {@link MainFragment}.
     */
    public static IMainFragment newInstance() {
        return new MainFragment();
    }

    public MainFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }


}
