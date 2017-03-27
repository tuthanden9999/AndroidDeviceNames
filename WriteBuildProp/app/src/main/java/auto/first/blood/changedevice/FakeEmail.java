package auto.first.blood.changedevice;

import android.accounts.Account;
import android.util.Log;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class FakeEmail {

	public void fakeGmail(final LoadPackageParam loadPkgParam){
		try {
			XposedHelpers.findAndHookMethod("android.accounts.AccountManager", loadPkgParam.classLoader, "getAccounts", new XC_MethodHook() {

				@Override
				protected void beforeHookedMethod(MethodHookParam param)
						throws Throwable {
					// TODO Auto-generated method stub
					super.afterHookedMethod(param);
					if( ! loadPkgParam.packageName.startsWith("android.google")) {
						param.setResult(new Account[]{new Account(SharedPref.getValue("Email"), "com.google")});
			        }
					Log.d("WBP", "Email: " + SharedPref.getValue("Email"));
				}
				
			});
			XposedHelpers.findAndHookMethod("android.accounts.AccountManager", loadPkgParam.classLoader, "getAccountsByType", String.class, new XC_MethodHook() {

				@Override
				protected void beforeHookedMethod(MethodHookParam param)
						throws Throwable {
					// TODO Auto-generated method stub
					super.afterHookedMethod(param);
					if( ! loadPkgParam.packageName.startsWith("android.google")) {
						param.setResult(new Account[]{new Account(SharedPref.getValue("Email"), "com.google")});
			        }
					Log.d("WBP", "Email: " + SharedPref.getValue("Email"));
				}
				
			});
		} catch (Exception e) {
			Log.e("WBP", "Fake Email ERROR: " + e.getMessage());
			//XposedBridge.log("Fake Email ERROR: " + e.getMessage());
		}
		
	}
}
