package apex.javatool.platform.system;

import apex.javatool.io.FileOps;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class SystemCall {
    public static String execute(String cmd) {
        String res = "";
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader errReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            BufferedReader inpReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String line = "";
            while ((line = errReader.readLine()) != null)
                System.out.println(line + "\r");
            while ((line = inpReader.readLine()) != null) {
                if (res.length() > 0)
                    res += "\n";
                res += line;
            }
            p.waitFor();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return res;
    }

    public static void execute(String cmd, String redirect) {
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            LinkedList<String> data = new LinkedList<String>();
            while ((line = reader.readLine()) != null)
                data.add(line);
            FileOps.SaveFile(redirect, data);
            p.waitFor();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
