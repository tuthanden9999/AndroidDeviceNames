package auto.first.blood.changedevice;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Created by Administrator on 2/27/2017.
 */

public class SupportedDevices {
    private static int DEVICES_MAX = 14545;
    public SupportedDevices() {
    }

    public String readALineInFile(int lineIndex, InputStream inputStream) {
        String line = "";

        try {
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                bufferedReader.readLine();
                int count = 0;
                while ( (line = bufferedReader.readLine()) != null ) {
                    if(count == lineIndex) break;
                    count++;
                }

                Log.d("WBP", "lineIndex = count: " + count + " ; " + lineIndex);

                inputStream.close();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("WBP", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("WBP", "Can not read file: " + e.toString());
        }

        return line;
    }

    public Device getRandomDeviceFromSupportedDevices(Context context, int resRawFileId) {
        Device device;
        int randomNum = new Random().nextInt(DEVICES_MAX);
        String randomDeviceStr = readALineInFile(randomNum, context.getResources().openRawResource(resRawFileId));
        Log.d("WBP", "randomNum: " + randomNum);
        Log.d("WBP", "randomDeviceStr: " + randomDeviceStr);

        String[] randomDeviceStrParts = randomDeviceStr.split(",");
        if(randomDeviceStrParts.length != 4) {
            device = new Device();
        } else {
            device = new Device(randomDeviceStrParts[0], randomDeviceStrParts[1], randomDeviceStrParts[2], randomDeviceStrParts[3]);
        }
        return device;
    }
}

class Device {
    private AndroidVersion[] androidVersions =
            {
                    new AndroidVersion("6.0.1", 23, "MMB29K", "96d9b2f5a6be"),
                    new AndroidVersion("6.0.1", 23, "MMB29Q", "92361f002671"),
                    new AndroidVersion("6.0.1", 23, "MMB29V", "21eeeb9be548"),
                    new AndroidVersion("6.0.1", 23, "MOB30D", "6962d419a262"),

                    new AndroidVersion("6.0.0", 23, "MRA58K", "77f220f68f3f"),
                    new AndroidVersion("6.0.0", 23, "MRA58N", "ac3ef9367eb5"),
                    new AndroidVersion("6.0.0", 23, "MRA58K", "f5698cd8a606"),
                    new AndroidVersion("6.0.0", 23, "MRA58N", "146c149519e1"),

                    new AndroidVersion("5.1.1", 22, "LMY48I", "38113f566857"),
                    new AndroidVersion("5.1.1", 22, "LMY48M", "c70eeff01c6c"),
                    new AndroidVersion("5.1.0", 22, "LMY47D", "3c996a9f9fe0"),
                    new AndroidVersion("5.1.1", 22, "LMY48I", "b5f163e00247"),
                    new AndroidVersion("5.1.1", 22, "LMY48M", "895c98c0ac59"),
                    new AndroidVersion("5.1.0", 22, "LMY47D", "357891fad090"),

                    new AndroidVersion("5.0.1", 21, "LRX22C", "77bca82898a1"),
                    new AndroidVersion("5.0.1", 21, "LRX22C", "7e406c29ecdc"),
                    new AndroidVersion("5.0.1", 21, "LRX22C", "f3c364ea8cc7"),
                    new AndroidVersion("5.0.1", 21, "LRX22C", "81412f4ff53f"),

                    new AndroidVersion("4.4.4", 19, "KTU84P", "75fba0ae8f68"),
                    new AndroidVersion("4.4.2", 19, "KOT49H", "188de71bb310"),
                    new AndroidVersion("4.4.1", 19, "KOT49E", "a7f5e781ab2d"),

                    new AndroidVersion("4.3", 18, "JWR66Y", "fe9aded0e079"),
                    new AndroidVersion("4.3", 18, "JWR66V", "c025a124b640"),
                    new AndroidVersion("4.3", 18, "JWR66N", "ac7adfc5e2d2"),

                    new AndroidVersion("4.2.1", 17, "JOP40D", "439ce37c9226"),
                    new AndroidVersion("4.2.2", 17, "JDQ39", "4f3d72111bdc"),
                    new AndroidVersion("4.1.2", 16, "JZO54K", "f3c364ea8cd0"),
                    new AndroidVersion("4.0.4", 15, "IMM76D", "25ad72111be5"),
                    new AndroidVersion("4.0.1", 14, "ITL41D", "dedac7111b13"),
//                    new AndroidVersion("2.3.4", 10, "GRJ22", "acac711vc13"),
//                    new AndroidVersion("2.3.3", 10, "GRI40", "d721211vbd3"),
//                    new AndroidVersion("2.3", 9, "GRH55", "edac211vbd3"),
//                    new AndroidVersion("2.2.1", 8, "FRG83", "ed9ce31vbd0"),
//                    new AndroidVersion("2.1", 7, "ERD79", "ed9cea345d0")
            };

    private String deviceBrand;
    private String deviceMarketName;
    private String deviceName;
    private String deviceModel;
    private AndroidVersion androidVersion;
    private String buildFingerPrint;
    private String buildDescription;

    public Device() {
        this.deviceBrand = "";
        this.deviceMarketName = "";
        this.deviceName = "";
        this.deviceModel = "";
        this.androidVersion = androidVersions[new Random().nextInt(androidVersions.length)];
        this.buildFingerPrint = "";
        this.buildDescription = "";
    }

    public Device(String deviceBrand, String deviceMarketName, String deviceName, String deviceModel) {
        this.deviceBrand = deviceBrand;
        this.deviceMarketName = deviceMarketName;
        this.deviceName = deviceName;
        this.deviceModel = deviceModel;
        this.androidVersion = androidVersions[new Random().nextInt(androidVersions.length)];
        String buildFingerPrintTmp = this.deviceBrand + "/" + this.deviceName + "/" + this.deviceName + ":" +
                                    this.androidVersion.versionRelease + "/" + this.androidVersion.buildId + "/" +
                                    this.androidVersion.versionIncremental + ":user/release-keys";
        this.buildFingerPrint = buildFingerPrintTmp.replace(' ', '_');
        this.buildDescription = buildFingerPrintTmp.replace('/', ' ');
        Log.d("WBP", "buildFingerPrint: " + this.buildFingerPrint);
        Log.d("WBP", "buildDescription: " + this.buildDescription);
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public String getDeviceMarketName() {
        return deviceMarketName;
    }

    public void setDeviceMarketName(String deviceMarketName) {
        this.deviceMarketName = deviceMarketName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public AndroidVersion getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(AndroidVersion androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getBuildFingerPrint() {
        return buildFingerPrint;
    }

    public void setBuildFingerPrint(String buildFingerPrint) {
        this.buildFingerPrint = buildFingerPrint;
    }

    public String getBuildDescription() {
        return buildDescription;
    }

    public void setBuildDescription(String buildDescription) {
        this.buildDescription = buildDescription;
    }
}

class AndroidVersion {
    public String versionRelease;
    public int versionSDK;
    public String buildId;
    public String versionIncremental;
    public AndroidVersion() {

    }

    public AndroidVersion(String versionRelease, int versionSDK, String buildId, String versionIncremental) {
        this.versionRelease = versionRelease;
        this.versionSDK = versionSDK;
        this.buildId = buildId;
        this.versionIncremental = versionIncremental;
    }
}
