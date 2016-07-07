package apex.javatool.console.gui;

/**
 * @author Xuezhi Cao
 * @contact cxz@apex.sjtu.edu.cn
 * Created on 7/7/16.
 */
public abstract class Describable {
    private String name;
    private static int indent = 0;

    public abstract void describeContent();

    public void describe() {
        show(name);
        indent++;
        describeContent();
        indent--;
    }

    protected void show(String content) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < indent; i++) stringBuilder.append("|  ");
        stringBuilder.append("|--");
        stringBuilder.append(content);
        System.out.println(stringBuilder.toString());
    }
    protected void show(String name,double value) {
        show(name+" : "+value);
    }
    protected void show(String name,int value) {
        show(name+" : "+value);
    }
    protected void show(String name,String value) {
        show(name+" : "+value);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
