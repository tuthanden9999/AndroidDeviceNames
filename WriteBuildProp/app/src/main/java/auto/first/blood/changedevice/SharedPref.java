package auto.first.blood.changedevice;

import android.content.SharedPreferences;

import de.robv.android.xposed.XSharedPreferences;

public class SharedPref {
    private static SharedPreferences sharedPref;
    private static XSharedPreferences xSharedPref;

    public static SharedPreferences getSharedPref()
    {
        if(sharedPref == null)
        {
            sharedPref = MainActivity.getAppContext().getSharedPreferences(Common.PREFS_FILE, 1);
        }
        return sharedPref;
    }

    public static XSharedPreferences getXSharedPref() {
        if (xSharedPref != null) {
            xSharedPref.reload();
            return xSharedPref;
        }
        xSharedPref = new XSharedPreferences(Common.PACKAGE_NAME, Common.PREFS_FILE);
        return xSharedPref;
    }
    
    public static String getValue(String key) {
    	String value = "";
    	try {
    		value = getXSharedPref().getString(key, null);
    	} catch (Exception e) {
        }
    	return value;
    }

    public static void setSharedPref(String key, String value) {
        try {
            getSharedPref().edit().putString(key, value).commit();
        } catch (Exception e) {
        }
    }
}
