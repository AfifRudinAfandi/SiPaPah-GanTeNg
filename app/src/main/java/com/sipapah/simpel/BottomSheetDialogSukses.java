package com.sipapah.simpel;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Timer;
import java.util.TimerTask;

public class BottomSheetDialogSukses extends BottomSheetDialogFragment {

    View btn_close;
//    TextView btn_home;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_dialog_sukses, container, false);

        btn_close = view.findViewById(R.id.btn_close);
//        btn_home = view.findViewById(R.id.btn_home);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });



//        btn_home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), HomeActivity.class);
//                startActivity(intent);
//                getActivity().finish();
//            }
//        });
        dissmis();

        return view;
    }
    private void dissmis() {
        final long period = 1600;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // do your task here
                dismiss();
            }
        }, 1600, period);
    }

}
