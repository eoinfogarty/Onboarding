package redstar.onboarding;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import redstar.onboarding.databinding.ActivityMainBinding;
import redstar.onboarding.adapter.ScenePagerAdapter;
import redstar.onboarding.adapter.TextPagerAdapter;
import redstar.onboarding.adapter.SceneTransformer;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_BUTTON_VISIBILITY = "KEY_BUTTON_VISIBILITY";
    private static final String KEY_BUTTON_Y = "KEY_BUTTON_Y";

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        SceneTransformer sceneTransformer = new SceneTransformer();
        ScenePagerAdapter scenePagerAdapter =
                new ScenePagerAdapter(getSupportFragmentManager(), sceneTransformer);

        binding.tutorialPager.setAdapter(scenePagerAdapter);
        // set limit same as number of fragments
        binding.tutorialPager.setOffscreenPageLimit(3);
        binding.tutorialPager.setPageTransformer(true, sceneTransformer);

        TextPagerAdapter textAdapter = new TextPagerAdapter();
        binding.textPager.setAdapter(textAdapter);

        binding.indicator.setViewPager(binding.textPager);
        binding.indicator.setSnap(true);
        binding.indicator.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // do nothing
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                // translate up start button
                if (position == 1) {
                    binding.start.setVisibility(View.VISIBLE);
                    binding.start.setTranslationY(
                            binding.textPager.getBottom() * (1 - positionOffset));
                    binding.indicator.setAlpha(1 - positionOffset);
                }
            }
        });

        // to control the two view pagers at once we put a layout above them that intercepts the touches
        binding.touchInterceptorLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                binding.tutorialPager.onTouchEvent(event);
                binding.textPager.onTouchEvent(event);
                return true;
            }
        });

        binding.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Start clicked", Toast.LENGTH_SHORT).show();
            }
        });

        if (savedInstanceState != null) {
            // re-add the listeners from the scene fragments
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                sceneTransformer.addSceneChangeListener(
                        (SceneTransformer.SceneChangeListener) fragment);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(KEY_BUTTON_VISIBILITY, binding.start.getVisibility());
        savedInstanceState.putFloat(KEY_BUTTON_Y, binding.start.getTranslationY());

        super.onSaveInstanceState(savedInstanceState);
    }

    @SuppressWarnings("ResourceType")
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        binding.start.setVisibility(savedInstanceState.getInt(KEY_BUTTON_VISIBILITY));
        binding.start.setTranslationY(savedInstanceState.getFloat(KEY_BUTTON_Y));
    }
}
