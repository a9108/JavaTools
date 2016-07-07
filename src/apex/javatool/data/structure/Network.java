package apex.javatool.data.structure;

import apex.javatool.console.gui.Describable;
import apex.javatool.io.FileOps;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

public class Network extends Describable {

    private HashMap<Integer, HashSet<Integer>> links = new HashMap<>();
    private HashMap<Integer, String> name = new HashMap<>();

    public Network() {
    }

    public Network(Network netB) {
        links = new HashMap<>();
        for (int i : netB.getNodes())
            for (int j : netB.getLinks(i))
                insert(i, j);
    }

    public Network(int N) {
        links = new HashMap<>();
        for (int i = 0; i < N; i++) links.put(i, new HashSet<>());
    }

    public Network(String path) {
        load(path);
    }

    public String getNodeName(int i) {
        if (!name.containsKey(i)) return null;
        return name.get(i);
    }

    public void order() {
        HashMap<Integer, Integer> rid = new HashMap<>();
        HashMap<Integer, HashSet<Integer>> nlinks = new HashMap<>();
        for (int id : getNodes()) {
            rid.put(id, rid.size());
            nlinks.put(rid.size() - 1, new HashSet<>());
        }
        for (int i : links.keySet())
            for (int j : links.get(i))
                nlinks.get(rid.get(i)).add(rid.get(j));
        links = nlinks;
        HashMap<Integer, String> nname = new HashMap<>();
        for (int i : name.keySet())
            nname.put(rid.get(i), name.get(i));
        name = nname;
    }

    public void setNodeName(int i, String name) {
        this.name.put(i, name);
    }

    public void clear() {
        links.clear();
        name.clear();
    }

    public int size() {
        int sum = 0;
        for (HashSet<Integer> cur : links.values())
            sum += cur.size();
        return sum / 2;
    }

    public int getDegree(int i) {
        if (links.containsKey(i))
            return links.get(i).size();
        return 0;
    }

    public Set<Integer> getNodes() {
        return links.keySet();
    }

    public void insert(int i, int j) {
        if (!links.containsKey(i))
            links.put(i, new HashSet<>());
        links.get(i).add(j);
        if (!links.containsKey(j))
            links.put(j, new HashSet<>());
        links.get(j).add(i);
    }

    public void remove(int i) {
        if (links.containsKey(i)) {
            for (Integer j : links.get(i))
                links.get(j).remove(i);
            links.remove(i);
        }
    }

    public void retainAll(Set<Integer> active) {
        for (int id : new HashSet<>(links.keySet())) {
            if (active.contains(id))
                links.get(id).retainAll(active);
            else links.remove(id);
        }
    }

    public void load(String filename) {
        clear();
        try {
            BufferedReader fin = new BufferedReader(new FileReader(filename));
            for (; ; ) {
                String s = fin.readLine();
                if (s == null)
                    break;
                String[] sep = s.split("\t");
                if (sep.length != 2) continue;
                int i = Integer.valueOf(sep[0]);
                int j = Integer.valueOf(sep[1]);
                insert(i, j);
            }
            fin.close();
        } catch (Exception ex) {
        }
    }

    public void loadLEDA(String filename) {
        clear();
        try {
            BufferedReader fin = new BufferedReader(new FileReader(filename));
            if (!fin.readLine().equals("LEDA.GRAPH")) {
                System.err.print(filename + " is not a valid LEDA graph file.");
                return;
            }
            fin.readLine();
            fin.readLine();
            if (!fin.readLine().equals("-2")) {
                System.err.print(filename + " is not an undirected graph.");
                return;
            }
            int cnt = Integer.valueOf(fin.readLine());
            for (int i = 0; i < cnt; i++) {
                String cur = fin.readLine();
                cur = cur.substring(2, cur.length() - 2);
                name.put(i, cur);
            }
            int cntEdge = Integer.valueOf(fin.readLine());
            for (int i = 0; i < cntEdge; i++) {
                String[] cur = fin.readLine().split(" ");
                insert(Integer.valueOf(cur[0]) - 1, Integer.valueOf(cur[1]) - 1);
            }
            fin.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.print("Error when parsing " + filename);
            return;
        }
    }

    public void saveLEDA(String filename) {
        LinkedList<String> out = new LinkedList<>();
        out.add("LEDA.GRAPH");
        out.add("void");
        out.add("void");
        out.add("-2");
        out.add("" + getNodes().size());
        for (int i = 0; i < getNodes().size(); i++)
            out.add("|{" + (i + 1) + "}|");
        out.add("" + size());
        for (int i : getNodes())
            out.addAll(
                    getLinks(i).stream()
                            .filter(j -> i < j)
                            .map(j -> "" + (i + 1) + " " + (j + 1) + " 0 |{}|")
                            .collect(Collectors.toList()));
        FileOps.SaveList(filename, out);
    }


    public HashSet<Integer> getLinks(int i) {
        if (links.containsKey(i))
            return links.get(i);
        return new HashSet<>();
    }

    @Override
    public void describeContent() {
        show("# Nodes", getNodes().size());
        show("# Edges", size());
    }

    public boolean hasLink(int i, int j) {
        if (links.containsKey(i) && links.get(i).contains(j))
            return true;
        return false;
    }
}
