package redstar.onboarding.adapter;


import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import redstar.onboarding.fragment.BaseSceneFragment;
import redstar.onboarding.fragment.SceneOneFragment;
import redstar.onboarding.fragment.SceneThreeFragment;
import redstar.onboarding.fragment.SceneTwoFragment;

/**
 * Created by Eoin on 8/8/16.
 */
public class ScenePagerAdapter extends FragmentPagerAdapter {

    private SceneTransformer mSceneTransformer;

    public ScenePagerAdapter(@NonNull FragmentManager supportFragmentManager,
                             @NonNull SceneTransformer sceneTransformer) {
        super(supportFragmentManager);

        mSceneTransformer = sceneTransformer;
    }

    @Override
    public Fragment getItem(int position) {
        BaseSceneFragment baseSceneFragment = null;

        switch (position) {
            case 0:
                baseSceneFragment = SceneOneFragment.newInstance(position);
                break;
            case 1:
                baseSceneFragment = SceneTwoFragment.newInstance(position);
                break;
            case 2:
                baseSceneFragment = SceneThreeFragment.newInstance(position);
                break;
        }

        mSceneTransformer.addSceneChangeListener(baseSceneFragment);
        return baseSceneFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}