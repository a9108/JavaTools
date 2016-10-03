package apex.javatool.io;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FileOps {

    public static void remove(String dir) {
        File f = new File(dir);
        if (f.isDirectory()) {
            if (!dir.endsWith("/")) dir += "/";
            for (String s : f.list())
                remove(dir + s);
            f.delete();
        } else {
            f.delete();
        }
    }

    public static boolean exist(String dir) {
        File f = new File(dir);
        return f.exists();
    }

    public static String LoadFile(String dir, String encoding) {
        LinkedList<String> res = LoadFilebyLine(dir, encoding);
        StringBuilder sb = new StringBuilder();
        for (String q : res)
            sb.append(q + "\n");
        return sb.toString();
    }

    public static String LoadFile(String dir) {
        LinkedList<String> res = LoadFilebyLine(dir);
        StringBuilder sb = new StringBuilder();
        for (String q : res)
            sb.append(q + "\n");
        return sb.toString();
    }

    public static <T> void SaveList(String dir, LinkedList<T> data) {
        LinkedList<String> outdata = new LinkedList<String>();
        for (T item : data)
            outdata.add(item.toString());
        SaveFile(dir, outdata);
    }

    public static LinkedList<String> LoadFilebyLine(String dir) {
        LinkedList<String> res = new LinkedList<String>();
        try {
            BufferedReader fin = new BufferedReader(new FileReader(dir));
            for (; ; ) {
                String s = fin.readLine();
                if (s == null)
                    break;
                res.add(s);
            }
            fin.close();
        } catch (Exception ex) {
        }
        return res;
    }

    public static LinkedList<String> LoadFilebyLine(String dir, String encoding) {
        LinkedList<String> res = new LinkedList<String>();
        try {
            InputStreamReader read = new InputStreamReader(new FileInputStream(new File(dir)), encoding);
            BufferedReader reader = new BufferedReader(read);
            String line;
            while ((line = reader.readLine()) != null) {
                res.add(line);
            }
            read.close();
        } catch (Exception ex) {
        }
        return res;
    }

    public static HashMap<String, Integer> LoadDiction(String dir) {
        HashMap<String, Integer> res = new HashMap<String, Integer>();
        try {
            List<String> lines = LoadFilebyLine(dir);
            for (String line : lines) {
                String[] sep = line.split("\t");
                res.put(sep[0], Integer.valueOf(sep[1]));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return res;
    }

    public static HashMap<String, String> LoadDictionSS(String dir) {
        HashMap<String, String> res = new HashMap<String, String>();
        try {
            List<String> lines = LoadFilebyLine(dir);
            for (String line : lines) {
                String[] sep = line.split("\t");
                if (sep.length == 2)
                    res.put(sep[0], sep[1]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return res;
    }

    public static HashMap<Integer, String> LoadDictionIS(String dir) {
        HashMap<Integer, String> res = new HashMap<Integer, String>();
        try {
            List<String> lines = LoadFilebyLine(dir);
            for (String line : lines) {
                String[] sep = line.split("\t");
                if (sep.length == 2)
                    res.put(Integer.valueOf(sep[0]), sep[1]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return res;
    }

    public static HashMap<Integer, Integer> LoadDictionII(String dir) {
        HashMap<Integer, Integer> res = new HashMap<Integer, Integer>();
        try {
            List<String> lines = LoadFilebyLine(dir);
            for (String line : lines) {
                String[] sep = line.split("\t");
                if (sep.length == 2)
                    res.put(Integer.valueOf(sep[0]), Integer.valueOf(sep[1]));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return res;
    }

    public static void SaveFile(String dir, String content) {
        try {
            BufferedWriter fout = new BufferedWriter(new FileWriter(dir));
            fout.write(content);
            fout.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void SaveFile(String dir, List<String> content) {
        try {
            BufferedWriter fout = new BufferedWriter(new FileWriter(dir));
            for (String s : content)
                fout.write(s + "\n");
            fout.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static <A extends Object, B extends Object> void SaveFile(String dir, Map<A, B> dict) {
        try {
            BufferedWriter fout = new BufferedWriter(new FileWriter(dir));
            for (Object s : dict.keySet())
                fout.write(s + "\t" + dict.get(s) + "\n");
            fout.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void createDir(String dir) {
        try {
            File cur = new File(dir);
            cur.mkdir();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void SaveFile(String dir, double[][] data) {
        LinkedList<String> sdata = new LinkedList<String>();
        for (int i = 0; i < data.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < data[i].length; j++)
                sb.append(data[i][j] + "\t");
            sdata.add(sb.toString());
        }
        SaveFile(dir, sdata);
    }

    public static <A extends Object, B extends Object> void SaveCSV(String dir, Map<A, B> dict) {
        try {
            BufferedWriter fout = new BufferedWriter(new FileWriter(dir));
            for (Object s : dict.keySet())
                fout.write(s + "," + dict.get(s) + "\n");
            fout.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
