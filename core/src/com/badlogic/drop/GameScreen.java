
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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;

public class GameScreen extends ScreenAdapter {
    
        public GameScreen(final DropGame dropGame)
        {
            this.dropGame = dropGame;
            dropletImage = new Texture(Gdx.files.internal("droplet.png"));
            bucketImage = new Texture(Gdx.files.internal("bucket.png"));
            rainSoundBackground = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
            dropletSound = Gdx.audio.newSound(Gdx.files.internal("droplet.wav"));
            atticImage = new Texture(Gdx.files.internal("attic.jpg"));
            rainSoundBackground.setLooping(true);
            
            font = new BitmapFont();
            font.setScale(1.5f);
            
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
                for(Droplet droplet : droplets)
                    droplet.getRect().y -= 200 * Gdx.graphics.getDeltaTime();
                
                /* las gotas que caigan en el cubo las eliminamos y aquellas que se salgan
                de la pantalla */
                Iterator<Droplet> it = droplets.iterator();
                while(it.hasNext())
                {
                    Droplet droplet = it.next();
                    if(droplet.getRect().overlaps(bucket))
                    {
                        dropletSound.play();
                        it.remove();
                        ++dropletsCatched;
                        score += droplet.getValue();
                        if(score > maxScore)
                            maxScore = score;
                    }
                    else if(droplet.getRect().y < 0)
                    {
                        it.remove();
                        score -= 2 * droplet.getValue();
                        /* gameover ?? */
                        if(score <= 0)
                            dropGame.setScreen(new GameOverScreen(dropGame));
                    }
                }           
                /* creamos nuevas gotas */
                double x, y;
                final double m = 2.5;
                x = dropletsCatched;
                y = 1200 - m * x;
                if(y < 250)
                    y = 250;
                if((TimeUtils.millis() - lastDropletTime) >= MathUtils.random((int)(y - 100), (int)(y + 100)))
                {
                    Rectangle re = new Rectangle();
                    re.x = MathUtils.random(64, 800-64);
                    re.y = 480;
                    re.width = re.height = 64;
                    DropletType dropletType;
                    int aux = MathUtils.random(1, 8);
                    if(aux <= 5)
                        dropletType = DropletType.NORMAL;
                    else if(aux <= 7)
                        dropletType = DropletType.DOUBLE;
                    else 
                        dropletType = DropletType.TRIPLE;
                    
                    Droplet droplet = new Droplet(dropletType, re);
                    
                    droplets.add(droplet);
                    lastDropletTime = TimeUtils.millis();
                }
                
                /* dibujamos el fondo, el cubo y las gotas */
                batch.setProjectionMatrix(camera.combined);
                
                batch.begin();
                batch.draw(atticImage, 0, 0);
                batch.draw(bucketImage, bucket.x, bucket.y);
                for(Droplet droplet : droplets)
                    droplet.draw(dropletImage, batch);
                /* dibujamos las estdísticas en la esquina superior derecha */
                batch.draw(dropletImage, 640, 375);
                font.draw(batch, String.valueOf(dropletsCatched), 700, 400);
                if(score > 0)
                    font.draw(batch, String.valueOf(score) + ((score > 1) ? " points" : " point"), 700, 340);
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
     
        DropGame dropGame;
        Texture dropletImage, bucketImage, atticImage;
        Music rainSoundBackground;
        Sound dropletSound;
        private OrthographicCamera camera;
        private SpriteBatch batch;
        private BitmapFont font;

        private Rectangle bucket = new Rectangle();
        private Array<Droplet> droplets = new Array<Droplet>();
        private long lastDropletTime;
        
        
        
        private int dropletsCatched = 0;
        private int score = 200; 
        private int maxScore = score;
}
