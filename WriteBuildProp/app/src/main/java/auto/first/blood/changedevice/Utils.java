package auto.first.blood.changedevice;

import android.util.Log;

import java.io.InputStream;
import java.util.Random;
import java.util.UUID;

public class Utils {
	private static int EMAILS_MAX = 10;

//	public static String randomnetworktype() {
//		String[] networktypes = new String[]{"LTE","UMTS","IDEN","HSUPA","HSPA","HSDPA","GPRS","EVDO","EDGE","CDMA"};
//		Random rnd = new Random();
//		String result = networktypes[rnd.nextInt(networktypes.length)];
//		return result;
//	}
//
//	public static String randomNum(int n)
//	{
//		String res = "";
//		Random rnd = new Random();
//		for (int i = 0; i < n; i++)
//		{
//			res = res + rnd.nextInt(10);
//		}
//		return res;
//	}
//
//	public static String randomPhone()
//	{
//		/** 前锟斤拷为 */
//		String head[] = { "+8613", "+8615", "+8617", "+8618", "+8616" };
//		Random rnd = new Random();
//		String res = head[rnd.nextInt(head.length)];
//		for (int i = 0; i < 9; i++)
//		{
//			res = res + rnd.nextInt(10);
//		}
//		return res;
//	}
//
//	public static String randomMac()
//	{
//		String chars = "abcde0123456789";
//		String res = "";
//		Random rnd = new Random();
//		int leng = chars.length();
//		for (int i = 0; i < 17; i++)
//		{
//			if (i % 3 == 2)
//			{
//				res = res + ":";
//			} else
//			{
//				res = res + chars.charAt(rnd.nextInt(leng));
//			}
//
//		}
//		return res;
//	}
//
//	public static String randomMac1()
//	{
//		String chars = "ABCDE0123456789";
//		String res = "";
//		Random rnd = new Random();
//		int leng = chars.length();
//		for (int i = 0; i < 17; i++)
//		{
//			if (i % 3 == 2)
//			{
//				res = res + ":";
//			} else
//			{
//				res = res + chars.charAt(rnd.nextInt(leng));
//			}
//
//		}
//		return res;
//	}
//
//	public static String randomIP() {
//		String chars = "0123456789";
//		String res = "";
//		Random rnd = new Random();
//		int leng = chars.length();
//		for (int i = 0; i < 4; i++)
//		{
//			int length = rnd.nextInt(3);
//			if(length == 0) {
//				length = 1;
//			}
//			String temp = "";
//			for(int j=0;j<length;j++) {
//				temp += chars.charAt(rnd.nextInt(leng));
//			}
//			int temNum = Integer.parseInt(temp);
//			res += temNum+".";
//		}
//		return res;
//	}
//
//	public static String randomABC(int n)
//	{
//		String chars = "abcdef0123456789";
//		String res = "";
//		Random rnd = new Random();
//		int leng = chars.length();
//		for (int i = 0; i < n; i++)
//		{
//			res = res + chars.charAt(rnd.nextInt(leng));
//
//		}
//		return res;
//	}
//
//	public static String randomChar() {
//		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
//		Random rand = new Random();
//		int n = rand.nextInt(26);
//		if(n < 6) {
//			n = 6;
//		}
//		int leng = chars.length();
//		String res = "";
//		for (int i = 0; i < n; i++)
//		{
//			res = res + chars.charAt(rand.nextInt(leng));
//		}
//		return res;
//
//	}
//
//	public static String randomISP() {
//		String[] isps = new String[]{"Bright House Networks","Armstrong Zoom","AT&T Yahoo! High Speed Internet"};
//		Random rnd = new Random();
//		return isps[rnd.nextInt(isps.length)];
//    }
//
//    public static String randomUUID() {
//        UUID uuid = UUID.randomUUID();
//        return uuid.toString();
//    }

	public static String getEmail()
	{
		String email = "tuthanden0000@gmail.com";
		int randomNum = new Random().nextInt(EMAILS_MAX);
		try {
			InputStream inputStream = DeviceName.class.getResourceAsStream("/res/raw/email.txt");
			email = TextFileHandle.readALineInFile(randomNum, inputStream);
		} catch (Exception e) {
			Log.e("WBP", "Error read file: " + e.toString());
		}
		Log.d("WBP", "randomNum: " + randomNum);
		Log.d("WBP", "email: " + email);
		return email == null ? "tuthanden0000@gmail.com" : email;
	}

	public static String getGLRenderer()
	{
		String[] adrendo = { "405", "420", "305", "320", "330" };

		Random rnd = new Random();
		return "Adreno (TM) " + adrendo[rnd.nextInt(adrendo.length)];
	}

	public static String getDPI()
	{
		String[] dpi = { "160", "240", "320", "480", "640"};
		Random rnd = new Random();

		return dpi[rnd.nextInt(dpi.length)];
	}

