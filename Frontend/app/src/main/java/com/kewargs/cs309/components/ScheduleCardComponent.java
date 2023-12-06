package com.kewargs.cs309.components;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class ScheduleCardComponent extends Drawable {
    private String cID;
    private int width;
    private int height;
    private int relativeDistance;

    private int cNum;

    public ScheduleCardComponent(String courseIdentifier, int courseNum, int width, int height, int relativeDistance) {
        this.cID= courseIdentifier;
        this.cNum = courseNum;
        this.width = width;
        this.height = height;
        this.relativeDistance = relativeDistance;
    }

    @Override
    public void draw(Canvas canvas) {
        // Create a paint object for drawing
        Paint paint = new Paint();
        paint.setColor(Color.BLUE); // Set rectangle color
        paint.setStyle(Paint.Style.FILL);

        // Draw the rectangle
        Rect rect = new Rect(0, relativeDistance, width, relativeDistance + height);
        canvas.drawRect(rect, paint);

        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE); // Set text color
        textPaint.setTextSize(30); // Set text size

        // Calculate text position to center it horizontally inside the rectangle
        float textX = rect.left + (rect.width() - textPaint.measureText(cID)) / 2;
        float textY = rect.top + textPaint.getTextSize(); // Position for text at the top of the rectangle

        // Draw the text
        canvas.drawText(cID, textX, textY, textPaint);

        // Create a paint object for the number
        Paint numberPaint = new Paint();
        numberPaint.setColor(Color.WHITE); // Set number color
        numberPaint.setTextSize(40); // Set number size

        // Calculate number position to center it vertically inside the rectangle
        float numberX = rect.left + (rect.width() - numberPaint.measureText(cNum+"")) / 2;
        float numberY = rect.top + rect.height() / 2 - ((numberPaint.descent() + numberPaint.ascent()) / 2);

        // Draw the number
        canvas.drawText(cNum+"", numberX, numberY, numberPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        // Implement if needed
    }

    @Override
    public void setColorFilter(android.graphics.ColorFilter colorFilter) {
        // Implement if needed
    }

    @Override
    public int getOpacity() {
        // Implement if needed
        return PixelFormat.UNKNOWN;
    }
}
