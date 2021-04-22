package com.ss.commons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ss.core.util.GStage;

import java.io.File;

import ze.spineactor.esotericsoftware.spine.AnimationState;
import ze.spineactor.esotericsoftware.spine.AnimationStateData;
import ze.spineactor.esotericsoftware.spine.Skeleton;
import ze.spineactor.esotericsoftware.spine.SkeletonData;
import ze.spineactor.esotericsoftware.spine.SkeletonJson;
import ze.spineactor.esotericsoftware.spine.SkeletonRenderer;
import ze.spineactor.esotericsoftware.spine.SkeletonRendererDebug;
import ze.spineactor.esotericsoftware.spine.utils.TwoColorPolygonBatch;

public class Spine extends Actor {
    TextureAtlas atlas;
    Skeleton skeleton;
    public AnimationState state;
    SkeletonRenderer renderer;
    SkeletonRendererDebug debugRenderer;

    TwoColorPolygonBatch tcpBatch;

    public Spine(TextureAtlas atlas, FileHandle file){
        renderer = new SkeletonRenderer();
        renderer.setPremultipliedAlpha(false); // PMA results in correct blending without outlines.
        this.atlas = atlas;
        SkeletonJson json = new SkeletonJson(atlas);
        SkeletonData skeletonData = json.readSkeletonData(file);
        skeleton = new Skeleton(skeletonData); // Skeleton holds skeleton state (bone positions, slot attachments, etc).
        AnimationStateData stateData = new AnimationStateData(skeletonData); // Defines mixing (crossfading) between animations.
        //stateData.setMix("run", "jump", 0.2f);
        //stateData.setMix("jump", "run", 0.2f);
        state = new AnimationState(stateData); // Holds the animation state for a skeleton (current animation, time, etc).
//        state.setTimeScale(0.5f); // Slow all animations down to 50% speed.
        // Queue animations on track 0.
        state.setAnimation(0, "animation", true);
        skeleton.setScale(1f,1f);


        tcpBatch = new TwoColorPolygonBatch();
        tcpBatch.setPremultipliedAlpha(true);
        this.setSize(skeleton.getData().getWidth(),skeleton.getData().getHeight());


    }


    @Override
    public void act(float delta) {
        super.act(delta);
        state.update(delta);
        state.apply(skeleton);
        skeleton.updateWorldTransform();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        //renderer.draw(batch, skeleton);

        drawMesh(batch);
    }

    public void drawMesh(Batch batch){
        batch.end();
        tcpBatch.setProjectionMatrix(batch.getProjectionMatrix());
        tcpBatch.setTransformMatrix(batch.getTransformMatrix());
        tcpBatch.begin();

        renderer.draw(tcpBatch, skeleton);
        tcpBatch.end();

        batch.begin();
    }
}
