package modules.general.ui.utils.GUI;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by mac on 4/11/16.
 */
public class CustomVideoView extends VideoView {

    protected int _overrideWidth = 480;

    public CustomVideoView(Context context) {
        super(context);
    }

    public CustomVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void resizeVideo(int width) {
    _overrideWidth = width;
    // not sure whether it is useful or not but safe to do so
    getHolder().setFixedSize(width, width);
    //getHolder().setSizeFromLayout();
    requestLayout();
    invalidate(); // very important, so that onMeasure will be triggered

}

@Override
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
{
    setMeasuredDimension(_overrideWidth, _overrideWidth);
}

}