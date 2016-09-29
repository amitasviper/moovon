package com.appradar.viper.moovon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mutils.UserProgress;

public class WaterIntakeActivity extends AppCompatActivity {

    Button btn_save_water;
    EditText et_water_capacity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_intake);

        initViews();
    }

    private void initViews()
    {
        btn_save_water = (Button) findViewById(R.id.btn_save_water);
        et_water_capacity = (EditText) findViewById(R.id.et_water_capacity);

        btn_save_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int capacity;
                try
                {
                    capacity = Integer.parseInt(et_water_capacity.getText().toString());
                    UserProgress.getInstance().addNewWaterProgress(capacity);
                    Log.e("WaterIntakeActivity", "Capacity is : " + capacity);
                    et_water_capacity.setText("");
                    Toast.makeText(WaterIntakeActivity.this, "Value saved", Toast.LENGTH_LONG).show();

                }
                catch (Exception ex)
                {
                    Toast.makeText(WaterIntakeActivity.this, "Invalid input", Toast.LENGTH_LONG).show();
                }


            }
        });
    }
}
