package com.sipapah.simpel;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BottomSheetDialogBelumLengkap extends BottomSheetDialogFragment {

    View btn_close;
    TextView back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_dialog_belum_lengkap, container, false);

        btn_close = view.findViewById(R.id.btn_close);
        back = view.findViewById(R.id.btn_lengkapi);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

//dissmis();

        return view;
//    }
//
//    private void dissmis() {
//        final long period = 1000;
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                // do your task here
//                dismiss();
//            }
//        }, 1000, period);
    }
}
