package ru.demyanko.casino.model.casino1;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import ru.demyanko.casino.model.AbstractGameContentUnit;

/**
 * Created by Dmitriy on 26.09.2017.
 */
class Barrel extends AbstractGameContentUnit {
    private int speed;
    private int number;
    private int amuntOfPictures;
    private Array<Sprite> pictures;
    private float pictureHeight;
    private boolean isStopped;
    private Graphics graphics;
    private TextureAtlas textureAtlas;

    Barrel(float x, float y,
           float width, float height,
           int number, int amuntOfPictures,
           TextureAtlas textureAtlas,
           Graphics graphics) {

        super(x, y, width, height);

        this.number = number;
        this.amuntOfPictures = amuntOfPictures;
        isStopped = true;
        this.pictures = new Array();
        this.pictureHeight = height / 3;
        this.textureAtlas = textureAtlas;
        this.graphics = graphics;
        fill(pictures);
    }


    private void fill(Array<Sprite> array) {
        for (int i = 0; i < amuntOfPictures; i++) {
            Sprite picture = textureAtlas.createSprite("" + MathUtils.random(1, amuntOfPictures));
            picture.setX(getX());
            picture.setY(getY() + i * pictureHeight);
            array.add(picture);
        }
    }

    void setSpeed(int speed) {
        this.speed = speed;
    }


    @Override
    public void draw(SpriteBatch batch) {
        for (int i = 0; i < amuntOfPictures; i++) {
            pictures.get(i).draw(batch);        }
    }
    @Override
    public void update() {
        float shift;
        if (!isStopped) {
            shift = speed * graphics.getDeltaTime();
            for (int i = 0; i < amuntOfPictures; i++) {
                pictures.get(i).setY(pictures.get(i).getY() - shift);
                jumpUp(pictures.get(i));
            }
        }
    }
    // to imitate real barrel the pictures should go cycle
    private void jumpUp(Sprite picture) {
        if (picture.getY() + pictureHeight < getY())
            picture.setY(picture.getY() + pictureHeight * amuntOfPictures);
    }


    @Override
    public void dispose() {
        for (Sprite picture : pictures) {
            picture.getTexture().dispose();
        }
    }

    int getAmuntOfPictures() {
        return amuntOfPictures;
    }

    void stopBarrel() {
        for (int i = 0; i < getAmuntOfPictures(); i++) {
            if (pictures.get(i).getY() <= getY()  && pictures.get(i).getY() >= getY() - 6) {
                isStopped = true;
                break;
            }
        }
    }

    boolean isStopped() {
        return isStopped;
    }

    void setIsStopped(boolean isStopped) {
        this.isStopped = isStopped;
    }


}
