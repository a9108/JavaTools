package apex.javatool.algorithm;

import apex.javatool.data.structure.Tuple;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Xuezhi Cao
 * @contact cxz@apex.sjtu.edu.cn
 * Created on 10/10/16.
 */
public class KMMatching {
    private double w[][], slack[], x[], y[];
    private int prev_x[], prev_y[], son_y[], par[];
    private int lx, ly, N;
    private double inf = 1e20;

    public HashMap<Integer, Integer> align(HashSet<Integer> A, HashSet<Integer> B, HashMap<Tuple<Integer, Integer>, Double> weight) {
        HashMap<Integer, Integer> idA = new HashMap<>();
        A.forEach(i -> idA.put(i, idA.size()));
        HashMap<Integer, Integer> idB = new HashMap<>();
        B.forEach(i -> idB.put(i, idB.size()));
        HashMap<Integer, Integer> ridA = new HashMap<>();
        idA.forEach((a, b) -> ridA.put(b, a));
        HashMap<Integer, Integer> ridB = new HashMap<>();
        idB.forEach((a, b) -> ridB.put(b, a));

        N = A.size();
        w = new double[N][N];
        weight.forEach((pair, v) -> w[idA.get(pair.getFirst())][idB.get(pair.getSecond())] = v);
        slack = new double[N];
        x = new double[N];
        y = new double[N];
        prev_x = new int[N];
        prev_y = new int[N];
        son_y = new int[N];
        par = new int[N];

        run();

        HashMap<Integer, Integer> align = new HashMap<>();
        for (int i = 0; i < N; i++)
            align.put(ridA.get(son_y[i]), ridB.get(i));
        return align;
    }

    private double run() {
        double m;
        for (int i = 0; i < N; i++) {
            son_y[i] = -1;
            y[i] = 0;
        }
        for (int i = 0; i < N; i++) {
            x[i] = 0;
            for (int j = 0; j < N; j++)
                x[i] = Math.max(x[i], w[i][j]);
        }
        boolean flag;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                prev_x[j] = prev_y[j] = -1;
                slack[j] = inf;
            }
            prev_x[i] = -2;
            if (find(i)) continue;
            flag = false;
            while (!flag) {
                m = inf;
                for (int j = 0; j < N; j++)
                    if (prev_y[j] == -1)
                        m = Math.min(m, slack[j]);
                for (int j = 0; j < N; j++) {
                    if (prev_x[j] != -1)
                        x[j] -= m;
                    if (prev_y[j] != -1)
                        y[j] += m;
                    else
                        slack[j] -= m;
                }
                for (int j = 0; j < N; j++)
                    if (prev_y[j] == -1 && slack[j] != 0) {
                        prev_y[j] = par[j];
                        if (son_y[j] == -1) {
                            adjust(j);
                            flag = true;
                            break;
                        }
                        prev_x[son_y[j]] = j;
                        if (find(son_y[j])) {
                            flag = true;
                            break;
                        }
                    }
            }
        }
        double ans = 0;
        for (int i = 0; i < N; i++)
            ans += w[son_y[i]][i];
        return ans;
    }

    private void adjust(int v) {
        son_y[v] = prev_y[v];
        if (prev_x[son_y[v]] != -2)
            adjust(prev_x[son_y[v]]);
    }

    private boolean find(int v) {
        int i;
        for (i = 0; i < N; i++)
            if (prev_y[i] == -1) {
                if (slack[i] > x[v] + y[i] - w[v][i]) {
                    slack[i] = x[v] + y[i] - w[v][i];
                    par[i] = v;
                }
                if (x[v] + y[i] == w[v][i]) {
                    prev_y[i] = v;
                    if (son_y[i] == -1) {
                        adjust(i);
                        return true;
                    }
                    if (prev_x[son_y[i]] != -1)
                        continue;
                    prev_x[son_y[i]] = i;
                    if (find(son_y[i]))
                        return true;
                }
            }
        return false;
    }
}