package redstar.onboarding.onboarding.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import redstar.onboarding.onboarding.R;
import redstar.onboarding.onboarding.databinding.FragmentSceneTwoBinding;
import redstar.onboarding.onboarding.helper.ViewHelper;

/**
 * Created by Eoin on 8/8/16.
 */
public class SceneTwoFragment extends BaseSceneFragment {

    private FragmentSceneTwoBinding binding;

    public static SceneTwoFragment newInstance(int position) {
        SceneTwoFragment scene = new SceneTwoFragment();

        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        scene.setArguments(args);

        return scene;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_scene_two, container, false);
        setRootPositionTag(binding.root);

        return binding.getRoot();
    }

    @Override
    public void enterScene(ImageView sharedElement, float position) {
        binding.root.setAlpha(1);

        moveSnsIconOut(position, sharedElement, binding.amebaIcon);
        moveSnsIconOut(position, sharedElement, binding.facebookIcon);
        moveSnsIconOut(position, sharedElement, binding.twitterIcon);

        // scale icons up
        scaleIcon(binding.amebaIcon, 1 - position);
        scaleIcon(binding.facebookIcon, 1 - position);
        scaleIcon(binding.twitterIcon, 1 - position);
    }

    @Override
    public void centerScene(ImageView sharedElement) {
        sharedElement.setTranslationY(
                -getResources().getDimension(R.dimen.tutorial_shared_element_translate_y));
        setSharedImageRadius(sharedElement, 1);
    }

    //position goes from -1.0 to 0.0
    @Override
    public void exitScene(ImageView sharedElement, float position) {
        binding.root.setAlpha(1);

        // scale icons down
        scaleIcon(binding.amebaIcon, 1 + position);
        scaleIcon(binding.facebookIcon, 1 + position);
        scaleIcon(binding.twitterIcon, 1 + position);

        binding.amebaIcon.setAlpha(1 + position);
        binding.facebookIcon.setAlpha(1 + position);
        binding.twitterIcon.setAlpha(1 + position);
    }

    @Override
    public void notInScene() {
        binding.root.setAlpha(0);
    }

    private void moveSnsIconOut(float position, ImageView sharedElement, ImageView icon) {
        Point iconCenter = ViewHelper.getViewCenterPoint(icon);
        Point sharedCenter = ViewHelper.getViewCenterPoint(sharedElement);

        float distanceX = (sharedCenter.x - iconCenter.x) * position;
        float distanceY = (sharedCenter.y - iconCenter.y) * position;

        // multiple position again for an accelerate effect
        icon.setTranslationX(distanceX * position);
        icon.setTranslationY(distanceY * position);
    }

    private void scaleIcon(ImageView icon, float position) {
        icon.setScaleX(position);
        icon.setScaleY(position);
    }
}
