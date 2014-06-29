
package com.badlogic.drop;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.math.MathUtils;
import java.util.Iterator;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class GameScreen extends ScreenAdapter {
    
        public GameScreen(final DropGame dropGame)
        {
            /* crear texturas y sonidos */
            dropletImage = new Texture(Gdx.files.internal("droplet.png"));
            bucketImage = new Texture(Gdx.files.internal("bucket.png"));
            rainSoundBackground = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
            dropletSound = Gdx.audio.newSound(Gdx.files.internal("droplet.wav"));
            atticImage = new Texture(Gdx.files.internal("attic.jpg"));
            
            rainSoundBackground.setLooping(true);
            
            /* obtener sprite batch para dibujar en la pantalla */
            batch = dropGame.batch;
            
            /* establecer las dimensiones del cubo */
            bucket.x = 800 / 2 - 64 / 2;
            bucket.y = 20; 
            bucket.width = bucket.height = 64;
            
            
            lastDropletTime = TimeUtils.millis();
	}
        
        @Override
        public void show() 
        {
            /* crear la cámara */
            camera = new OrthographicCamera();
            camera.setToOrtho(false, 800, 480);
            
            /* el sonido de lluvia se escucha cuando el juego comienza */
            rainSoundBackground.play();
        }

	@Override
	public void render (float delta) {
                /* mover el cubo en función de si el usuario presiona algún botón o 
                la pantalla
                */
                if(Gdx.input.isTouched()) { 
                    Vector3 v = new Vector3();
                    v.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                    camera.unproject(v);
                    bucket.x = v.x - 64/2;
                }
                
                /*
                mover el cubo si se presionan las flechas.
                */
                
                if(Gdx.input.isKeyPressed(Keys.LEFT))
                    bucket.x -= 270 * Gdx.graphics.getDeltaTime();
                if(Gdx.input.isKeyPressed(Keys.RIGHT))
                    bucket.x += 270 * Gdx.graphics.getDeltaTime();
            
		Gdx.gl.glClearColor(0, 0, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                
                /* queremos actualizar la matriz de proyección o modelview */
                camera.update();
                
                /* actualizamos la posición vertical de las gotas */
                for(Rectangle droplet : droplets)
                    droplet.y -= 200 * Gdx.graphics.getDeltaTime();
                
                /* las gotas que caigan en el cubo las eliminamosy aquellas que se salgan
                de la pantalla */
                Iterator<Rectangle> it = droplets.iterator();
                while(it.hasNext())
                {
                    Rectangle droplet = it.next();
                    if(droplet.overlaps(bucket))
                    {
                        dropletSound.play();
                        it.remove();
                    }
                    else if(droplet.y < 0)
                    {
                        it.remove();
                    }
                }           
                /* creamos nuevas gotas */
                if((TimeUtils.millis() - lastDropletTime) >= MathUtils.random(600, 900))
                {
                    Rectangle droplet = new Rectangle();
                    droplet.x = MathUtils.random(64, 800-64);
                    droplet.y = 480;
                    droplet.width = droplet.height = 64;
                    droplets.add(droplet);
                    lastDropletTime = TimeUtils.millis();
                }
                
                /* dibujamos el fondo, el cubo y las gotas */
                batch.setProjectionMatrix(camera.combined);
                
                batch.begin();
                batch.draw(atticImage, 0, 0);
                batch.draw(bucketImage, bucket.x, bucket.y);
                for(Rectangle droplet : droplets)
                    batch.draw(dropletImage, droplet.x, droplet.y);
                batch.end();
                
	}
        
        @Override
        public void dispose() 
        {
            dropletImage.dispose();
            bucketImage.dispose();
            rainSoundBackground.dispose();
            dropletSound.dispose();
        }
     
        private Texture dropletImage, bucketImage, atticImage;
        private Music rainSoundBackground;
        private Sound dropletSound;
        private OrthographicCamera camera;
        private SpriteBatch batch;

        private Rectangle bucket = new Rectangle();
        private Array<Rectangle> droplets = new Array<Rectangle>();
        private long lastDropletTime;
}
