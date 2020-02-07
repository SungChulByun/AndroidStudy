package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.mycalculator.R.color.*;

public class PartCalculation extends AppCompatActivity {
    public static final String partAns = "partAns";

    float result=0;
    String below="0";
    int isClicked=0;// plus = 1, minus = 2, multiply = 3, divide = 4
    boolean isFirst=true, pressEnter=false, dotClicked=false;
    int touch = 0;


    TextView above, main, history;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            findViewById(R.id.history).setVisibility(View.VISIBLE);
        }
        else{
            findViewById(R.id.history).setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_calculatioin);

        main = (TextView) findViewById(R.id.textView);
        above = (TextView) findViewById(R.id.textView2);
        history = (TextView) findViewById(R.id.history_text);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            findViewById(R.id.history).setVisibility(View.VISIBLE);
        }

    }
    public void init(){
        below="0";
        result=0;
        turnoff();
        isClicked=0;
        isFirst=true;
        pressEnter=false;
        dotClicked=false;
        touch=0;
        main.setText(below);
        above.setText("");

    }

    public String toStr(double x){
        String temp = String.valueOf(x);
        return temp;
    }
    public Float toNum(String x){

        return Float.parseFloat(x);
    }
    public int turnoff(){
        int re = isClicked;
        if(isClicked>0) {
//            Log.d("turnoff","button : "+isClicked);
            Button btn = (Button) findViewById(R.id.button8);
            btn.setBackgroundColor(btn.getResources().getColor(colorPrimary));
            btn = (Button) findViewById(R.id.button12);
            btn.setBackgroundColor(btn.getResources().getColor(colorPrimary));
            btn = (Button) findViewById(R.id.button16);
            btn.setBackgroundColor(btn.getResources().getColor(colorPrimary));
            btn = (Button) findViewById(R.id.button20);
            btn.setBackgroundColor(btn.getResources().getColor(colorPrimary));
            isClicked = 0;
        }
        return re;
    }
    public Float calculate(){
        int caltype = turnoff();
        isClicked=caltype;
        float be = toNum(below);

        Float last = toNum(below);
        Log.d("spide333", "caltype : "+caltype);
        if(caltype==0||isFirst){
            result=toNum(below);
            main.setText("= "+result);
        }
        else {
            if (caltype == 1) {
                below = toStr(result + be);
            } else if (caltype == 2) {
                below = toStr(result - be);
            } else if (caltype == 3) {
                below = toStr(result * be);
            } else if (caltype == 4) {
                below = toStr(((float)result) / be);
            }

            result=toNum(below);
            Log.d("spide333","result : "+below);
        }
        isFirst=false;
        return last;
    }

    public void click_enter(View view){
        if(touch>0) {
            Float last = calculate();
            String cur = above.getText().toString();
            above.setText(cur + last);
            main.setText("= "+toStr(result));
            pressEnter = true;
            touch=0;

            history.append(cur+last+" = "+result+"\n");
            sendAnswer();
        }
    }
    public void click_add(View view){
        if(isClicked!=0){
            above.setText(result+" + ");
        }

        isClicked = turnoff();

        if(touch>0) {
            calculate();
            touch=0;
            above.setText(toStr(result) + " + ");
            below = "0";
            main.setText(below);
        }
        else if(pressEnter){
            above.setText(result+" + ");
            below="0";
            main.setText(below);
            pressEnter=false;
        }
        isClicked = 1;
        Button btn = (Button) findViewById(R.id.button20);
        btn.setBackgroundColor(Color.GRAY);
    }
    public void click_minus(View view){
        if(isClicked!=0){
            above.setText(result+" − ");
        }

        isClicked = turnoff();

        if(touch>0) {
            calculate();
            touch=0;
            above.setText(toStr(result) + " − ");
            below = "0";
            main.setText(below);
        }
        else if(pressEnter){
            above.setText(result+" − ");
            below="0";
            main.setText(below);
            pressEnter=false;
        }
        isClicked = 2;
        Button btn = (Button) findViewById(R.id.button16);
        btn.setBackgroundColor(Color.GRAY);
    }
    public void click_multiply(View view){
        if(isClicked!=0){
            above.setText(result+" × ");
        }

        isClicked = turnoff();

        if(touch>0) {
            calculate();
            touch=0;
            above.setText(toStr(result) + " × ");
            below = "0";
            main.setText(below);
        }
        else if(pressEnter){
            above.setText(result+" × ");
            below="0";
            main.setText(below);
            pressEnter=false;
        }
        isClicked = 3;
        Button btn = (Button) findViewById(R.id.button12);
        btn.setBackgroundColor(Color.GRAY);
    }

    public void click_divide(View view){
        if(isClicked!=0){
            above.setText(result+" ÷ ");
        }

        isClicked = turnoff();

        if(touch>0) {
            calculate();
            touch=0;
            above.setText(toStr(result) + " ÷ ");
            below = "0";
            main.setText(below);
        }
        else if(pressEnter){
            above.setText(result+" ÷ ");
            below="0";
            main.setText(below);
            pressEnter=false;
        }
        isClicked = 4;
        Button btn = (Button) findViewById(R.id.button8);
        btn.setBackgroundColor(Color.GRAY);
    }

    public void restart(){
        if(pressEnter){
            init();
        }
    }

    public void delete(View view){
        if(touch>0) touch--;
        if(pressEnter){
            init();
        }
        else if(below.length()<=1){
            below ="0";
        }
        else{
            below = below.substring(0, below.length()-1);
        }
        main.setText(below);
    }
    public void reverse(View view){
        String temp = toStr(-toNum(below));
        restart();
        main.setText(temp);
        below = temp;
        touch++;
    }
    public void push0(View view){
        restart();
        if(!below.equals("0")) {
            below += "0";
        }
        main.setText(below);
        touch++;
    }
    public void push_1(View view){
        restart();
        int num = 1;
        if(below.equals("0")){
            below =String.valueOf(num);
        }
        else{
            below +=num;
        }
        main.setText(below);
        touch++;
    }
    public void push_2(View view){
        restart();
        int num = 2;
        if(below.equals("0")){
            below =String.valueOf(num);
        }
        else{
            below +=num;
        }
        main.setText(below);
        touch++;
    }
    public void push_3(View view){
        restart();
        int num = 3;
        if(below.equals("0")){
            below =String.valueOf(num);
        }
        else{
            below +=num;
        }
        main.setText(below);
        touch++;
    }
    public void push_4(View view){
        restart();
        int num = 4;
        if(below.equals("0")){
            below =String.valueOf(num);
        }
        else{
            below +=num;
        }
        main.setText(below);
        touch++;
    }
    public void push_5(View view){
        restart();
        int num = 5;
        if(below.equals("0")){
            below =String.valueOf(num);
        }
        else{
            below +=num;
        }
        main.setText(below);
        touch++;
    }
    public void push_6(View view){
        restart();
        int num = 6;
        if(below.equals("0")){
            below =String.valueOf(num);
        }
        else{
            below +=num;
        }
        main.setText(below);
        touch++;
    }
    public void push_7(View view){
        restart();
        int num = 7;
        if(below.equals("0")){
            below =String.valueOf(num);
        }
        else{
            below +=num;
        }
        main.setText(below);
        touch++;
    }
    public void push_8(View view){
        restart();
        int num = 8;
        if(below.equals("0")){
            below =String.valueOf(num);
        }
        else{
            below +=num;
        }
        main.setText(below);
        touch++;
    }
    public void push_9(View view){
        restart();
        int num = 9;
        if(below.equals("0")){
            below =String.valueOf(num);
        }
        else{
            below +=num;
        }
        main.setText(below);
        touch++;
    }
    public void push_dot(View view){
        restart();
        if(!dotClicked) {
            below += ".";
            main.setText(below);
            dotClicked=true;
        }
    }
    public void div_by_own(View view){
        String temp = toStr(((float) 1)/toNum(below));
        String temp2 = below;
        restart();
        above.setText("1 ÷ "+temp2);
        main.setText("= "+temp);
        below = temp;
        touch=0;
        pressEnter=true;
    }
    public void square(View view){
        String temp = below;
        restart();
        above.setText(temp+"²");
        Float re = (toNum(temp)*toNum(temp));
        main.setText("= "+re);
        below = toStr(re);
        touch=0;
        pressEnter=true;
    }
    public void root(View view){
        String temp = below;
        restart();
        above.setText("²√"+temp);
        float re = (float) (Math.sqrt(toNum(temp)));
        main.setText("= "+re);
        below = toStr(re);
        touch=0;
        pressEnter=true;
    }
    public void pressCE(View view){
        init();
    }
    public void pressC_only(View view){
        init();
        history.setText("");
    }
    public void sendAnswer(){
        Intent pintent = new Intent(PartCalculation.this, MainActivity.class);
        String message = toStr(result);
        Log.d("here", "cal result : "+message);
        pintent.putExtra(partAns,message);
//        startActivity(pintent);
        setResult(Activity.RESULT_OK, pintent);
        finish();
    }

}
