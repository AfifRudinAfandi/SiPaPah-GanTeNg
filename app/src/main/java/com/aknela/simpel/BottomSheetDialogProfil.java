package com.aknela.simpel;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BottomSheetDialogProfil extends BottomSheetDialogFragment {

    View btn_close;
    TextView btn_lengkapi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_dialog_profil, container, false);

        btn_close = view.findViewById(R.id.btn_close);
        btn_lengkapi = view.findViewById(R.id.btn_lengkapi);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btn_lengkapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
                dismiss();
            }
        });



        return view;
    }
}
