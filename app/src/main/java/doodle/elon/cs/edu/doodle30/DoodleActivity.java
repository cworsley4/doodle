package doodle.elon.cs.edu.doodle30;

import android.os.Bundle;
import java.io.FileOutputStream;
import java.io.IOException;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class DoodleActivity extends Activity {

    private Dialog currentDialog;
    private DoodleView doodleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doodle);

        // get a reference to the DoodleView
        doodleView = (DoodleView) findViewById(R.id.doodleView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_doodle, menu);
        return true;
    }

    public void saveImage(View v){

        Bitmap bit = v.getDrawingCache();

        try {
            FileOutputStream fos = new FileOutputStream("image.png");
            bit.compress(Bitmap.CompressFormat.PNG, 100, fos);
            String s = (String) bit.toString();
            System.out.println(s);

        } catch (IOException e) {
            e.printStackTrace();
        }

        v.destroyDrawingCache();
    }

    public boolean showWidthDialog(MenuItem menuItem) {

        // build a new Dialog
        currentDialog = new Dialog(this);
        currentDialog.setContentView(R.layout.width_dialog);
        currentDialog.setTitle("Choose a width");

        // set the SeekBar progress with the saved line width
        SeekBar widthSeekBar = (SeekBar) currentDialog.findViewById(R.id.seekBarWidth);
        widthSeekBar.setOnSeekBarChangeListener(widthSeekBarListener);
        widthSeekBar.setProgress(doodleView.width);

        // attach a button listener
        Button widthButton = (Button) currentDialog.findViewById(R.id.buttonWidth);
        widthButton.setOnClickListener(setWidthButtonListener);

        // now, show the Dialog
        currentDialog.show();
        return true;
    }

    public boolean showColorDialog(MenuItem menuItem){
        currentDialog = new Dialog(this);
        currentDialog.setContentView(R.layout.color_dialog);
        currentDialog.setTitle("Choose a Color");

        View view = (View) currentDialog.findViewById(R.id.preViewColor);

        SeekBar alphaBar = (SeekBar) currentDialog.findViewById(R.id.seekBarAlpha);
        alphaBar.setProgress(doodleView.alpha);
        alphaBar.setOnSeekBarChangeListener(colorChangeListener);

        SeekBar redBar = (SeekBar) currentDialog.findViewById(R.id.seekBarRed);
        redBar.setProgress(doodleView.red);
        redBar.setOnSeekBarChangeListener(colorChangeListener);

        SeekBar greenBar = (SeekBar) currentDialog.findViewById(R.id.seekBarGreen);
        greenBar.setProgress(doodleView.green);
        greenBar.setOnSeekBarChangeListener(colorChangeListener);

        SeekBar blueBar = (SeekBar) currentDialog.findViewById(R.id.seekBarBlue);
        blueBar.setProgress(doodleView.blue);
        blueBar.setOnSeekBarChangeListener(colorChangeListener);

        Button saveColor = (Button) currentDialog.findViewById(R.id.saveColor);
        saveColor.setOnClickListener(setColorButtonListener);

        view.setBackgroundColor(Color.argb(doodleView.alpha, doodleView.red, doodleView.green, doodleView.blue));

        currentDialog.show();
        return true;
    }

    private OnClickListener setColorButtonListener = new OnClickListener(){

        @Override
        public void onClick(View v) {
            SeekBar alpha = (SeekBar) currentDialog.findViewById(R.id.seekBarAlpha);
            doodleView.alpha = alpha.getProgress();

            SeekBar red = (SeekBar) currentDialog.findViewById(R.id.seekBarRed);
            doodleView.red = red.getProgress();

            SeekBar green = (SeekBar) currentDialog.findViewById(R.id.seekBarGreen);
            doodleView.green = green.getProgress();

            SeekBar blue = (SeekBar) currentDialog.findViewById(R.id.seekBarBlue);
            doodleView.blue = blue.getProgress();

            currentDialog.dismiss();
            currentDialog = null;
        }

    };

    private OnClickListener setWidthButtonListener = new OnClickListener() {

        @Override
        public void onClick(View view) {
            // remember the width
            SeekBar seekBar = (SeekBar) currentDialog.findViewById(R.id.seekBarWidth);
            doodleView.width = seekBar.getProgress();

            // Dialog is done!
            currentDialog.dismiss();
            currentDialog = null;
        }
    };

    private OnSeekBarChangeListener colorChangeListener = new OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {

            View view = (View) currentDialog.findViewById(R.id.preViewColor);
            Paint p = new Paint();
            p.setARGB(doodleView.alpha, doodleView.red, doodleView.green, doodleView.blue);
            p.setStrokeWidth(doodleView.width);

            switch (seekBar.getId()) {
                case R.id.seekBarAlpha :
                    p.setAlpha(progress);
                    doodleView.alpha = progress;
                    break;
                case R.id.seekBarRed :
                    p.setARGB(doodleView.alpha, progress, doodleView.green, doodleView.blue);
                    doodleView.red = progress;
                    break;
                case R.id.seekBarGreen :
                    p.setARGB(doodleView.alpha, doodleView.red, progress, doodleView.blue);
                    doodleView.green = progress;
                    break;
                case R.id.seekBarBlue :
                    p.setARGB(doodleView.alpha, doodleView.red, doodleView.green, progress);
                    doodleView.blue = progress;
                    break;
            }

            view.setBackgroundColor(Color.argb(doodleView.alpha, doodleView.red, doodleView.green, doodleView.blue));

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) { }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) { }

    };

    private OnSeekBarChangeListener widthSeekBarListener = new OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            // grab a reference to the ImageView
            ImageView imageView = (ImageView) currentDialog.findViewById(R.id.imageViewWidth);

            // set up the paintbrush
            Paint p = new Paint();
            p.setARGB(doodleView.alpha, doodleView.red, doodleView.green, doodleView.blue);
            p.setStrokeWidth(progress);
            p.setStrokeCap(Paint.Cap.ROUND);

            // create a bitmap/canvas
            Bitmap bitmap = Bitmap.createBitmap(250, 100, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);

            // draw the line, show the bitmap
            canvas.drawLine(30, 50, 250-30, 50, p);
            imageView.setImageBitmap(bitmap);
        }

        @Override
        public void onStartTrackingTouch(SeekBar arg0) {}

        @Override
        public void onStopTrackingTouch(SeekBar arg0) {}

    };

    public void clearScreen(View view) {
        doodleView.clear();
    }
}

