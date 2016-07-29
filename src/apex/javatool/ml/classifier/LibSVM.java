package apex.javatool.ml.classifier;

import apex.javatool.data.structure.Feature;
import apex.javatool.data.structure.SparseFeature;
import apex.javatool.data.structure.Tuple;
import apex.javatool.io.FileOps;
import apex.javatool.platform.system.SystemCall;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class LibSVM extends Classifier {
    private static int synID = 1001;
    private static int myID;
    private static Random random = new Random();
    private String SVMDir;
    private String workspace;
    private String trainFile;
    private String trainScaleFile;
    private String scaleFile;
    private String modelFile;

    public LibSVM(String dir, String workspace) {
        myID = synID++;
        SVMDir = dir;
        this.workspace = workspace;
        trainFile = workspace + "train." + myID + ".svm";
        trainScaleFile = workspace + "train." + myID + ".svm.scale";
        scaleFile = workspace + myID + ".scale";
        modelFile = workspace + "model." + myID + ".svm";
    }

    @Override
    public void clear() {
    }

    @Override
    public void train() {
        LinkedList<String> out = new LinkedList<String>();
        for (Tuple<Integer, Feature> ins : train)
            out.add(ins.getFirst() + "\t" + ins.getSecond().toString(1));
        FileOps.SaveList(trainFile, out);
        SystemCall.execute(SVMDir + "svm-scale -s " + scaleFile + " " + trainFile, trainScaleFile);
        SystemCall.execute(SVMDir + "svm-train -b 1 -h 0 " + trainScaleFile + " " + modelFile);
        FileOps.remove(trainFile);
        FileOps.remove(trainScaleFile);
    }

    @Override
    public double predict(Feature data) {
        ArrayList<Feature> datas = new ArrayList<Feature>();
        datas.add(data);
        return predict(datas).get(0);
    }

    @Override
    public ArrayList<Double> predict(List<Feature> data) {
        int curid = random.nextInt(10000000);
        String testFile = workspace + "test." + curid + ".svm";
        String testScaleFile = workspace + "test." + curid + ".svm.scale";
        String predFile = workspace + "pred." + curid + ".svm";
        LinkedList<String> out = new LinkedList<String>();
        for (Feature feature : data)
            out.add("0\t" + feature.toString(1));
        FileOps.SaveList(testFile, out);
        SystemCall.execute(SVMDir + "svm-scale -r " + scaleFile + " " + testFile, testScaleFile);
        SystemCall.execute(SVMDir + "svm-predict -b 1 " + testScaleFile + " " + modelFile + " " + predFile);
        ArrayList<Double> res = new ArrayList<Double>();
        int off = -1;
        for (String t : FileOps.LoadFilebyLine(predFile))
            try {
                if (off == -1) {
                    String[] sep = t.split(" ");
                    for (int i = 0; i < sep.length; i++)
                        try {
                            if (Integer.valueOf(sep[i]) == 1)
                                off = i;
                        } catch (Exception e) {
                        }
                } else
                    res.add(Double.valueOf(t.split(" ")[off]));
            } catch (Exception e) {
            }
        FileOps.remove(testFile);
        FileOps.remove(testScaleFile);
        FileOps.remove(predFile);
        return res;
    }

    @Override
    public void destroy() {
        FileOps.remove(modelFile);
        FileOps.remove(scaleFile);
    }

    public static void main(String[] args) {
        LibSVM model = new LibSVM("/home/cxz/Gits/libsvm/", "");
        model.setNFeature(1);
        model.addTrain(1, new SparseFeature(1));
        model.train();
        System.out.println(model.predict(new SparseFeature(1)));
        model.destroy();
    }
}