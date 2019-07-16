package io.pearlmaknun.mypharmacist_apoteker.util;

import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class CommonUtil {

    public static boolean validateEmptyEntries(ArrayList<View> fields) {
        boolean check = true;
        for (int i = 0; i < fields.size(); i++) {
            EditText currentField = (EditText) fields.get(i);
            if (currentField.getText().toString().length() <= 0) {
                currentField.setError("Please fill out this field");
//                showToast("Please fill out this field");
                check = false;
            }
        }
        return check;
    }
}
