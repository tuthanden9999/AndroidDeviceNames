package auto.first.blood.changedevice;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import android.content.ContentResolver;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES10;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class MeXposedHook implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(LoadPackageParam sharePkgParam) throws Throwable {
        // TODO Auto-generated method stub

        Log.d("WBP","in handleLoadPackage");
        if(sharePkgParam.packageName.startsWith("com.android") ||
                sharePkgParam.packageName.startsWith("com.google") ||
                sharePkgParam.packageName.startsWith("com.bluestacks")) {
            return;
        }
        switch (sharePkgParam.packageName) {
            case "":
            case "android":
            case "com.cyanogenmod.trebuchet":
            case "auto.first.blood.changedevice.MeXposedHook":
			case "de.robv.android.xposed.installer":
                return;
        }

//		Log.d("WBP","android.opengl.GLES10.glGetString = " + android.opengl.GLES10.glGetString(GLES10.GL_VENDOR));
//        Log.d("WBP","android.opengl.GLES20.glGetString = " + android.opengl.GLES20.glGetString(GLES20.GL_VENDOR));
//        Log.d("WBP","android.opengl.GLES30.glGetString = " + android.opengl.GLES30.glGetString(GLES30.GL_VENDOR));

		new FakeEmail().fakeGmail(sharePkgParam);
		//new FakeOpenGL().FakeDisplay(sharePkgParam);
        new FakeBattery().fakePinStt(sharePkgParam);
        //new FakeCPU(sharePkgParam);
        new FakeRAM(sharePkgParam);
		//new FakeHardwareInfo(sharePkgParam);
		//new FakeBuildInfo(sharePkgParam);
		new RootCloak().handleLoadPackage(sharePkgParam);
    }

	//private BuildPropFileHandle bpfh = new BuildPropFileHandle();
	public void FakeBuildInfo(LoadPackageParam lpparam) throws Throwable {
		
		try
		{

            if(lpparam.packageName.startsWith("com.android") ||
                    lpparam.packageName.startsWith("com.google") ||
                    lpparam.packageName.startsWith("com.bluestacks")) {
                return;
            }
            switch (lpparam.packageName) {
                case "":
                case "android":
                case "com.cyanogenmod.trebuchet":
                case "auto.first.blood.changedevice.MeXposedHook":
                    return;
            }
			Log.d("WBP","handleLoadPackage " + lpparam.packageName);
            //changeDevice();
//			HookMethod(TelephonyManager.class.getName(), lpparam.classLoader, "getDeviceId", Utils.randomNum(15));
//			HookMethod(TelephonyManager.class.getName(), lpparam.classLoader, "getSubscriberId", Utils.randomNum(15));
//			HookMethod(TelephonyManager.class.getName(), lpparam.classLoader, "getLine1Number", Utils.randomPhone());
////			HookMethod(TelephonyManager.class.getName(), lpparam.classLoader, "getNetworkOperatorName", Utils.randomISP());
//			HookMethod(TelephonyManager.class.getName(), lpparam.classLoader, "getNetworkType", Utils.randomnetworktype());
//			HookMethod(WifiInfo.class.getName(), lpparam.classLoader, "getMacAddress",Utils.randomMac());
//			HookMethod(WifiInfo.class.getName(), lpparam.classLoader, "getSSID", Utils.randomChar());
////			HookMethod(WifiInfo.class.getName(),lpparam.classLoader,"getIpAddress",pre.getInt(MainActivity.IP,0));
//			HookMethod(BluetoothAdapter.class.getName(), lpparam.classLoader, "getAddress", Utils.randomMac());
//			HookMethodForAndroidID(android.provider.Settings.Secure.class.getName(), lpparam.classLoader, "getString", Utils.randomABC(16));
//			XposedHelpers.findField(android.os.Build.class, "SERIAL").set(null, Utils.randomNum(19)+"a");

			Device device = new Device();
			Log.d("WBP", "MODEL: " + device.getDeviceName().deviceModel);

			XposedHelpers.findField(android.os.Build.class, "MODEL").set(null, device.getDeviceName().deviceModel);
			XposedHelpers.findField(android.os.Build.class, "DEVICE").set(null, device.getDeviceName().deviceName);
			XposedHelpers.findField(android.os.Build.class, "BRAND").set(null, device.getDeviceName().deviceBrand);
			XposedHelpers.findField(android.os.Build.class, "MANUFACTURER").set(null, device.getDeviceName().deviceBrand);
			XposedHelpers.findField(android.os.Build.class, "PRODUCT").set(null, device.getDeviceName().deviceName);

			XposedHelpers.findField(android.os.Build.class, "ID").set(null, device.getAndroidVersion().buildId);
			XposedHelpers.findField(android.os.Build.class, "FINGERPRINT").set(null, device.getBuildFingerPrint());
			XposedHelpers.findField(android.os.Build.class, "DISPLAY").set(null, device.getAndroidVersion().buildId + "." + device.getAndroidVersion().versionIncremental);
			XposedHelpers.findField(android.os.Build.VERSION.class, "RELEASE").set(null, device.getAndroidVersion().versionRelease);
			XposedHelpers.findField(android.os.Build.VERSION.class, "SDK").set(null, device.getAndroidVersion().versionSDK + "");
			XposedHelpers.findField(android.os.Build.VERSION.class, "INCREMENTAL").set(null, device.getAndroidVersion().versionIncremental);
			Log.d("WBP", "SDK: " + device.getAndroidVersion().versionSDK);
			//bpfh.writeBuildPropFile(device);
		} catch (Throwable e)
		{
			Log.d("WBP","failed to change device: " + e.getMessage());
		}
		
	}
	
	public void HookMethod(String className,ClassLoader classLoader,String method,
			final Object result)
	{
		findAndHookMethod(className, classLoader, method, new XC_MethodHook() {

			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				param.setResult(result);
			}
			
		});
	}

	public void HookMethodForAndroidID(String className,ClassLoader classLoader,String method,
						   final Object result)
	{
		findAndHookMethod(className, classLoader, method, ContentResolver.class, String.class, new XC_MethodHook() {

			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				param.setResult(result);
			}

		});
	}
}
