package auto.first.blood.changedevice;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String wipeFilePath;
    private String uninstallFilePath;

    private BuildPropFileHandle bpfh = new BuildPropFileHandle();
    AppDataCleaner appDataCleaner = new AppDataCleaner();
    TextView selectedWipeAppsTextView;
    private List<String> packageNameList = new ArrayList<>();
    private List<String> selectedWipeArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bpfh.writeBuildPropFile(this, R.raw.supported_devices);
        Toast.makeText(this, "Change device success. Please restart device to apply.", Toast.LENGTH_LONG).show();

        wipeFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wipeApps.txt";
        selectedWipeAppsTextView = (TextView) findViewById(R.id.selectedWipeAppsTextView);
        selectedWipeAppsTextView.setMovementMethod(new ScrollingMovementMethod());
        File wipeFile = new File(wipeFilePath);
        if(wipeFile.exists()) {
            initWipeAppsByReadFile();
        } else {
            initDefaultWipeApps();
        }
        wipeSelectedAppsByPackageName();
        selectWipeAppsByPackageName();
    }

    public void selectWipeAppsByPackageName() {
        packageNameList = appDataCleaner.loadAllPackageName(this);
        Spinner wipeSpinner = (Spinner)findViewById(R.id.wipeSpinner);
        ArrayAdapter<String> wipeSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, packageNameList);
        wipeSpinner.setAdapter(wipeSpinnerAdapter);
        wipeSpinner.setSelection(wipeSpinnerAdapter.getPosition("com.google.android.gms"));

        wipeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedPackageName = parent.getItemAtPosition(position).toString().trim();
                if(!selectedWipeAppsTextView.getText().toString().contains(selectedPackageName) && !selectedWipeArray.contains(selectedPackageName)) {
                    selectedWipeAppsTextView.append(selectedPackageName + "\n");
                    selectedWipeArray.add(selectedPackageName);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void wipeSelectedAppsByPackageName() {
        for(String selectedPackageName : selectedWipeArray) {
            appDataCleaner.clearApplicationDataByPackageName(this, selectedPackageName);
        }
    }

    public void clearWipeApps() {
        selectedWipeAppsTextView.setText("");
        selectedWipeArray.clear();
        TextFileHandle.writeToFile(selectedWipeArray, wipeFilePath, this);
    }

    public void initDefaultWipeApps() {
        selectedWipeAppsTextView.setText("");
        selectedWipeArray.clear();
        selectedWipeAppsTextView.append("com.google.android.gms\n");
        selectedWipeAppsTextView.append("com.android.browser\n");
        selectedWipeAppsTextView.append("com.android.vending\n");
        selectedWipeAppsTextView.append("com.android.providers.downloads.ui\n");
        selectedWipeAppsTextView.append("com.android.providers.downloads\n");
        selectedWipeArray.add("com.google.android.gms");
        selectedWipeArray.add("com.android.browser");
        selectedWipeArray.add("com.android.vending");
        selectedWipeArray.add("com.android.providers.downloads.ui");
        selectedWipeArray.add("com.android.providers.downloads");
        TextFileHandle.writeToFile(selectedWipeArray, wipeFilePath, this);
    }

    public void initWipeAppsByReadFile() {
        selectedWipeArray = TextFileHandle.readFromFile(wipeFilePath, this);
        for(String selectedWipe : selectedWipeArray) {
            selectedWipeAppsTextView.append(selectedWipe + "\n");
        }
    }

    public void changeDeviceBtn_onClick(View view) {
        bpfh.writeBuildPropFile(this, R.raw.supported_devices);
        Toast.makeText(this, "Change device success. Please restart device to apply.", Toast.LENGTH_LONG).show();
    }

    public void wipeSaveBtn_onClick(View view) {
        TextFileHandle.writeToFile(selectedWipeArray, wipeFilePath, this);
        Toast.makeText(this, "Save done. Please restart app to apply.", Toast.LENGTH_LONG).show();
    }

    public void wipeDefaultBtn_onClick(View view) {
        initDefaultWipeApps();
    }

    public void wipeClearBtn_onClick(View view) {
        clearWipeApps();
    }
}
