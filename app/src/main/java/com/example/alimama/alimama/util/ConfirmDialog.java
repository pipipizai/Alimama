package com.example.alimama.alimama.util;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.widget.Toast;



public class ConfirmDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        builder.setTitle("^ ^")
                .setMessage("Login Successful！")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        return builder.create();
    }
}



// Dialog list
//public class ConfirmDialog extends AppCompatDialogFragment {
//
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//
//        final String[] items = {"hello", "word"};
//        AlertDialog.Builder listDialog =
//                new AlertDialog.Builder(getActivity()) {
//
//                    @Override
//                    public AlertDialog create() {
//                        items[0] = "Payment Successful";
//                        return super.create();
//                    }
//
////                    @Override
////                    public AlertDialog show() {
////                        items[1] = "two";
////                        return super.show();
////                    }
//                };
//        listDialog.setTitle("^ ^");
//        listDialog.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // ...To-do
//            }
//        });
        /* @setOnDismissListener Dialog销毁时调用
         * @setOnCancelListener Dialog关闭时调用
         */

//        listDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            public void onDismiss(DialogInterface dialog) {
//                Toast.makeText(getApplicationContext(),
//                        "Dialog被销毁了",
////                        Toast.LENGTH_SHORT).show();
////            }
////        });
//        return listDialog.show();
//    }
//}
