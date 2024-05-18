/*
 * Project Name: My Electricity
 * Created by Mohamad Ikhwan Rosdi
 * Copyright © 2024. All rights reserved.
 * Last modified 06/05/2024, 2:44 pm
 *
 */

package dev.lab.myelectricity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    Button buttonCalc;
    EditText etNumber , etRebate;
    TextView textView,textViewR;

    FloatingActionButton floatingActionInfo;
    float xAxis;
    float yAxis;
    int lastAction;
    private float initialX;
    private float initialY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        textViewR = findViewById(R.id.textR);
        etNumber = findViewById(R.id.etNumber);
        etRebate = findViewById(R.id.etRebate);
        buttonCalc = findViewById(R.id.buttonCalc);
        floatingActionInfo = findViewById(R.id.floatingbtnInfo);
        buttonCalc.setOnClickListener(this);
        floatingActionInfo.setOnClickListener(this);
        floatingActionInfo.setOnTouchListener((View.OnTouchListener) this);
        setSupportActionBar(findViewById(R.id.my_toolbar));
    }

    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                initialX = v.getX();
                initialY = v.getY();

                xAxis = initialX - event.getRawX();
                yAxis = initialY - event.getRawY();
                lastAction = MotionEvent.ACTION_DOWN;
                break;

            case MotionEvent.ACTION_MOVE:
                float newX = event.getRawX() + xAxis;
                float newY = event.getRawY() + yAxis;

                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
                int screenWidth = displayMetrics.widthPixels;
                int screenHeight = displayMetrics.heightPixels;

                Rect rectangle = new Rect();
                Window window = getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
                int statusBarHeight = rectangle.top;

                int maxAllowedX = screenWidth - v.getWidth();
                int maxAllowedY = screenHeight - v.getHeight();

                if (newX >= 0 && newX <= maxAllowedX && newY >= statusBarHeight && newY <= maxAllowedY) {
                    v.setX(newX);
                    v.setY(newY);
                } else {
                    ObjectAnimator animX = ObjectAnimator.ofFloat(v, "x", initialX);
                    ObjectAnimator animY = ObjectAnimator.ofFloat(v, "y", initialY);
                    animX.setDuration(300);
                    animY.setDuration(300);
                    animX.start();
                    animY.start();
                }

                lastAction = MotionEvent.ACTION_MOVE;
                break;

            case MotionEvent.ACTION_UP:
                if (lastAction == MotionEvent.ACTION_DOWN) {
                    createpopUpwindowInfo();
                }
                break;

            default:
                return false;
        }
        return true;
    }

    private void showShortToast(final String message) {
        final Toast toast = Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.show();
            }
        }, 10); // 1000 milliseconds = 1 second
    }

    private void createpopUpwindow(double total, double totalR, double totalCharge) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popUpView = inflater.inflate(R.layout.mainpopup, null);

        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        PopupWindow popupWindow = new PopupWindow(popUpView, width, height, focusable);

        View layout = findViewById(android.R.id.content).getRootView();

        layout.post(new Runnable() {
            public void run() {
                popupWindow.showAtLocation(layout, Gravity.BOTTOM, 0, 0);
            }
        });
        showShortToast("Calculation Successful!");

        TextView textViewRPopup = popUpView.findViewById(R.id.textR);
        TextView textViewCPopup = popUpView.findViewById(R.id.textCharge);
        TextView textViewPopup = popUpView.findViewById(R.id.textView);

        textViewCPopup.setText("\nCharges : RM" + String.format("%.2f", totalCharge));
        textViewRPopup.setText("\nRebate : RM" + String.format("%.2f", totalR));
        textViewPopup.setText("\nTotal After Rebate : RM" + String.format("%.2f", total));

        TextView Gotit = popUpView.findViewById(R.id.Gotit);
        Gotit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
    private void createpopUpwindowInfo() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popUpInfo = inflater.inflate(R.layout.infopopup, null);

        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        PopupWindow popupWindow1 = new PopupWindow(popUpInfo, width, height, focusable);

        View layout = findViewById(android.R.id.content).getRootView();

        layout.post(new Runnable() {
            public void run() {
                popupWindow1.showAtLocation(layout, Gravity.BOTTOM, 0, 0);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (popupWindow1 != null && popupWindow1.isShowing()) {
                            popupWindow1.dismiss();
                        }
                    }
                }, 2000);
            }
        });
        showShortToast("Information");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        try {
            String useStr = etNumber.getText().toString();
            String rebateStr = etRebate.getText().toString();

            if (useStr.isEmpty() || rebateStr.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter values in both fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double use = Double.parseDouble(useStr);
            double rebate1 = Double.parseDouble(rebateStr);
            double total, totalR, reb, afterReb, totalCharge = 0;

            if (view == buttonCalc) {

                if (rebate1 < 0 || rebate1 > 5) {
                    Toast.makeText(getApplicationContext(), "Please enter a rebate percentage (0%-5%) ONLY", Toast.LENGTH_SHORT).show();
                    return;
                } else if (use < 1) {
                    Toast.makeText(getApplicationContext(), "Electricity usage must be 1 or more", Toast.LENGTH_SHORT).show();
                    return;
                }


                etNumber.setError(null);
                etRebate.setError(null);

                if (use <= 200) {
                    totalCharge = use * 0.218;
                } else if (use <= 300) {
                    totalCharge = (200 * 0.218) + ((use - 200) * 0.334);
                } else if (use <= 600) {
                    totalCharge = (200 * 0.218) + (100 * 0.334) + ((use - 300) * 0.516);
                } else if (use <= 900) {
                    totalCharge = (200 * 0.218) + (100 * 0.334) + (300 * 0.516) + ((use - 600) * 0.546);
                } else if (use >= 901) {
                    totalCharge = (200 * 0.218) + (100 * 0.334) + (300 * 0.516) + (300 * 0.546) + ((use - 900) * 0.571);
                }
                reb = rebate1 / 100;
                afterReb = 1 - reb;
                total = totalCharge * afterReb;
                totalR = totalCharge * reb;

                createpopUpwindow(total, totalR, totalCharge);
            } else if (view == floatingActionInfo) {
                createpopUpwindowInfo();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Please enter valid numbers in the input fields", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selected = item.getItemId();

        if(selected == R.id.menuAbout){
            Toast.makeText(this,"About Developer!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,SecondActivity.class);
            startActivity(intent);
            return true;
        }
        else if (selected == R.id.menuTutorial) {
            // Show a toast message
            Toast.makeText(this, "Tutorial", Toast.LENGTH_SHORT).show();

            // Create an Intent to open the YouTube link in a web browser
            String url = "https://youtu.be/HhSZWJAjvWU";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

            // Start the activity to open the link in the web browser
            startActivity(intent);

            return true;
        }
        return false;
    }
}
