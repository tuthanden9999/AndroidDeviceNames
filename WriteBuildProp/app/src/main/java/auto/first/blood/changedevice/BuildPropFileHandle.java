package auto.first.blood.changedevice;

import android.content.Context;
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

    public void writeBuildPropFile(Context context, int resRawFileId) {

        readBuildPropFile();

//        //Better RAM managment
//        prop.setProperty("ro.HOME_APP_ADJ", "1");
//        //Disables built-in error reporting.
//        prop.setProperty("profiler.force_disable_err_rpt", "1");
//        prop.setProperty("profiler.force_disable_ulog", "1");
//        //Better net speeds
//        prop.setProperty("net.tcp.buffersize.default", "4096,87380,256960,4096,16384,256960");
//        prop.setProperty("net.tcp.buffersize.wifi", "4096,87380,256960,4096,16384,256960");
//        prop.setProperty("net.tcp.buffersize.umts", "4096,87380,256960,4096,16384,256960");
//        prop.setProperty("net.tcp.buffersize.gprs", "4096,87380,256960,4096,16384,256960");
//        prop.setProperty("net.tcp.buffersize.edge", "4096,87380,256960,4096,16384,256960");
//        //Disables logcat
//        prop.setProperty("logcat.live", "disable");
//        //Support for ipv4 and ipv6
//        prop.setProperty("persist.telephony.support.ipv6", "1");
//        prop.setProperty("persist.telephony.support.ipv4", "1");
//
//        //Disables error checking
//        prop.setProperty("ro.kernel.android.checkjni", "0");
//        prop.setProperty("ro.kernel.checkjni", "0");
//        //Dalvik Virtual Machine tweaks
//        prop.setProperty("dalvik.vm.checkjni", "false");
//        prop.setProperty("dalvik.vm.dexopt-data-only", "1");
//        prop.setProperty("dalvik.vm.heapstartsize", "5m");
//        prop.setProperty("dalvik.vm.heapgrowthlimit", "48m");
//        prop.setProperty("dalvik.vm.heapsize", "64m");
//        prop.setProperty("dalvik.vm.verify-bytecode", "false");
//        prop.setProperty("dalvik.vm.execution-mode", "int:jit");
//        prop.setProperty("dalvik.vm.lockprof.threshold", "250");
//        prop.setProperty("dalvik.vm.dexopt-flags", "m=v,o=y");
//        prop.setProperty("dalvik.vm.stack-trace-file", "/data/anr/traces.txt");
//        prop.setProperty("dalvik.vm.jmiopts", "forcecopy");
//        // Disables locating. Also delete /system/app/networklocation.apk and /system/
//        prop.setProperty("framework/com.android.location.provider.jar", "");
//        prop.setProperty("ro.com.google.locationfeatures", "0");
//        prop.setProperty("ro.com.google.networklocation", "0");
//        //Disables sending of usage data
//        prop.setProperty("ro.config.nocheckin", "1");
//        //Disable notification while adb is active
//        prop.setProperty("persist.adb.notify", "0");
//        //build.prop tweaks for improved performance
//        prop.setProperty("debug.performance.tuning", "1");
//        // Disable strict mode checking.
//        prop.setProperty("persist.android.strictmode", "0");

        //Faster boot
        prop.setProperty("ro.config.hw_quickpoweron", "true");

        //change device here
        SupportedDevices supportedDevices = new SupportedDevices();
        Device device = supportedDevices.getRandomDeviceFromSupportedDevices(context, resRawFileId);
        prop.setProperty("ro.product.model", device.getDeviceModel());
        prop.setProperty("ro.product.device", device.getDeviceName());
        prop.setProperty("ro.product.brand", device.getDeviceBrand());
        prop.setProperty("ro.product.name", device.getDeviceName());
        prop.setProperty("ro.product.manufacturer", device.getDeviceBrand());
        prop.setProperty("ro.product.locale.region", "en-US");
        prop.setProperty("ro.build.product", device.getDeviceName());

        prop.setProperty("ro.build.id", device.getAndroidVersion().buildId);
        prop.setProperty("ro.build.display.id", device.getAndroidVersion().buildId + "." + device.getAndroidVersion().versionIncremental);
        prop.setProperty("ro.build.version.incremental", device.getAndroidVersion().versionIncremental);
        //prop.setProperty("ro.build.version.sdk", device.getAndroidVersion().versionSDK + ""); //error SDK
        prop.setProperty("ro.build.version.release", device.getAndroidVersion().versionRelease);
        prop.setProperty("ro.build.PDA", device.getAndroidVersion().versionIncremental);
        prop.setProperty("ro.build.hidden_ver", device.getAndroidVersion().versionIncremental);

        prop.setProperty("ro.build.fingerprint", device.getBuildFingerPrint());
        prop.setProperty("ro.build.description", device.getBuildDescription());

//        prop.setProperty("ro.telephony.default_network", "3");
//        prop.setProperty("ro.gsm.imei", "356871045027780");
//        prop.setProperty("ro.serialno", "SH196V201237");
//        prop.setProperty("net.hostname", "android-cd9f3782d41a0d81");

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

        Log.d("WBP", "Edit saved and a backup was made at " + Environment.getExternalStorageDirectory().getAbsolutePath() + ".bluestacks.prop.bak");
    }

