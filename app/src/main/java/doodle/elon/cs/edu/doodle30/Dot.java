package doodle.elon.cs.edu.doodle30;

import android.graphics.Paint;

public class Dot {

    protected int x;
    protected int y;
    protected Paint paint;

    public Dot(int x, int y) {
        this.x = x;
        this.y = y;

        paint = new Paint();
        int a = (int)(Math.random() *256);
        int r = (int)(Math.random() *256);
        int g = (int)(Math.random() *256);
        int b = (int)(Math.random() *256);
        paint.setARGB(a, r, g, b);
    }

}
