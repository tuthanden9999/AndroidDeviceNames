package auto.first.blood.changedevice;

import android.content.Context;
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
    private static Context context;

//    private String currentAppPackageName = "auto.first.blood.changedevice";
//
//    private String wipeFilePath;
//    private String uninstallFilePath;
//
//    AppDataCleaner appDataCleaner = new AppDataCleaner();
//    TextView selectedWipeAppsTextView;
//    TextView selectedUninstallAppsTextView;
//    private List<String> packageNameList = new ArrayList<>();
//    private List<String> selectedWipeArray = new ArrayList<>();
//    private List<String> selectedUninstallArray = new ArrayList<>();

    public static Context getAppContext() {
        return MainActivity.context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.context = getApplicationContext();

        setContentView(R.layout.activity_main);
        changeDevice();

//        packageNameList = appDataCleaner.loadAllPackageName(this);
//
//        wipeFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wipeApps.txt";
//        selectedWipeAppsTextView = (TextView) findViewById(R.id.selectedWipeAppsTextView);
//        selectedWipeAppsTextView.setMovementMethod(new ScrollingMovementMethod());
//        File wipeFile = new File(wipeFilePath);
//        if(wipeFile.exists()) {
//            initWipeAppsByReadFile();
//        } else {
//            initDefaultWipeApps();
//        }
//        wipeSelectedAppsByPackageName();
//        selectWipeAppsByPackageName();
//
//        uninstallFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/uninstallApps.txt";
//        selectedUninstallAppsTextView = (TextView) findViewById(R.id.selectedUninstallAppsTextView);
//        selectedUninstallAppsTextView.setMovementMethod(new ScrollingMovementMethod());
//        File uninstallFile = new File(uninstallFilePath);
//        if(uninstallFile.exists()) {
//            initUninstallAppsByReadFile();
//        } else {
//            initDefaultUninstallApps();
//        }
//        uninstallSelectedAppsByPackageName();
//        selectUninstallAppsByPackageName();
    }

//    public void selectWipeAppsByPackageName() {
//        Spinner wipeSpinner = (Spinner)findViewById(R.id.wipeSpinner);
//        ArrayAdapter<String> wipeSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, packageNameList);
//        wipeSpinner.setAdapter(wipeSpinnerAdapter);
//        wipeSpinner.setSelection(wipeSpinnerAdapter.getPosition(currentAppPackageName));
//
//        wipeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedPackageName = parent.getItemAtPosition(position).toString().trim();
//                if(!selectedWipeAppsTextView.getText().toString().contains(selectedPackageName)
//                        && !selectedWipeArray.contains(selectedPackageName)
//                        && !selectedPackageName.equals(currentAppPackageName)) {
//                    selectedWipeAppsTextView.append(selectedPackageName + "\n");
//                    selectedWipeArray.add(selectedPackageName);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }
//
//    public void wipeSelectedAppsByPackageName() {
//        for(String selectedPackageName : selectedWipeArray) {
//            appDataCleaner.clearApplicationDataByPackageName(this, selectedPackageName);
//        }
//    }
//
//    public void clearWipeApps() {
//        selectedWipeAppsTextView.setText("");
//        selectedWipeArray.clear();
//        TextFileHandle.writeToFile(selectedWipeArray, wipeFilePath);
//    }
//
//    public void initDefaultWipeApps() {
//        selectedWipeAppsTextView.setText("");
//        selectedWipeArray.clear();
//        selectedWipeAppsTextView.append("com.google.android.gms\n");
//        selectedWipeAppsTextView.append("com.android.browser\n");
//        selectedWipeAppsTextView.append("com.android.vending\n");
//        selectedWipeAppsTextView.append("com.android.providers.downloads.ui\n");
//        selectedWipeAppsTextView.append("com.android.providers.downloads\n");
//        selectedWipeArray.add("com.google.android.gms");
//        selectedWipeArray.add("com.android.browser");
//        selectedWipeArray.add("com.android.vending");
//        selectedWipeArray.add("com.android.providers.downloads.ui");
//        selectedWipeArray.add("com.android.providers.downloads");
//        TextFileHandle.writeToFile(selectedWipeArray, wipeFilePath);
//    }
//
//    public void initWipeAppsByReadFile() {
//        selectedWipeArray = TextFileHandle.readFromFile(wipeFilePath);
//        for(String selectedWipe : selectedWipeArray) {
//            selectedWipeAppsTextView.append(selectedWipe + "\n");
//        }
//    }
//
//    public void selectUninstallAppsByPackageName() {
//        Spinner uninstallSpinner = (Spinner)findViewById(R.id.uninstallSpinner);
//        ArrayAdapter<String> uninstallSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, packageNameList);
//        uninstallSpinner.setAdapter(uninstallSpinnerAdapter);
//        uninstallSpinner.setSelection(uninstallSpinnerAdapter.getPosition(currentAppPackageName));
//
//        uninstallSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedPackageName = parent.getItemAtPosition(position).toString().trim();
//                if(!selectedUninstallAppsTextView.getText().toString().contains(selectedPackageName)
//                        && !selectedUninstallArray.contains(selectedPackageName)
//                        && !selectedPackageName.equals(currentAppPackageName)) {
//                    selectedUninstallAppsTextView.append(selectedPackageName + "\n");
//                    selectedUninstallArray.add(selectedPackageName);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }
//
//    public void uninstallSelectedAppsByPackageName() {
//        for(String selectedPackageName : selectedUninstallArray) {
//            appDataCleaner.uninstallApplicationByPackageName(this, selectedPackageName);
//        }
//    }
//
//    public void clearUninstallApps() {
//        selectedUninstallAppsTextView.setText("");
//        selectedUninstallArray.clear();
//        TextFileHandle.writeToFile(selectedUninstallArray, uninstallFilePath);
//    }
//
//    public void initDefaultUninstallApps() {
//        selectedUninstallAppsTextView.setText("");
//        selectedUninstallArray.clear();
//        TextFileHandle.writeToFile(selectedUninstallArray, uninstallFilePath);
//    }
//
//    public void initUninstallAppsByReadFile() {
//        selectedUninstallArray = TextFileHandle.readFromFile(uninstallFilePath);
//        for(String selectedUninstall : selectedUninstallArray) {
//            selectedUninstallAppsTextView.append(selectedUninstall + "\n");
//        }
//    }
//
//    //event
//    public void wipeSaveBtn_onClick(View view) {
//        TextFileHandle.writeToFile(selectedWipeArray, wipeFilePath);
//        Toast.makeText(this, "Save done. Please restart app to apply.", Toast.LENGTH_LONG).show();
//    }
//
//    public void wipeDefaultBtn_onClick(View view) {
//        initDefaultWipeApps();
//    }
//
//    public void wipeClearBtn_onClick(View view) {
//        clearWipeApps();
//    }
//
//    public void uninstallSaveBtn_onClick(View view) {
//        TextFileHandle.writeToFile(selectedUninstallArray, uninstallFilePath);
//        Toast.makeText(this, "Save done. Please restart app to apply.", Toast.LENGTH_LONG).show();
//    }
//
//    public void uninstallDefaultBtn_onClick(View view) {
//        initDefaultUninstallApps();
//    }
//
//    public void uninstallClearBtn_onClick(View view) {
//        clearUninstallApps();
//    }

    public void changeDeviceBtn_onClick(View view) {
        changeDevice();
    }

    public void changeDevice() {
        FakeHardwareInfo.CreatDataCpu(context);
        Utils.setSharedPrefs();
//        bpfh.writeBuildPropFile();
        Toast.makeText(this, "Change device success.", Toast.LENGTH_LONG).show();
    }
}
