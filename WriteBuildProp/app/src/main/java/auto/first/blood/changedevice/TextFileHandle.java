package auto.first.blood.changedevice;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 3/8/2017.
 */

public class TextFileHandle {
    public static void writeToFile(List<String> lines, String fileName, Context context) {
        try {
            File tmpFile = new File(fileName);
            FileWriter fw = new FileWriter(tmpFile);
            for(String line: lines) {
                fw.write(line + "\n");
                Log.e("WBP", "line: " + line);
            }

            fw.close();
        }
        catch (IOException e) {
            Log.e("WBP", "File write failed: " + e.toString());
        }
    }

    public static List<String> readFromFile(String fileName, Context context) {

        List<String> lines = new ArrayList<>();

        try {
            File tmpFile = new File(fileName);
            Reader fr = new FileReader(tmpFile);
            BufferedReader br = new BufferedReader(fr);

            while (br.ready()) {
                String expectedReceiveString = br.readLine().trim();
                if(!expectedReceiveString.equals("")) {
                    lines.add(expectedReceiveString);
                    Log.e("WBP", "expectedReceiveString: " + expectedReceiveString);
                }
            }

            br.close();
            fr.close();
        }
        catch (FileNotFoundException e) {
            Log.e("WBP", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("WBP", "Can not read file: " + e.toString());
        }

        return lines;
    }
}
