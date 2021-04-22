package com.ss.commons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

public class ProgressMask extends Image {
    Vector2 center = new Vector2(getWidth() / 2.0f, getHeight() / 2.0f);
    Vector2 centerTop = new Vector2(getWidth() / 2.0f, getHeight());
    float[] fv;
    IntersectAt intersectAt;
    Vector2 leftBottom = new Vector2(0.0f, 0.0f);
    Vector2 leftTop = new Vector2(0.0f, getHeight());
    PolygonSpriteBatch polyBatch;
    Vector2 progressPoint = new Vector2(getWidth() / 2.0f, getHeight() / 2.0f);
    Vector2 rightBottom = new Vector2(getWidth(), 0.0f);
    Vector2 rightTop = new Vector2(getWidth(), getHeight());
    TextureRegion texture;

    public enum IntersectAt {
        NONE,
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }

    public ProgressMask(TextureRegion region, PolygonSpriteBatch polyBatch) {
        super(region);
        setOrigin(getWidth() / 2.0f, getHeight() / 2.0f);
        this.texture = region;
        this.polyBatch = polyBatch;
        setPercentage(0.0f);
    }

    private Vector2 IntersectPoint(Vector2 line) {
        Vector2 v = new Vector2();
        if (Intersector.intersectSegments(this.leftTop, this.rightTop, this.center, line, v)) {
            this.intersectAt = IntersectAt.TOP;
            return v;
        } else if (Intersector.intersectSegments(this.leftBottom, this.rightBottom, this.center, line, v)) {
            this.intersectAt = IntersectAt.BOTTOM;
            return v;
        } else if (Intersector.intersectSegments(this.leftTop, this.leftBottom, this.center, line, v)) {
            this.intersectAt = IntersectAt.LEFT;
            return v;
        } else if (Intersector.intersectSegments(this.rightTop, this.rightBottom, this.center, line, v)) {
            this.intersectAt = IntersectAt.RIGHT;
            return v;
        } else {
            this.intersectAt = IntersectAt.NONE;
            return null;
        }
    }

    public void setPercentage(float percent) {
        float len;
        float angle = convertToRadians(90.0f) - convertToRadians((360.0f * percent) / 100.0f);
        if (getWidth() > getHeight()) {
            len = getWidth();
        } else {
            len = getHeight();
        }
        float dx = (float) (Math.cos((double) angle) * ((double) len));
        Vector2 v = IntersectPoint(new Vector2(this.center.x + dx, this.center.y + ((float) (Math.sin((double) angle) * ((double) len)))));
        if (this.intersectAt == IntersectAt.TOP) {
            if (v.x >= getWidth() / 2.0f) {
                this.fv = new float[]{this.center.x, this.center.y, this.centerTop.x, this.centerTop.y, this.leftTop.x, this.leftTop.y, this.leftBottom.x, this.leftBottom.y, this.rightBottom.x, this.rightBottom.y, this.rightTop.x, this.rightTop.y, v.x, v.y};
                return;
            }
            this.fv = new float[]{this.center.x, this.center.y, this.centerTop.x, this.centerTop.y, v.x, v.y};
        } else if (this.intersectAt == IntersectAt.BOTTOM) {
            this.fv = new float[]{this.center.x, this.center.y, this.centerTop.x, this.centerTop.y, this.leftTop.x, this.leftTop.y, this.leftBottom.x, this.leftBottom.y, v.x, v.y};
        } else if (this.intersectAt == IntersectAt.LEFT) {
            this.fv = new float[]{this.center.x, this.center.y, this.centerTop.x, this.centerTop.y, this.leftTop.x, this.leftTop.y, v.x, v.y};
        } else if (this.intersectAt == IntersectAt.RIGHT) {
            this.fv = new float[]{this.center.x, this.center.y, this.centerTop.x, this.centerTop.y, this.leftTop.x, this.leftTop.y, this.leftBottom.x, this.leftBottom.y, this.rightBottom.x, this.rightBottom.y, v.x, v.y};
        } else {
            this.fv = null;
        }
    }

    public void draw(Batch batch,float x, float y,float sclX,float sclY, float parentAlpha) {
        if (this.fv != null) {
            batch.end();
            drawMe(batch,x,y,sclX,sclY);
            batch.begin();
        }
    }

    public void drawMe(Batch batch,float x, float y, float sclX, float sclY) {
        PolygonSprite poly = new PolygonSprite(new PolygonRegion(this.texture, this.fv, new EarClippingTriangulator().computeTriangles(this.fv).toArray()));
        poly.setPosition(x,y);
        poly.setRotation(getRotation());
        poly.setColor(getColor());
        poly.setSize(poly.getWidth()*sclX,poly.getHeight()*sclY);
        poly.setOrigin(Align.center,Align.center);
        this.polyBatch.begin();
        this.polyBatch.setProjectionMatrix(batch.getProjectionMatrix());
        poly.draw(this.polyBatch);
        this.polyBatch.end();
    }

    float convertToDegrees(float angleInRadians) {
        return angleInRadians * 57.29578f;
    }

    float convertToRadians(float angleInDegrees) {
        return angleInDegrees * 0.017453292f;
    }
}