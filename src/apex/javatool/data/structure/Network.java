package apex.javatool.data.structure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Network {
    private HashMap<Integer, HashSet<Integer>> links = new HashMap<>();

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
        int cnt = 0;
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

    public HashSet<Integer> getLinks(int i) {
        if (links.containsKey(i))
            return links.get(i);
        return new HashSet<>();
    }
}
