package nemo.truewindcalcucator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.common.collect.Range;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button hitung = findViewById(R.id.bHitung);
        final Button reset = findViewById(R.id.bReset);

        final TextView tArahKapal = findViewById(R.id.tArahKapal);
        final TextView tArahTerukur = findViewById(R.id.tArahTerukur);
        final TextView tKecKapal = findViewById(R.id.tKecepatanKapal);
        final TextView tKecTerukur = findViewById(R.id.tKecepatanTerukur);
        final TextView tArahSebenarnya = findViewById(R.id.tArahSebenarnya);
        final TextView tKecepatanSebenarnya = findViewById(R.id.tKecepatanSebenarnya);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tArahKapal.setText("");
                tArahTerukur.setText("");
                tKecKapal.setText("");
                tKecTerukur.setText("");
                tArahSebenarnya.setText("");
                tKecepatanSebenarnya.setText("");
            }
        });

        hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AwesomeValidation validation = new AwesomeValidation(ValidationStyle.BASIC);

                boolean validated = true;

                validation.addValidation(MainActivity.this, R.id.tArahKapal, Range.closed(0, 360), R.string.err_range_arah);
                validation.addValidation(MainActivity.this, R.id.tArahTerukur, Range.closed(0, 360), R.string.err_range_arah);

                if (TextUtils.isEmpty(tArahKapal.getText())) {
                    tArahKapal.setError("Arah Kapal tidak boleh kosong!");
                    validated = false;
                }

                if (TextUtils.isEmpty(tKecKapal.getText())) {
                    tKecKapal.setError("Kecepatan kapal tidak boleh kosong");
                    validated = false;
                }

                if (TextUtils.isEmpty(tArahTerukur.getText())) {
                    tArahTerukur.setError("Arah terukur tidak boleh kosong");
                    validated = false;
                }

                if (TextUtils.isEmpty(tKecTerukur.getText())) {
                    tKecTerukur.setError("Kecepatan terukur tidak boleh kosong");
                    validated = false;
                }

                if (validation.validate() && validated) {

                    int kec_kapal = Integer.parseInt(tKecKapal.getText().toString());
                    int arah_kapal = Integer.parseInt(tArahKapal.getText().toString());
                    int arah_terukur = Integer.parseInt(tArahTerukur.getText().toString());
                    int kec_terukur = Integer.parseInt(tKecTerukur.getText().toString());

                    double kec_sebenarnya = Math.sqrt(Math.pow(kec_kapal, 2) + Math.pow(kec_terukur, 2) - (2 * kec_kapal * kec_terukur * Math.cos(Math.toRadians(arah_terukur))));
                    double arah_thd_kapal = arah_terukur + Math.toDegrees(Math.asin((kec_kapal * Math.sin(Math.toRadians(arah_terukur))) / kec_sebenarnya));

                    double arah_sebenarnya = arah_kapal + arah_thd_kapal;

                    tArahSebenarnya.setText(String.format("%.2f", arah_sebenarnya));
                    tKecepatanSebenarnya.setText(String.format("%.2f", kec_sebenarnya));

                }

            }
        });
    }
}
