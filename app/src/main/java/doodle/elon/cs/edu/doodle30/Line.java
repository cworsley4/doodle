package doodle.elon.cs.edu.doodle30;

import android.graphics.Paint;
import android.graphics.Point;

public class Line {

    protected Point start;
    protected Point end;
    protected Paint paint;

    public Line(Point start, Point end, int width, int alpha, int red, int green, int blue) {
        paint = new Paint();
        this.start = new Point();
        this.end = new Point();
        paint.setARGB(alpha, red, green, blue);
        paint.setStrokeWidth(width);
        paint.setStrokeCap(Paint.Cap.ROUND);
        this.start = start;
        this.end = end;
    }
}
