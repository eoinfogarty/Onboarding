package redstar.onboarding.onboarding.fragment;

import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import redstar.onboarding.onboarding.R;
import redstar.onboarding.onboarding.databinding.FragmentSceneOneBinding;

/**
 * Created by Eoin on 8/8/16.
 */
public class SceneOneFragment extends BaseSceneFragment {

    public static final String KEY_SHARED_VIEW_Y = "KEY_SHARED_VIEW_Y";
    public static final String KEY_SHARED_VIEW_RADIUS = "KEY_SHARED_VIEW_RADIUS";

    private FragmentSceneOneBinding binding;

    public static SceneOneFragment newInstance(int position) {
        SceneOneFragment scene = new SceneOneFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        scene.setArguments(args);

        return scene;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_scene_one, container, false);
        setRootPositionTag(binding.root);

        Resources resources = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.img_shared);
        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(resources, bitmap);
        drawable.setAntiAlias(true);

        if (savedState != null) {
            // restore shared image view position and radius
            binding.sharedImage.setTranslationY(savedState.getFloat(KEY_SHARED_VIEW_Y));
            drawable.setCornerRadius(savedState.getFloat(KEY_SHARED_VIEW_RADIUS));
        }

        binding.sharedImage.setImageDrawable(drawable);

        return binding.getRoot();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save the shared image view y and radius
        outState.putFloat(KEY_SHARED_VIEW_Y, binding.sharedImage.getTranslationY());
        outState.putFloat(KEY_SHARED_VIEW_RADIUS,
                ((RoundedBitmapDrawable) binding.sharedImage.getDrawable()).getCornerRadius());
    }

    @Override
    public void enterScene(ImageView sharedElement, float position) {
        // starts center so no entrance needed
    }

    @Override
    public void centerScene(ImageView sharedElement) {
        binding.sharedImage.setTranslationY(0);
        setSharedImageRadius(binding.sharedImage, 0);

        binding.deviceImage.setAlpha(1.0f);
        binding.deviceText.setAlpha(1.0f);
        binding.deviceImage.setAlpha(1.0f);
        binding.deviceImage.setScaleX(1.0f);
    }

    //position goes from -1.0 to 0.0
    @Override
    public void exitScene(ImageView sharedElement, float position) {
        binding.sharedImage.setTranslationY(
                getResources().getDimension(R.dimen.tutorial_shared_element_translate_y)
                        * position);

        setSharedImageRadius(binding.sharedImage, -position);

        binding.deviceText.setAlpha(1 + position);
        binding.deviceImage.setAlpha(1 + position);
        binding.deviceImage.setScaleX(1 - position); // stretch
    }

    @Override
    public void notInScene() {
        binding.deviceImage.setAlpha(0.0f);
        binding.deviceText.setAlpha(0.0f);
    }
}
