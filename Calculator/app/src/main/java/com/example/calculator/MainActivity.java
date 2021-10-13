package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    private String text_box_ = "";
    private int result = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Create24Button();
    }

    private void Create24Button() {
        LinearLayout rows[] = Create6Row();
        AddRowToMainLayout(rows);
        String labels[] = InitAllLabel();
        Button btn;
        int row_index;
        for (int i = 0; i < 24; i++) {
            btn = CreateButton(labels[i], 65000 + i);
            row_index = i / 4;
            AddButtonToRow(btn, rows[row_index]);
        }
    }

    private void AddButtonToRow(Button btn, LinearLayout row) {
        row.addView(btn);
    }

    private Button CreateButton(String label, int id) {
        Button btn = new Button(this);
        btn.setText(label);
        btn.setId(id);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                char first_char = label.charAt(0);
                if (!(('0' <= first_char && first_char <= '9') || first_char == '+' || first_char == '-' || //
                        first_char == '=' || label == "Backspace" || first_char == 'C'))
                    return;

                if (first_char == 'C') {
                    text_box_ = "";
                }
                else if (label == "Backspace") {
                    if (text_box_.length() > 0) {
                        StringBuilder sb = new StringBuilder(text_box_);
                        sb.deleteCharAt(text_box_.length() - 1);
                        text_box_ = sb.toString();
                    }
                }
                else if (first_char != '=') {
                    text_box_ += label;
                }
                else if (first_char == '=') {
                    result = 0;
                    int temp = 0;
                    char symbol = '+';
                    for (int i = 0; i < text_box_.length(); i++) {
                        if ('0' <= text_box_.charAt(i) && text_box_.charAt(i) <= '9') {
                            temp = temp * 10 + text_box_.charAt(i) - '0';
                        }
                        else {
                            if (symbol == '+') result += temp;
                            else result -= temp;
                            temp = 0;
                            symbol = text_box_.charAt(i);
                        }
                    }
                    if (symbol == '+') result += temp;
                    else result -= temp;
                    text_box_ = String.valueOf(result);
                }
                EditText edit_text = (EditText)findViewById(R.id.editTextNumber);
                edit_text.setText(text_box_);
                return;
            }
        });
        return btn;
    }

    private String[] InitAllLabel() {
        return new String[] {
                "%", "CE", "C", "Backspace",
                "1/x", "x^2", "sqrt", "./.",
                "7", "8", "9", "*",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "+/-", "0", ".", "="
        };
    }

    private void AddRowToMainLayout(LinearLayout[] rows) {
        LinearLayout linear_layout = (LinearLayout)findViewById(R.id.linearlayoutMain);
        for (int i = 0; i < rows.length; i++) {
            linear_layout.addView(rows[i]);
        }
    }

    private LinearLayout[] Create6Row() {
        LinearLayout[] rows = new LinearLayout[6];
        for (int i = 0; i < 6; i++) {
            rows[i] = CreateRow(65500 + i);
        }
        return rows;
    }

    private LinearLayout CreateRow(int id) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setId(id);
        return row;
    }
}