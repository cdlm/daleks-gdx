package imt.uvinfo.daleks.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Daleks implements ApplicationListener {
    private Viewport viewport;
    private SpriteBatch sprites;
    private ShapeRenderer shapes;
    private float elapsed;
    private Sprite alien;
    private Animation<TextureRegion> idle;

    @Override
    public void create() {
        // le viewport gère la caméra quand la fenêtre change de taille
        viewport = new ScalingViewport(Scaling.fit, 800, 600);

        // prise en compte de la transparence des couleurs
        Gdx.gl.glEnable(GL30.GL_BLEND);

        // rendu en batches
        sprites = new SpriteBatch();
        shapes = new ShapeRenderer();

        Texture alienSpriteMap = new Texture(Gdx.files.internal("alienGreen.png"));
        alien = new Sprite(alienSpriteMap,
                           70, 92,
                           66, 92);
        idle = new Animation<>(0.5f, new Array<>(new TextureRegion[]{
                new TextureRegion(alienSpriteMap, 70, 92, 66, 92),
                new TextureRegion(alienSpriteMap, 135, 379, -66, 92),
                new TextureRegion(alienSpriteMap, 70, 92, 66, 92),
                new TextureRegion(alienSpriteMap, 69, 379, 66, 92),
        }));
    }

    @Override
    public void render() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();

        elapsed += Gdx.graphics.getDeltaTime();

        Gdx.gl.glClearColor(0, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(0, 0.5f, 1, 0.3f);
        shapes.rect(10, 10, 780, 580);
        shapes.end();

        alien.setPosition(400 + 100 * (float) Math.cos(elapsed), 300 + 25 * (float) Math.sin(elapsed));
        TextureRegion currentAlien = idle.getKeyFrame(elapsed, true);

        sprites.setProjectionMatrix(viewport.getCamera().combined);
        sprites.begin();
        alien.draw(sprites);
        sprites.draw(currentAlien, 50, 50);
        sprites.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
