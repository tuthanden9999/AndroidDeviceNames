package auto.first.blood.changedevice;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.Log;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
public class FakeHardwareInfo {

	public FakeHardwareInfo(LoadPackageParam sharePkgParam){
		FakeBluetooth(sharePkgParam);
		FakeWifi(sharePkgParam);
		FakeCPUFile(sharePkgParam);
		FakeTelephony(sharePkgParam);
		
	}
	
	public static boolean CreatDataCpu(Context context) {
        String str = "/data/data/" + context.getPackageName() + "/cpuinfo";
        String str2 = "/data/data/" + context.getPackageName() + "/version";
        try {
            AssetManager assets = context.getAssets();
            InputStream open = assets.open("cpuinfo");
            OutputStream fileOutputStream = new FileOutputStream(str);
            writeValue(open, fileOutputStream);
            open.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            InputStream open2 = assets.open("version");
            OutputStream fileOutputStream2 = new FileOutputStream(str2);
            writeValue(open2, fileOutputStream2);
            open2.close();
            fileOutputStream2.flush();
            fileOutputStream2.close();
            Sendfile(str, str2);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
	
	private static void writeValue(InputStream inputStream, OutputStream outputStream) {
        try {
            byte[] bArr = new byte[AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT];
            while (true) {
                int read = inputStream.read(bArr);
                if (read != -1) {
                    outputStream.write(bArr, 0, read);
                } else {
                    return;
                }
            }
        } catch (Exception e) {
        }
    }
	
	private static void Sendfile(String str, String str2) {
		Process process = null;
		DataOutputStream os = null;

		try {
			process = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes("mount -o remount,rw -t yaffs2 /dev/block/mtdblock5 /data\n");
			os.flush();
			os.writeBytes("mkdir /data/misc/sys/\n");
			os.flush();
			os.writeBytes("chmod 777 /data/misc/sys/\n");
			os.flush();
			os.writeBytes("mv " + str + " /data/misc/sys\n");
			os.flush();
			os.writeBytes("chmod 444 /data/misc/sys/cpuinfo\n");
			os.flush();
//			os.writeBytes("rm " + str + "\n");
//			os.flush();
			os.writeBytes("mv " + str2 + " /data/misc/sys\n");
			os.flush();
			os.writeBytes("chmod 444 /data/misc/sys/version\n");
			os.flush();
//			os.writeBytes("rm " + str2 + "\n");
//			os.flush();
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
		} catch (Exception e) {
			Log.e("WBP", "Sendfile Error: " + e.getMessage());
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				process.destroy();
			} catch (Exception e) {
				Log.e("WBP", "Sendfile Error: " + e.getMessage());
			}
		}
    }
	
	
	public void FakeCPUFile(LoadPackageParam loadPkgParam){
		try {

			XposedBridge.hookAllConstructors(File.class, new XC_MethodHook() {

				@Override
				protected void beforeHookedMethod(MethodHookParam param)
						throws Throwable {
					// TODO Auto-generated method stub
					super.beforeHookedMethod(param);
					if (param.args.length == 1) {
			            if (param.args[0].equals("/proc/cpuinfo")) {
			            	param.args[0] = "/data/misc/sys/cpuinfo";
							Log.e("WBP", "hookAllConstructors: param.args[0] = " + param.args[0]);
			            }
			            if (param.args[0].equals("/proc/version")) {
			            	param.args[0] = "/data/misc/sys/version";
			            }
			        } else if (param.args.length == 2 && !File.class.isInstance(param.args[0])) {
			            int i = 0;
			            String str = "";
			            while (i < 2) {
			                String stringBuilder;
			                if (param.args[i] != null) {
			                    if (param.args[i].equals("/proc/cpuinfo")) {
			                    	param.args[i] = "/data/misc/sys/cpuinfo";
									Log.e("WBP", "hookAllConstructors: param.args[i] = " + i + "::" + param.args[i]);
			                    }
			                    if (param.args[i].equals("/proc/version")) {
			                    	param.args[i] = "/data/misc/sys/version";
			                    }
			                    stringBuilder = new StringBuilder(String.valueOf(str)).append(param.args[i]).append(":").toString();
			                } else {
			                    stringBuilder = str;
			                }
			                i++;
			                str = stringBuilder;
			            }
			        }
				}
				
			});
			
			XposedHelpers.findAndHookMethod("java.lang.Runtime", loadPkgParam.classLoader, "exec", String[].class, String[].class, File.class, new XC_MethodHook() {

				@Override
				protected void beforeHookedMethod(MethodHookParam param)
						throws Throwable {
					// TODO Auto-generated method stub
					super.beforeHookedMethod(param);
					if (param.args.length == 1) {
			            if (param.args[0].equals("/proc/cpuinfo")) {
			            	param.args[0] = "/data/misc/sys/cpuinfo";
							Log.e("WBP", "java.lang.Runtime: param.args[0] = " + param.args[0]);
			            }
			            if (param.args[0].equals("/proc/version")) {
			            	param.args[0] = "/data/misc/sys/version";
			            }
			        } else if (param.args.length == 2 && !File.class.isInstance(param.args[0])) {
			            int i = 0;
			            String str = "";
			            while (i < 2) {
			                String stringBuilder;
			                if (param.args[i] != null) {
			                    if (param.args[i].equals("/proc/cpuinfo")) {
			                    	param.args[i] = "/data/misc/sys/cpuinfo";
									Log.e("WBP", "java.lang.Runtime: param.args[i] = " + i + "::" + param.args[i]);
			                    }
			                    if (param.args[i].equals("/proc/version")) {
			                    	param.args[i] = "/data/misc/sys/version";
			                    }
			                    stringBuilder = new StringBuilder(String.valueOf(str)).append(param.args[i]).append(":").toString();
			                } else {
			                    stringBuilder = str;
			                }
			                i++;
			                str = stringBuilder;
			            }
			        }
				}
				
			});
		} catch (Exception e) {
            Log.e("WBP", "Fake CPUFile - 1 ERROR: " + e.getMessage());
			//XposedBridge.log("Fake CPUFile - 1 ERROR: " + e.getMessage());
		}
		
		
		try {
            XposedBridge.hookMethod(XposedHelpers.findConstructorExact(ProcessBuilder.class, new Class[]{String[].class}), new XC_MethodHook() {

				@Override
				protected void beforeHookedMethod(MethodHookParam param)
						throws Throwable {
					// TODO Auto-generated method stub
					super.beforeHookedMethod(param);
					if (param.args[0] != null) {
	                    String[] strArr = (String[]) param.args[0];
	                    String str = "";
	                    for (String str2 : strArr) {
	                        str = new StringBuilder(String.valueOf(str)).append(str2).append(":").toString();
	                        if (str2 == "/proc/cpuinfo") {
	                            strArr[1] = "/data/misc/sys/cpuinfo";
								Log.e("WBP", "findConstructorExact: ");
	                        }
	                        if (str2 == "/proc/version") {
	                            strArr[1] = "/data/misc/sys/version";
	                        }
	                    }
	                    param.args[0] = strArr;
	                }
				}
            	
			});
        } catch (Exception e) {
            Log.e("WBP", "Fake CPUFile - 2 ERROR: " + e.getMessage());
        	//XposedBridge.log("Fake CPUFile - 2 ERROR: " + e.getMessage());
        }
		
		try {
			//Pattern.compile("").matcher("");
			
			XposedHelpers.findAndHookMethod("java.util.regex.Pattern", loadPkgParam.classLoader, "matcher", CharSequence.class, new XC_MethodHook() {

				@Override
				protected void beforeHookedMethod(MethodHookParam param)
						throws Throwable {
					// TODO Auto-generated method stub
					super.beforeHookedMethod(param);
					if (param.args.length == 1) {
			            if (param.args[0].equals("/proc/cpuinfo")) {
			            	param.args[0] = "/data/misc/sys/cpuinfo";
							Log.e("WBP", "java.util.regex.Pattern: param.args[0] = " + param.args[0]);
			            }
			            if (param.args[0].equals("/proc/version")) {
			            	param.args[0] = "/data/misc/sys/version";
			            }
			        }
				}
				
			});
			
		} catch (Exception e) {
            Log.e("WBP", "Fake CPU(Pattern) ERROR: " + e.getMessage());
			//XposedBridge.log("Fake CPU(Pattern) ERROR: " + e.getMessage());
		}
	}
	
	
	public void FakeBluetooth(LoadPackageParam loadPkgParam){
		try {
			XposedHelpers.findAndHookMethod("android.bluetooth.BluetoothAdapter", loadPkgParam.classLoader, "getAddress", new XC_MethodHook() {

				@Override
				protected void afterHookedMethod(MethodHookParam param)
						throws Throwable {
					// TODO Auto-generated method stub
					super.afterHookedMethod(param);
					param.setResult(SharedPref.getValue("WifiMAC"));
				}
				
			});
			XposedHelpers.findAndHookMethod("android.bluetooth.BluetoothDevice", loadPkgParam.classLoader, "getAddress", new XC_MethodHook() {

				@Override
				protected void afterHookedMethod(MethodHookParam param)
						throws Throwable {
					// TODO Auto-generated method stub
					//super.afterHookedMethod(param);
					param.setResult(SharedPref.getValue("WifiMAC"));
				}
				
			});
		} catch (Exception e) {
            Log.e("WBP", "Fake Bluetooth ERROR: " + e.getMessage());
			//XposedBridge.log("Fake Bluetooth ERROR: " + e.getMessage());
		}
	}
	
	public void FakeWifi(LoadPackageParam loadPkgParam){
		try {
			XposedHelpers.findAndHookMethod("android.net.wifi.WifiInfo", loadPkgParam.classLoader, "getMacAddress", new XC_MethodHook() {

				@Override
				protected void afterHookedMethod(MethodHookParam param)
						throws Throwable {
					// TODO Auto-generated method stub
					super.afterHookedMethod(param);
					param.setResult(SharedPref.getValue("WifiMAC"));
				}
				
			});
			XposedHelpers.findAndHookMethod("android.net.wifi.WifiInfo", loadPkgParam.classLoader, "getSSID", new XC_MethodHook() {

				@Override
				protected void afterHookedMethod(MethodHookParam param)
						throws Throwable {
					// TODO Auto-generated method stub
					super.afterHookedMethod(param);
					param.setResult(SharedPref.getValue("WifiName"));
				}
				
			});
			XposedHelpers.findAndHookMethod("android.net.wifi.WifiInfo", loadPkgParam.classLoader, "getBSSID", new XC_MethodHook() {

				@Override
				protected void afterHookedMethod(MethodHookParam param)
						throws Throwable {
					// TODO Auto-generated method stub
					super.afterHookedMethod(param);
					param.setResult(SharedPref.getValue("BSSID"));
				}
				
			});
		} catch (Exception e) {
            Log.e("WBP", "Fake Wifi ERROR: " + e.getMessage());
			//XposedBridge.log("Fake Wifi ERROR: " + e.getMessage());
		}
	}
	
	public void FakeTelephony(LoadPackageParam loadPkgParam){
		String TelePhone = "android.telephony.TelephonyManager";
		HookTelephony(TelePhone, loadPkgParam, "getDeviceId", SharedPref.getValue("IMEI"));
		HookTelephony(TelePhone, loadPkgParam, "getSubscriberId", SharedPref.getValue("IMSI"));
		HookTelephony(TelePhone, loadPkgParam, "getLine1Number", SharedPref.getValue("PhoneNumber"));
		HookTelephony(TelePhone, loadPkgParam, "getSimSerialNumber", SharedPref.getValue("SimSerial"));
		HookTelephony(TelePhone, loadPkgParam, "getNetworkOperator", SharedPref.getValue("CarrierCode"));
		HookTelephony(TelePhone, loadPkgParam, "getNetworkOperatorName", SharedPref.getValue("Carrier"));
		HookTelephony(TelePhone, loadPkgParam, "getSimOperator", SharedPref.getValue("CarrierCode"));
		HookTelephony(TelePhone, loadPkgParam, "getSimOperatorName", SharedPref.getValue("Carrier"));
		HookTelephony(TelePhone, loadPkgParam, "getNetworkCountryIso", SharedPref.getValue("CountryCode"));
		HookTelephony(TelePhone, loadPkgParam, "getSimCountryIso", SharedPref.getValue("CountryCode"));
		//HookTelephony(TelePhone, loadPkgParam, "getDeviceId", SharedPref.getValue("IMEI"));
		try {
			XposedHelpers.findAndHookMethod(System.class, "getProperty", String.class, new XC_MethodHook() {

				@Override
				protected void afterHookedMethod(MethodHookParam param)
						throws Throwable {
					// TODO Auto-generated method stub
					super.afterHookedMethod(param);
					if (param.args[0] == "os.version") {
						param.setResult(SharedPref.getValue("OSVersion"));
			        }
			        if (param.args[0] == "os.arch") {
			        	param.setResult(SharedPref.getValue("OSArch"));
			        }
			        if (param.args[0] == "os.name") {
			        	param.setResult(SharedPref.getValue("OSName"));
			        }
				}
				
			});
		} catch (Exception e) {
            Log.e("WBP", "Fake OS ERROR: " + e.getMessage());
			//XposedBridge.log("Fake OS ERROR: " + e.getMessage());
		}
	}
	private void HookTelephony(String hookClass, LoadPackageParam loadPkgParam, String funcName, final String value){
		try {
			XposedHelpers.findAndHookMethod(hookClass, loadPkgParam.classLoader, funcName, new XC_MethodHook() {

				@Override
				protected void afterHookedMethod(MethodHookParam param)
						throws Throwable {
					// TODO Auto-generated method stub
					super.afterHookedMethod(param);		
					param.setResult(value);				
				}
				
			});
		} catch (Exception e) {
            Log.e("WBP", "Fake " + funcName + " ERROR: " + e.getMessage());
			//XposedBridge.log("Fake " + funcName + " ERROR: " + e.getMessage());
		}
	}
}
