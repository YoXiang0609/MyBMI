package Yo.Xiang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import java.text.DecimalFormat;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ReportActivity", "onCreate called");
        setContentView(R.layout.activity_report);
        initViews();
        showResults();
        setListeners();
    }

    private Button button_back;
    private TextView show_result;
    private TextView show_suggest;

    private void initViews()
    {
        button_back = (Button)findViewById(R.id.button);
        show_result = (TextView)findViewById(R.id.result);
        show_suggest = (TextView)findViewById(R.id.suggest);
    }

    private void showResults()
    {
        try
        {
            DecimalFormat nf = new DecimalFormat("0.00");

            Bundle bundle = this.getIntent().getExtras();
            //身高
            double height = Double.parseDouble(bundle.getString("KEY_HEIGHT"))/100;
            //體重
            double weight = Double.parseDouble(bundle.getString("KEY_WEIGHT"));
            //計算出BMI值
            double BMI = weight / (height*height);

            //結果
            show_result.setText(getText(R.string.bmi_result) + nf.format(BMI));

            //建議
            if(BMI > 25) //太重了
                show_suggest.setText(R.string.advice_heavy);
            else if(BMI < 20) //太輕了
                show_suggest.setText(R.string.advice_light);
            else //剛剛好
                show_suggest.setText(R.string.advice_average);
            // 創建一個新的 Intent，將 BMI 值放入其中
            Intent resultIntent = new Intent();
            resultIntent.putExtra("BMI_RESULT", BMI);

            // 設置返回結果和 Intent
            setResult(RESULT_OK, resultIntent);
        }
        catch(Exception e)
        {
            Toast.makeText(this, "要先輸入身高體重喔!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setListeners()
    {
        button_back.setOnClickListener(backtickMain);
    }

    private Button.OnClickListener backtickMain = new Button.OnClickListener()
    {
        public void onClick(View v)
        {
            ReportActivity.this.finish();
        }
    };
}