	public static String getLevel()
	{
		Random rnd = new Random();
		return rnd.nextInt((100 - 20) + 1) + 20 + "";
	}

	public static String getTemp()
	{
		Random rnd = new Random();
		return rnd.nextInt((500 - 250) + 1) + 250 + "";
	}

	public static String getLAT()
	{
		Random rnd = new Random();
		String lat = String.format("%.5f", (rnd.nextDouble() * 180) - 90.0);
		return lat;
	}

	public static String getLONG()
	{
		Random rnd = new Random();
		String lng = String.format("%.5f", (rnd.nextDouble() * 360) - 180.0);
		return lng;
	}

	public static String getALT()
	{
		Random rnd = new Random();
		String alt = String.format("%.5f", (rnd.nextDouble() * 2.0) * 686.0);
		return alt;
	}

	public static String getSpeed()
	{
		Random rnd = new Random();
		String speed = String.format("%.1f", (rnd.nextDouble() * 10.0));
		return speed;
	}

	public static String getAndroidID()
	{
		String digitAndChar = "0123456789abcdef";
		String androidID = "";
		Random rnd = new Random(); // Random dữ liệu
		while (androidID.length() < 16)
		{
			androidID = androidID + digitAndChar.charAt(rnd.nextInt(16));
		}
		return androidID;
	}

	public static String getMACAdd()
	{
		StringBuilder builderMACAdd = new StringBuilder();
		Random rnd = new Random();
		int i = 0;
		for (int i2 = 0; i2 < 6; i2++)
		{
			if (i2 != 0)
			{
				builderMACAdd.append(':');
			}
			i = rnd.nextInt(256);

			if (i2 == 0)
			{
				i &= 252;
			}
			builderMACAdd.append(String.format("%03X", i & 0xFFFFF).substring(1));

		}

		return builderMACAdd.toString().toUpperCase();
	}

	public static void setSharedPrefs()
	{
        SharedPref.setSharedPref("AndroidVer", "");
        SharedPref.setSharedPref("API", "");
        SharedPref.setSharedPref("PhoneNumber", "");
        SharedPref.setSharedPref("WifiName", "");
        SharedPref.setSharedPref("WifiMAC", getMACAdd());
        SharedPref.setSharedPref("BSSID", "");
        SharedPref.setSharedPref("AndroidID", getAndroidID());
        SharedPref.setSharedPref("GoogleAdsID", "");
        SharedPref.setSharedPref("AndroidSerial", "");
        SharedPref.setSharedPref("IMEI", "");
        SharedPref.setSharedPref("IMSI", "");
        SharedPref.setSharedPref("SimSerial", "");
        SharedPref.setSharedPref("Carrier", "");
        SharedPref.setSharedPref("CarrierCode", "");
        SharedPref.setSharedPref("CountryCode", "");
        SharedPref.setSharedPref("Lat", getLAT());
        SharedPref.setSharedPref("Long", getLONG());
        SharedPref.setSharedPref("Alt", getALT());
        SharedPref.setSharedPref("Speed", getSpeed());
        SharedPref.setSharedPref("OSName", "Linux");
        SharedPref.setSharedPref("OSArch", "armv7l");
        SharedPref.setSharedPref("GLVendor", "Qualcomm");
        SharedPref.setSharedPref("GLRenderer", getGLRenderer());
        SharedPref.setSharedPref("OSVersion", "3.4.0-gd59db4e");
        SharedPref.setSharedPref("Level", getLevel());
        SharedPref.setSharedPref("Temp", getTemp());
        SharedPref.setSharedPref("HideRootPackge", "");
        SharedPref.setSharedPref("FakeEmailPackge", "");
        SharedPref.setSharedPref("DPI", getDPI());
        SharedPref.setSharedPref("Email", getEmail());
        SharedPref.setSharedPref("Manufacture", "");
        SharedPref.setSharedPref("M_ID", "");
        SharedPref.setSharedPref("MODEL", "");
        SharedPref.setSharedPref("BRAND", "");
        SharedPref.setSharedPref("NAME", "");
        SharedPref.setSharedPref("DEVICE", "");
        SharedPref.setSharedPref("BOARD", "");
        SharedPref.setSharedPref("ABI", "");
        SharedPref.setSharedPref("ABI2", "");
        SharedPref.setSharedPref("DESCRIPTION", "");
        SharedPref.setSharedPref("FINGERPRINT", "");
        SharedPref.setSharedPref("BaseBand", "");
        SharedPref.setSharedPref("BOOTLOADER", "");
        SharedPref.setSharedPref("ID", "");
        SharedPref.setSharedPref("DISPLAY", "");
        SharedPref.setSharedPref("UserAgent", "");
	}
}
