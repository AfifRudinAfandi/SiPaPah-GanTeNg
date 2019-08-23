package com.sipapah.simpel;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class BottomSheetDialogSimpan extends BottomSheetDialogFragment {

    View btn_close;
    TextView back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_dialog_simpan, container, false);

        btn_close = view.findViewById(R.id.btn_close);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        dissmis();

        return view;
    }

    private void dissmis() {
        final long period = 1500;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // do your task here
                dismiss();
            }
        }, 1500, period);
    }
}
