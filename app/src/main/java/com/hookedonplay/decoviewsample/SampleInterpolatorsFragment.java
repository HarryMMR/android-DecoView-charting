/*
 * Copyright (C) 2015 Hooked On Play
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hookedonplay.decoviewsample;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

/**
 * Sample fragment showing example use of DynamicArcView
 */
public class SampleInterpolatorsFragment extends SampleFragment {

    /**
     * DynamicArcView allows you to add any number of arcs to the view. These are the index for
     * each arc so we can control each arc individually
     */
    private int mBackIndex;
    private int mSeries1Index;

    public SampleInterpolatorsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample_interpolators, container, false);
    }

    @Override
    protected void createTracks(){
        if (getView() != null) {
            createTracks(R.id.dynamicArcView1, new LinearInterpolator());
            createTracks(R.id.dynamicArcView2, new AnticipateInterpolator());
            createTracks(R.id.dynamicArcView3, new AccelerateInterpolator());
            createTracks(R.id.dynamicArcView4, new DecelerateInterpolator());
            createTracks(R.id.dynamicArcView5, new BounceInterpolator());
            createTracks(R.id.dynamicArcView6, new OvershootInterpolator());
        }
    }

    @Override
    protected void setupEvents() {
        if (getView() != null) {
            setupEvents(R.id.dynamicArcView1);
            setupEvents(R.id.dynamicArcView2);
            setupEvents(R.id.dynamicArcView3);
            setupEvents(R.id.dynamicArcView4);
            setupEvents(R.id.dynamicArcView5);
            setupEvents(R.id.dynamicArcView6);
        }
    }

    private void createTracks(int arcViewId, Interpolator interpolator) {
        final float mSeriesMax = 50f;
        final DecoView arcView = (DecoView) getView().findViewById(arcViewId);
        arcView.deleteAll();

        SeriesItem arcBackTrack = new SeriesItem.Builder(Color.argb(255,228,228,228))
                .setRange(0, mSeriesMax, mSeriesMax)
                .build();

        mBackIndex = arcView.addSeries(arcBackTrack);

        SeriesItem seriesItem1 = new SeriesItem.Builder(Color.argb(255,255,165,0))
                .setRange(0, mSeriesMax, 0)
                .setInterpolator(interpolator)
                .setSpinDuration(5000)
                .build();

        mSeries1Index = arcView.addSeries(seriesItem1);

    }

    /**
     * Create a series of events that build a demonstration of moving and positioning
     * the arcs in the DynamicArcView
     */
    private void setupEvents(int arcId) {
        final DecoView arcView = (DecoView) getView().findViewById(arcId);
        if (arcView == null || arcView.isEmpty()) {
            throw new IllegalStateException("Unable to add events to empty DynamicArcView");
        }

        arcView.executeReset();

        arcView.addEvent(new DecoEvent.Builder(15).setIndex(mSeries1Index).setDelay(1000).build());
        arcView.addEvent(new DecoEvent.Builder(40).setIndex(mSeries1Index).setDelay(5000).build());
        arcView.addEvent(new DecoEvent.Builder(50).setIndex(mSeries1Index).setDelay(8000).build());
        arcView.addEvent(new DecoEvent.Builder(0).setIndex(mSeries1Index).setDelay(13000).build());
        arcView.addEvent(new DecoEvent.Builder(25).setIndex(mSeries1Index).setDelay(16000).build());
        arcView.addEvent(new DecoEvent.Builder(0).setIndex(mSeries1Index).setDelay(19000)
                .setListener(new DecoEvent.ExecuteEventListener() {
                    @Override
                    public void onEventStart(DecoEvent event) {

                    }

                    @Override
                    public void onEventEnd(DecoEvent event) {
                        setDemoFinished(true);
                    }
                })
                .build());
    }
}
