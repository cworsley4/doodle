package doodle.elon.cs.edu.doodle30;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class DoodleView extends View {

    private Point lastPoint;
    private ArrayList<Line> lines;
    protected int width;
    protected int alpha;
    protected int red;
    protected int green;
    protected int blue;

    public DoodleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        lines = new ArrayList<Line>();
        width = 20;
        alpha = 255;
        red = 0;
        green = 0;
        blue = 0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action= event.getActionMasked();

        Line l;
        Point p = new Point();
        p.set((int) event.getX(),(int) event.getY());

        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE){

            //Action Down
            if(lastPoint == null){
                l = new Line(p, p, width, alpha, red, green, blue);
                lastPoint = p;
                lines.add(l);
            }
            //Action Move
            else {
                l = new Line(lastPoint, p, width, alpha, red, green, blue);
                lines.add(l);
                lastPoint = p;
            }
        }

        if (action == MotionEvent.ACTION_UP){
            lastPoint = null;
        }

        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        for(Line l: lines) {
            canvas.drawLine((float) l.start.x,(float) l.start.y,(float) l.end.x,(float) l.end.y, l.paint);
        }
    }

    public void clear(){
        lines.clear();
        invalidate();
    }
}
