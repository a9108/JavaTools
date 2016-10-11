package apex.javatool.chart.google;

/**
 * @author Xuezhi Cao
 * @contact cxz@apex.sjtu.edu.cn
 * Created on 10/10/16.
 */
public class PrintableTable {
    private boolean grid = false;
    private int[] width;

    public PrintableTable(int[] width) {
        this.width = width;
    }

    public void setGrid(boolean grid) {
        this.grid = grid;
    }

    public void printHeader(String[] header){

    }

    public void printLine(Object[] header){
    }
}
