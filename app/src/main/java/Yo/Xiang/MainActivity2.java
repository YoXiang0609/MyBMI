// 引入必要的 Android 套件
package Yo.Xiang;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

// 定義 MainActivity2 類別，繼承自 AppCompatActivity 類別
public class MainActivity2 extends AppCompatActivity {
    // 宣告 ActivityResultLauncher
    private ActivityResultLauncher<Intent> ACTIVITY_REPORT;
    // 建立 BMI 計算方法
    private double calculateBMI(double height, double weight) {
        if (height <= 0 || weight <= 0) {
            return 0;
        }
        double heightInMeters = height / 100.0; // 將身高轉換為公尺
        return weight / (heightInMeters * heightInMeters); // 使用 BMI 公式計算 BMI 值
    }

    // 在活動創建時被調用
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 設置畫面內容為指定的 layout 資源文件

        // 獲取介面元件
        EditText heightEditText = findViewById(R.id.height); // 輸入身高的 EditText
        EditText weightEditText = findViewById(R.id.weight); // 輸入體重的 EditText
        Button calculateButton = findViewById(R.id.button); // 計算按鈕
        TextView resultTextView = findViewById(R.id.result); // 顯示 BMI 結果的 TextView
        TextView suggestTextView = findViewById(R.id.suggest); // 顯示建議的 TextView

        // 初始化 ActivityResultLauncher
        ACTIVITY_REPORT = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData(); // 獲取返回的 Intent
                        if (data != null) {
                            double bmiResult = data.getDoubleExtra("BMI_RESULT", 0.0); // 從 Intent 中獲取 BMI 值
                            // 在這裡處理獲取到的 BMI 值，例如更新 UI 等
                            // 例如，你可以將 BMI 值設置到 TextView 中
                            suggestTextView.setText(getString(R.string.advice_history) + " " + bmiResult);
                            weightEditText.setText(R.string.input_empty);
                            weightEditText.requestFocus();
                        }
                    }
                });


        // 在按鈕點擊事件中
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 從 EditText 中獲取使用者輸入的身高和體重
                String height = heightEditText.getText().toString();
                String weight = weightEditText.getText().toString();

                try {
                    // 創建一個新的 Intent 並跳轉到 ReportActivity
                    Intent intent = new Intent(MainActivity2.this, ReportActivity.class);

                    // 創建一個 Bundle 並將數值放入其中
                    Bundle bundle = new Bundle();
                    bundle.putString("KEY_HEIGHT", height);
                    bundle.putString("KEY_WEIGHT", weight);

                    // 將 Bundle 附加到 Intent 中
                    intent.putExtras(bundle);

                    // 執行跳轉
                    //startActivity(intent);
                    ACTIVITY_REPORT.launch(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                    // 處理其他異常情況
                    Toast.makeText(MainActivity2.this, "發生錯誤，請稍後再試", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        // 設置計算按鈕的監聽器
//        calculateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                try {
//                    // 從 EditText 中獲取使用者輸入的身高和體重
//                    double height = Double.parseDouble(heightEditText.getText().toString());
//                    double weight = Double.parseDouble(weightEditText.getText().toString());
//
//                    // 計算 BMI 值
//                    double bmi = calculateBMI(height, weight);
//
//                    // 顯示 BMI 結果
//                    resultTextView.setText(getString(R.string.bmi_result) + " " + bmi);
//
//                    // 根據 BMI 值顯示不同的建議
//                    String suggest;
//                    if (bmi < 18.5) {
//                        suggest = getString(R.string.advice_light); // 體重過輕的建議
//                    } else if (bmi < 24.9) {
//                        suggest = getString(R.string.advice_average); // 正常體重的建議
//                    } else {
//                        suggest = getString(R.string.advice_heavy); // 體重過重的建議
//                    }
//                    suggestTextView.setText(suggest);
//                } catch (NumberFormatException e) {
//                    // 輸入不合法的數值，這裡可以顯示提示訊息或採取其他處理方式
//                    Toast.makeText(MainActivity2.this, "請先輸入身高體重喔!", Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                }
//            }
//        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            openOptionsDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void openOptionsDialog()
    {
        Toast popup = Toast.makeText(this, "BMI計算機", Toast.LENGTH_SHORT);
        popup.show();
    }
}
