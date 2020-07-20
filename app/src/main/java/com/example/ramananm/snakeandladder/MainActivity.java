package com.example.ramananm.snakeandladder;

import android.app.ActionBar;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import java.util.*;
public class MainActivity extends AppCompatActivity {
    String[][] board = { {"*", "*", "*", "*", "L4t", "S6t", "*", "S7t", "*", "*"},
            {"*", "S5t", "*", "*", "*", "*", "*", "L5t", "*", "*"},
            {"*", "*", "*", "*", "*", "S4t", "*", "*", "*", "S7v"},
            {"*", "*", "S3t", "*", "*", "*", "L3t", "*", "S2t", "*"},
            {"*", "*", "*", "*", "L2t", "*", "*", "*", "*", "L5v"},
            {"L4v", "S5v", "*", "*", "*", "*", "*", "*", "*", "*"},
            {"*", "*", "S6v", "*", "L1t", "*", "*", "*", "*", "*"},
            {"*", "S1t", "L2v", "*", "*", "*", "*", "*", "S4v", "L3v"},
            {"*", "*", "*", "*", "S2v", "*", "*", "S3v", "*", "*"},
            {"*", "*", "*", "S1v", "*", "*", "L1v", "*", "*", "*"}
    };
    int rows = 10;
    int columns = 10;
    int sPoint1 = 10;
    int sPoint2 = 0;
    int refID = 0;
    Map<Integer, String> mSnake = new HashMap<Integer, String>();
    Map<Integer, String> mLadd = new HashMap<Integer, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                if(board[i][j].startsWith("S") && board[i][j].endsWith("v")) {
                    int keyValue = Integer.parseInt(board[i][j].substring(1, board[i][j].length() - 1));
                    String pos = String.valueOf(i) + "," + String.valueOf(j);
                    mSnake.put(keyValue, pos);
                }
                else if (board[i][j].startsWith("L") && board[i][j].endsWith("t")) {
                    int keyValue = Integer.parseInt(board[i][j].substring(1, board[i][j].length() - 1));
                    String pos = String.valueOf(i) + "," + String.valueOf(j);
                    mLadd.put(keyValue, pos);
                }
            }
        }

        final Button button = findViewById(R.id.Roller_Dice);
        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                //System.out.println(temp);
                toChangeBoard(v);

            }
        });
    }
        private void toChangeBoard(View view) {

            if(refID != 0) {
                ((Button) findViewById(refID)).setText("");
            }


            Random rn = new Random();
            int temp = rn.nextInt(6) + 1;
            //setContentView(R.layout.activity_main);
            //TextView textView = (TextView) findViewById(R.id.valueFromRandom);
            //textView.setText(String.valueOf(temp));
            if(sPoint1 % 2 != 0) {
                sPoint2 += temp;
                int tempCount = 0;
                if(sPoint2 > columns - 1) {
                    int diff = (sPoint2 - (columns - 1));
                    sPoint1 -= diff / columns;
                    if(diff % columns != 0) {
                        sPoint1 -= 1;
                    }
                    sPoint2 = columns - diff;
                    if(sPoint2 < 0) {
                        sPoint2 *= -1;
                        sPoint2 -= 1;
                    }
                }
            }
            else {
                int temp1 = sPoint2;
                sPoint2 -= temp;
                if(sPoint2 < 0) {
                    int diff = (-1) * sPoint2;
                    sPoint1 -= diff / columns;
                    if(diff % columns != 0) {
                        sPoint1 -= 1;
                    }
                    sPoint2 = Math.abs(temp1 - temp) - 1;
                    if(sPoint2 > columns - 1) {
                        sPoint2 = columns - (sPoint2 - (columns - 1));
                    }
                    //sPoint2 = (columns - (Math.abs((Math.abs(temp1 - temp)) - columns)));
                }
            }
            if(sPoint1 < 0) {
                LayoutInflater layoutInflater;
                layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.popup,null);
                PopupWindow popupWindow = new PopupWindow(customView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                View linearLayout1 = (LinearLayout)findViewById(R.id.linearLayout1);
                popupWindow.showAtLocation(linearLayout1, Gravity.CENTER, 0, 0);
            }
            else {
                if(board[sPoint1][sPoint2].startsWith("L") && board[sPoint1][sPoint2].endsWith("v")) {
                    int keyToGet = Integer.parseInt(board[sPoint1][sPoint2].substring(1, board[sPoint1][sPoint2].length() - 1));
                    String[] justFun = (mLadd.get(keyToGet)).split(",");
                    sPoint1 = Integer.parseInt(justFun[0]);
                    sPoint2 = Integer.parseInt(justFun[1]);
                }
                else if(board[sPoint1][sPoint2].startsWith("S") && board[sPoint1][sPoint2].endsWith("t")) {
                    int keyToGet = Integer.parseInt(board[sPoint1][sPoint2].substring(1, board[sPoint1][sPoint2].length() - 1));
                    String[] justFun = (mSnake.get(keyToGet)).split(",");
                    sPoint1 = Integer.parseInt(justFun[0]);
                    sPoint2 = Integer.parseInt(justFun[1]);
                }
            }
            if(sPoint1 < 0 || sPoint1 == 0 && sPoint2 == 0) {
                if(!(sPoint1 < 0)) {
                    String imgButtID = "button_" + sPoint1 + sPoint2;
                    refID = getResources().getIdentifier(imgButtID, "id", getPackageName());
                    ((Button) findViewById(refID)).setText("X");
                }
                LayoutInflater layoutInflater;
                layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.popup,null);
                PopupWindow popupWindow = new PopupWindow(customView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                View linearLayout1 = (LinearLayout)findViewById(R.id.linearLayout1);
                popupWindow.showAtLocation(linearLayout1, Gravity.CENTER, 0, 0);
            }
            else {
                String imgButtID = "button_"+ sPoint1 + sPoint2;
                refID = getResources().getIdentifier(imgButtID,"id", getPackageName());
                ((Button)findViewById(refID)).setText("X");
            }
        }
}
