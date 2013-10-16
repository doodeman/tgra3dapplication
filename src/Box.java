import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.BufferUtils;

public class Box
{
	private Point3D location;
	private FloatBuffer vertexBuffer;
	private SpriteBatch batch;
	public Grid grid; 
	private Texture texture;
	
	Box(FloatBuffer buffer, Point3D loc){
		this.location =loc;
		this.vertexBuffer = buffer;
		//texture = new Texture(Gdx.files.internal("lib/box.png"));
		//batch = new SpriteBatch();
	}
	
	public void draw(){
		Gdx.gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, this.vertexBuffer);
		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glTranslatef(this.location.x, this.location.y, this.location.z);
		Gdx.gl11.glScalef(0.95f, 0.95f, 0.95f);
		Gdx.gl11.glNormal3f(0.0f, 0.0f, -1.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		Gdx.gl11.glNormal3f(1.0f, 0.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 4, 4);
		Gdx.gl11.glNormal3f(0.0f, 0.0f, 1.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 8, 4);
		Gdx.gl11.glNormal3f(-1.0f, 0.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 12, 4);
		Gdx.gl11.glNormal3f(0.0f, 1.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 16, 4);
		Gdx.gl11.glNormal3f(0.0f, -1.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 20, 4);
		Gdx.gl11.glPopMatrix();
	}
}