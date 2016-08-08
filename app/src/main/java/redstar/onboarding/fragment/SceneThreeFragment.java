package redstar.onboarding.fragment;

import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import redstar.onboarding.R;
import redstar.onboarding.adapter.ImageAdapter;
import redstar.onboarding.databinding.FragmentSceneThreeBinding;
import redstar.onboarding.helper.ViewHelper;

/**
 * Created by Eoin on 8/8/16.
 */
public class SceneThreeFragment extends BaseSceneFragment {

    private static final int SCROLL_OFFSET = 200;

    private Point transitionDistance;
    private int scrollOffsetX;
    private int finishWidth, finishHeight;

    private FragmentSceneThreeBinding binding;

    public static SceneThreeFragment newInstance(int position) {
        SceneThreeFragment scene = new SceneThreeFragment();

        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        scene.setArguments(args);

        return scene;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_scene_three, container, false);

        setRootPositionTag(binding.root);

        setRecyclerView(binding.recycler1, getResources().obtainTypedArray(R.array.images_1));
        setRecyclerView(binding.recycler2, getResources().obtainTypedArray(R.array.images_2));
        setRecyclerView(binding.recycler3, getResources().obtainTypedArray(R.array.images_3));
        setRecyclerView(binding.recycler4, getResources().obtainTypedArray(R.array.images_4));
        setRecyclerView(binding.recycler5, getResources().obtainTypedArray(R.array.images_5));

        if (savedState != null) {
            transitionDistance = savedState.getParcelable("transitionDistance");
            finishWidth = savedState.getInt("finishWidth");
            finishHeight = savedState.getInt("finishHeight");
            scrollOffsetX = savedState.getInt("scrollOffsetX");

            moveScrollViews(0);

            // make sure finish view is invisible
            ImageView finishView =
                    (ImageView) binding.recycler2.getLayoutManager().findViewByPosition(3);
            if (finishView != null) {
                finishView.setVisibility(View.INVISIBLE);
            }
        }

        return binding.getRoot();
    }

    private void setRecyclerView(RecyclerView recyclerView, TypedArray drawables) {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
        ImageAdapter adapter = new ImageAdapter(drawables);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("transitionDistance", transitionDistance);
        outState.putInt("scrollOffsetX", scrollOffsetX);
        outState.putInt("finishWidth", finishWidth);
        outState.putInt("finishHeight", finishHeight);
    }

    @Override
    public void enterScene(ImageView sharedElement, float position) {
        if (transitionDistance == null) {
            setTransition(sharedElement);
        }

        binding.root.setAlpha(1 - position);

        sharedElement.setX(transitionDistance.x * (1 - position));
        sharedElement.setY(-getResources().getDimension(R.dimen.tutorial_shared_element_translate_y)
                + (transitionDistance.y * (1 - position)));
        scaleSharedElement(position, sharedElement);
        setSharedImageRadius(sharedElement, position);

        moveScrollViews(position);
    }

    private void setTransition(ImageView sharedElement) {
        // get the finish view
        ImageView finishView =
                (ImageView) binding.recycler2.getLayoutManager().findViewByPosition(3);
        finishView.setVisibility(View.INVISIBLE);

        finishHeight = finishView.getHeight();
        finishWidth = finishView.getWidth();

        Point finishViewLocation = ViewHelper.getViewLocation(finishView);

        // find the point of the screen(middle - half image) and for final point to be centered
        int screenCenterX = ViewHelper.getDisplaySize(getActivity()).x / 2;
        int finishWidth = finishView.getWidth() / 2;
        int finishX = screenCenterX - finishWidth;

        // the distance the recyclerview needs to scroll for the finish view to be centered
        scrollOffsetX = finishX - finishViewLocation.x;

        Point sharedLocation = ViewHelper.getViewLocation(sharedElement);
        transitionDistance = new Point();
        transitionDistance.x = finishX - sharedLocation.x;
        transitionDistance.y = finishViewLocation.y - sharedLocation.y;
    }

    @Override
    public void centerScene(ImageView sharedElement) {
        binding.root.setAlpha(1.0f);

        // make sure shared element is set in the correct place
        sharedElement.setX(transitionDistance.x);
        sharedElement.setY(-getResources().getDimension(R.dimen.tutorial_shared_element_translate_y)
                + transitionDistance.y);
        scaleSharedElement(0, sharedElement);

        setSharedImageRadius(sharedElement, 0);
        moveScrollViews(0);
    }

    //position goes from -1.0 to 0.0
    @Override
    public void exitScene(ImageView sharedElement, float position) {
        // last scene, it wont exit
    }

    @Override
    public void notInScene() {
        // reset scroll views
        moveScrollViews(1.0f);
        binding.root.setAlpha(0);
    }

    private void moveScrollViews(float position) {
        // use and odd and even scroll so images don't end up perfectly aligned
        int scroll = (int) (scrollOffsetX * (1 - position) + SCROLL_OFFSET);

        scrollRecycleViewTo(binding.recycler1, -scroll);
        scrollRecycleViewTo(binding.recycler2, scroll);
        scrollRecycleViewTo(binding.recycler3, -scroll);
        scrollRecycleViewTo(binding.recycler4, scroll);
        scrollRecycleViewTo(binding.recycler5, -scroll);
    }

    private void scrollRecycleViewTo(RecyclerView recyclerView, int offset) {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
        layoutManager.scrollToPositionWithOffset(2, offset);
    }

    private void scaleSharedElement(float position, ImageView sharedElement) {
        float scaleX = 1 - ((float) finishWidth / sharedElement.getWidth());
        float scaleY = 1 - ((float) finishHeight / sharedElement.getHeight());

        // scale around the center
        sharedElement.setPivotX(1.0f);
        sharedElement.setPivotY(1.0f);

        // scale the shared image to the finish views size
        sharedElement.setScaleX((1 - (scaleX * (1 - position))));
        sharedElement.setScaleY((1 - (scaleY * (1 - position))));
    }
}
