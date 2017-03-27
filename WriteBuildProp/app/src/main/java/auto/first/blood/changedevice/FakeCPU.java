package auto.first.blood.changedevice;

import android.util.Log;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.XposedHelpers.ClassNotFoundError;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class FakeCPU {
	public FakeCPU(LoadPackageParam sharePkgParam){
		CPUFreg(sharePkgParam);
		CPUCore(sharePkgParam);
	}
	
	public void CPUFreg(LoadPackageParam loadPkgParam){
		try {
			XposedHelpers.findAndHookMethod("com.blizzard.wtcg.hearthstone.MinSpecCheck", loadPkgParam.classLoader, "getMaxCPUFreqMHz", new XC_MethodHook() {

				@Override
				protected void afterHookedMethod(MethodHookParam param)
						throws Throwable {
					// TODO Auto-generated method stub
					super.afterHookedMethod(param);
					param.setResult(2);
					Log.d("WBP", "CPUFreg: " + param.getResult().toString());
				}
				
			});
		} catch (ClassNotFoundError e) {
			//XposedBridge.log("Fake CPUFreg ERROR: " + e.getMessage());
			Log.e("WBP", "Fake CPUFreg ERROR: " + e.getMessage());
		}
		
	}
	public void CPUCore(LoadPackageParam loadPkgParam){
		try {
			XposedHelpers.findAndHookMethod("com.blizzard.wtcg.hearthstone.MinSpecCheck", loadPkgParam.classLoader, "getCPUNumCores", new XC_MethodHook() {

				@Override
				protected void afterHookedMethod(MethodHookParam param)
						throws Throwable {
					// TODO Auto-generated method stub
					super.afterHookedMethod(param);
					param.setResult(4);
					Log.d("WBP", "CPUCore: " + param.getResult().toString());
				}
				
			});
		} catch (ClassNotFoundError e) {
			Log.e("WBP", "Fake CPUCore ERROR: " + e.getMessage());
			//XposedBridge.log("Fake CPUCore ERROR: " + e.getMessage());
		}
		
	}
}
