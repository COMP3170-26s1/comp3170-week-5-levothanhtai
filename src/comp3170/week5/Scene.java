package comp3170.week5;

import org.joml.Matrix4f;
import org.joml.Vector2i;
import org.joml.Vector4f;
import comp3170.InputManager;
import comp3170.SceneObject;

import java.util.ArrayList;
import java.util.List;

import comp3170.week5.sceneobjects.*;

public class Scene extends SceneObject {
	private Camera camera;
	private List<Flower> flowers = new ArrayList<>();
	
	public Scene() {
		camera = new Camera();
		camera.setParent(this);
		createFlower(new Vector4f(0.0f,0.0f,0.f,1.0f));		
	}
	
	public Camera sceneCam() {
		return camera;
	}
	
	public void createFlower(Vector4f position) {
		Flower flower = new Flower(10);
		flower.setParent(this);	
		flower.setBasePosition(position.x, position.y);
		flowers.add(flower);
	}
	
	public void click(Vector2i position, int width, int height) {
		float mouseX = position.x;
		float mouseY = position.y;

		float xNDC = (2.0f * mouseX / width) - 1.0f;
		float yNDC = 1.0f - (2.0f * mouseY / height);

		Vector4f clickPosition = new Vector4f(xNDC, yNDC, 0.0f, 1.0f);

		Matrix4f viewMatrix = camera.GetViewMatrix(new Matrix4f());
		Matrix4f projectionMatrix = camera.GetProjectionMatrix(new Matrix4f());

		Matrix4f pvMatrix = new Matrix4f(projectionMatrix).mul(viewMatrix);
		Matrix4f inversePV = pvMatrix.invert(new Matrix4f());

		clickPosition.mul(inversePV);

		if (clickPosition.w != 0.0f) {
			clickPosition.x /= clickPosition.w;
			clickPosition.y /= clickPosition.w;
			clickPosition.z /= clickPosition.w;
			clickPosition.w = 1.0f;
		}

		createFlower(clickPosition);
	}

	public void update(InputManager input, float dt) {
		
		camera.update(input, dt);
		
		// TODO: Update the flowers when animating them. (TASK 5)
		for (Flower flower : flowers) {
			flower.update(dt);
		}
	}
	
}
