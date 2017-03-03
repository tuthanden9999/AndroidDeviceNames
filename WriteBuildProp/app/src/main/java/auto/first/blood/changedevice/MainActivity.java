package auto.first.blood.changedevice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private BuildPropFileHandle bpfh = new BuildPropFileHandle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bpfh.writeBuildPropFile(this, R.raw.supported_devices);

        //String buildPropFileContent = bpfh.readBuildPropFile();
        Toast.makeText(this, "Change device success. Please restart device to apply.", Toast.LENGTH_LONG).show();
    }

    public void changeDeviceBtn_onClick(View view) {
        bpfh.writeBuildPropFile(this, R.raw.supported_devices);

        //String buildPropFileContent = bpfh.readBuildPropFile();
        Toast.makeText(this, "Change device success. Please restart device to apply.", Toast.LENGTH_LONG).show();
    }
}
