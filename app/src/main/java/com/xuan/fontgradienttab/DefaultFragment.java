package com.xuan.fontgradienttab;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * com.xuan.fontgradienttab
 *
 * @author by xuan on 2018/5/17
 * @version [版本号, 2018/5/17]
 * @update by xuan on 2018/5/17
 * @descript
 */
public class DefaultFragment extends Fragment {
    private TextView tv;
    private String text;

    public static Fragment newInstant(Bundle args){
        DefaultFragment fragment=new DefaultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public DefaultFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_default,null);


        tv = view.findViewById(R.id.tv);

        Bundle args=getArguments();
        text=args.getString("text");

        tv.setText(text);


        return view;
    }
}
