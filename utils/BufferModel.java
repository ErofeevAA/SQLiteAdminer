package utils;

public class BufferModel {
    public static final int STATE_DEL = 1;
    public static final int STATE_ADD = 2;

    public int posStart;
    public String str;
    public int state;

    public BufferModel(int pos, String str, int state) {
        this.posStart = pos;
        this.str = str;
        this.state = state;
    }
}
