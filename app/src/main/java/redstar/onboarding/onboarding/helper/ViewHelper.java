package redstar.onboarding.onboarding.helper;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Eoin on 8/8/16.
 */
public class ViewHelper {

    public static Point getViewLocation(@NonNull View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int centerX = location[0];
        int centerY = location[1];

        return new Point(centerX, centerY);
    }

    public static Point getViewCenterPoint(@NonNull View view) {
        Point center = getViewLocation(view);
        center.x += view.getWidth() / 2;
        center.y += view.getHeight() / 2;

        return center;
    }

    public static Point getDisplaySize(@NonNull Context context) {
        Point point = new Point();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getSize(point);
        return point;
    }
}
