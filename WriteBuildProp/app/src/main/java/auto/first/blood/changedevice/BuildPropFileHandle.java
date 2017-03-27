package auto.first.blood.changedevice;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by Administrator on 2/25/2017.
 */

public class BuildPropFileHandle {

    private String tempFile;
    private String propReplaceFile;
    private Properties prop;

    public BuildPropFileHandle() {
        tempFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/buildprop.tmp";
        propReplaceFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/propreplace.txt";
        createTempFile();
        prop = new Properties();
    }

    public String readBuildPropFile() {
        try {
            FileInputStream in = new FileInputStream(new File(tempFile));
            prop.load(in);
            in.close();
        } catch (IOException e) {
            Log.e("WBP", "readBuildPropFile Error1: " + e.getMessage());
        }
        Log.d("WBP", "readBuildPropFile:\n" + prop.toString());
        return prop.toString();
    }

    public void writeBuildPropFile(Device device) {

        readBuildPropFile();

        //Faster boot
        //prop.setProperty("ro.config.hw_quickpoweron", "true");

        //change device here
//        Device device = new Device();
        prop.setProperty("ro.product.model", device.getDeviceName().deviceModel);
        prop.setProperty("ro.product.device", device.getDeviceName().deviceName);
        prop.setProperty("ro.product.brand", device.getDeviceName().deviceBrand);
        prop.setProperty("ro.product.name", device.getDeviceName().deviceName);
        prop.setProperty("ro.product.manufacturer", device.getDeviceName().deviceBrand);
        prop.setProperty("ro.product.locale.region", "en-US");
        prop.setProperty("ro.build.product", device.getDeviceName().deviceName);

        prop.setProperty("ro.build.id", device.getAndroidVersion().buildId);
        prop.setProperty("ro.build.display.id", device.getAndroidVersion().buildId + "." + device.getAndroidVersion().versionIncremental);
        prop.setProperty("ro.build.version.incremental", device.getAndroidVersion().versionIncremental);
        //prop.setProperty("ro.build.version.sdk", device.getAndroidVersion().versionSDK + ""); //error SDK
        prop.setProperty("ro.build.version.release", device.getAndroidVersion().versionRelease);
        prop.setProperty("ro.build.PDA", device.getAndroidVersion().versionIncremental);
        prop.setProperty("ro.build.hidden_ver", device.getAndroidVersion().versionIncremental);

        prop.setProperty("ro.build.fingerprint", device.getBuildFingerPrint());
        prop.setProperty("ro.build.description", device.getBuildDescription());

        try {
            FileOutputStream out = new FileOutputStream(new File(tempFile));
            prop.store(out, null);
            out.close();

            replaceInFile(new File(tempFile));
            transferFileToSystem();
        } catch (IOException e) {
            Log.e("WBP", "writeBuildPropFile Error1: " + e.getMessage());
        }

        //finish();
    }

    private void createTempFile() {
        Process process = null;
        DataOutputStream os = null;

        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());

//            os.writeBytes("mount -o remount,rw -t yaffs2 /dev/block/mtdblock4 /system\n");
//            os.writeBytes("cp -f /system/build.prop " + Environment.getExternalStorageDirectory().getAbsolutePath() + "/buildprop.tmp\n");
//            os.writeBytes("chmod 777 " + Environment.getExternalStorageDirectory().getAbsolutePath() + "/buildprop.tmp\n");

            os.writeBytes("mount -o remount,rw -t yaffs2 /dev/block/mtdblock5 /data\n");
            os.writeBytes("cp -f /data/.bluestacks.prop " + Environment.getExternalStorageDirectory().getAbsolutePath() + "/buildprop.tmp\n");
            os.writeBytes("chmod 777 " + Environment.getExternalStorageDirectory().getAbsolutePath() + "/buildprop.tmp\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            Log.e("WBP", "createTempFile1 Error: " + e.getMessage());
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
                Log.e("WBP", "createTempFile2 Error: " + e.getMessage());
            }
        }

        tempFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/buildprop.tmp";
    }

    private void replaceInFile(File file) throws IOException {
        File tmpFile = new File(propReplaceFile);
        FileWriter fw = new FileWriter(tmpFile);
        Reader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        while (br.ready()) {
            fw.write(br.readLine().replaceAll("\\\\", "") + "\n");
        }

        fw.close();
        br.close();
        fr.close();
    }

    private void transferFileToSystem() {
        Process process = null;
        DataOutputStream os = null;

        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());

            os.writeBytes("mount -o remount,rw -t yaffs2 /dev/block/mtdblock4 /system\n");
            os.writeBytes("mv -f /system/build.prop /system/build.prop.bak\n");
            os.writeBytes("busybox cp -f " + propReplaceFile + " /system/build.prop\n");
            os.writeBytes("chmod 777 /system/build.prop\n");

            os.writeBytes("mount -o remount,rw -t yaffs2 /dev/block/mtdblock5 /data\n");
            os.writeBytes("mv -f /data/.bluestacks.prop /data/.bluestacks.prop.bak\n");
            os.writeBytes("busybox cp -f " + propReplaceFile + " /data/.bluestacks.prop\n");
            os.writeBytes("chmod 777 /data/.bluestacks.prop\n");

            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            Log.e("WBP", "transferFileToSystem Error1: " + e.getMessage());
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
                Log.e("WBP", "transferFileToSystem Error2: " + e.getMessage());
            }
        }

        Log.d("WBP", "Edit saved and a backup was made at " + Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + ".bluestacks.prop.bak");
    }
}
