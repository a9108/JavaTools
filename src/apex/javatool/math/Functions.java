package apex.javatool.math;

/**
 * @author Xuezhi Cao
 * @contact cxz@apex.sjtu.edu.cn
 * Created on 6/11/16.
 */
public class Functions {
    public static double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }
}
