package com.example.wbx.transitionanim;

import android.animation.Animator;
import android.transition.ChangeBounds;
import android.transition.TransitionValues;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

/**
 * desc
 * 创建人：wbx
 * 创建时间：2019-02-01
 */
public class CustomChangeBounds extends ChangeBounds {

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        Animator changeBounds = super.createAnimator(sceneRoot, startValues, endValues);
        if (startValues == null || endValues == null || changeBounds == null)
            return null;

        changeBounds.setDuration(300);
        changeBounds.setInterpolator(AnimationUtils.loadInterpolator(sceneRoot.getContext(),
                android.R.interpolator.fast_out_slow_in));
        return changeBounds;
    }
}
