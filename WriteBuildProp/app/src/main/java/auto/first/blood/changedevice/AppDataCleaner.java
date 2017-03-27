package auto.first.blood.changedevice;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 3/7/2017.
 */

public class AppDataCleaner{
    public AppDataCleaner() {

    }

    public List<String> loadAllPackageName(Context context) {
        List<String> packageNameList = new ArrayList<>();
        List<PackageInfo> packageInfos = context.getPackageManager().getInstalledPackages(0);
        for(PackageInfo packageInfo : packageInfos) {
            String packageName = packageInfo.packageName;
            packageNameList.add(packageName);
        }
        return packageNameList;
    }

    public void clearApplicationDataByPackageName(Context context, String packageName) {
        Process process = null;
        DataOutputStream os = null;

        try {
            File cache = context.getCacheDir();
            File appsParentDir = new File(cache.getParent()).getParentFile();
            File expectedAppDir = new File(appsParentDir.getAbsolutePath() + "/" + packageName);

            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());

            os.writeBytes("mount -o remount,rw -t yaffs2 /dev/block/mtdblock5 /data\n");
            os.writeBytes("rm -rf " + expectedAppDir.getAbsolutePath() + "/*\n");
            Log.d("WBP", "rm -rf " + expectedAppDir.getAbsolutePath() + "/*");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            Log.e("WBP", "Clear data fail:" + e.toString());
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
                Log.e("WBP", "Clear data 2 fail: " + e.getMessage());
            }
        }
    }

    public void uninstallApplicationByPackageName(Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + packageName));
        context.startActivity(intent);
    }
}