//    public void backup() {
//        Process process = null;
//        DataOutputStream os = null;
//
//        try {
//            process = Runtime.getRuntime().exec("su");
//            os = new DataOutputStream(process.getOutputStream());
//            os.writeBytes("mount -o remount,rw -t yaffs2 /dev/block/mtdblock4 /system\n");
//            os.writeBytes("cp -f /system/build.prop " + Environment.getExternalStorageDirectory().getAbsolutePath() + "/build.prop.bak\n");
//            os.writeBytes("exit\n");
//            os.flush();
//            process.waitFor();
//        } catch (Exception e) {
//            Log.e("WBP", "backup Error1: " + e.getMessage());
//        } finally {
//            try {
//                if (os != null) {
//                    os.close();
//                }
//                process.destroy();
//            } catch (Exception e) {
//                Log.e("WBP", "backup Error2: " + e.getMessage());
//            }
//        }
//
//        Log.d("WBP", "build.prop Backup at " + Environment.getExternalStorageDirectory().getAbsolutePath() + "/build.prop.bak");
//    }
//
//    public void restore() {
//        Process process = null;
//        DataOutputStream os = null;
//
//        try {
//            process = Runtime.getRuntime().exec("su");
//            os = new DataOutputStream(process.getOutputStream());
//            os.writeBytes("mount -o remount,rw -t yaffs2 /dev/block/mtdblock4 /system\n");
//            os.writeBytes("mv -f /system/build.prop /system/build.prop.bak\n");
//            os.writeBytes("busybox cp -f " + Environment.getExternalStorageDirectory().getAbsolutePath() + "/build.prop.bak /system/build.prop\n");
//            os.writeBytes("chmod 644 /system/build.prop\n");
//            //os.writeBytes("mount -o remount,ro -t yaffs2 /dev/block/mtdblock4 /system\n");
//            os.writeBytes("exit\n");
//            os.flush();
//            process.waitFor();
//        } catch (Exception e) {
//            Log.e("WBP", "restore Error1: " + e.getMessage());
//        } finally {
//            try {
//                if (os != null) {
//                    os.close();
//                }
//                process.destroy();
//            } catch (Exception e) {
//                Log.e("WBP", "restore Error2: " + e.getMessage());
//            }
//        }
//
//        Log.d("WBP", "build.prop Restored from " + Environment.getExternalStorageDirectory().getAbsolutePath() + "/build.prop.bak");
//    }
//
//    private boolean runRootCommand(String command) {
//        Process process = null;
//        DataOutputStream os = null;
//
//        try {
//            process = Runtime.getRuntime().exec("su");
//            os = new DataOutputStream(process.getOutputStream());
//            os.writeBytes(command+"\n");
//            os.writeBytes("exit\n");
//            os.flush();
//            process.waitFor();
//        } catch (Exception e) {
//            Log.e("WBP", "runRootCommand Error1: " + e.getMessage());
//            return false;
//        } finally {
//            try {
//                if (os != null) {
//                    os.close();
//                }
//                process.destroy();
//            } catch (Exception e) {
//                Log.e("WBP", "runRootCommand Error2: " + e.getMessage());
//            }
//        }
//        return true;
//    }
}
