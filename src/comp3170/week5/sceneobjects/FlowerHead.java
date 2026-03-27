package comp3170.week5.sceneobjects;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import comp3170.GLBuffers;
import comp3170.SceneObject;
import comp3170.Shader;
import comp3170.ShaderLibrary;

import static org.lwjgl.opengl.GL41.*;

public class FlowerHead extends SceneObject {
	
	private static final String VERTEX_SHADER = "vertex.glsl";
	private static final String FRAGMENT_SHADER = "fragment.glsl";
	private Shader shader;

	private Vector3f petalColour = new Vector3f(1.0f,1.0f,1.0f);

	private Vector4f[] vertices;
	private int vertexBuffer;
	
	private float innerRadius = 0.15f;
	private float outerRadius = 0.3f;

	public FlowerHead(int nPetals, Vector3f colour) {
		
		// TODO: Create the flower head. (TASK 1)
		// Consider the best way to draw the mesh with the nPetals input. 
		// Note that this may involve moving some code OUT of this class!
		
		shader = ShaderLibrary.instance.compileShader(VERTEX_SHADER, FRAGMENT_SHADER);		
		
		petalColour = colour;
		
		vertices = new Vector4f[2 * nPetals + 2];

	    vertices[0] = new Vector4f(0, 0, 0, 1);

	    float angleStep = (float)(2 * Math.PI / nPetals);

	    int index = 1;

	    for (int i = 0; i < nPetals; i++) {
	        float outerAngle = i * angleStep;
	        float innerAngle = outerAngle + angleStep / 2.0f;

	        // outer vertex
	        float outerX = (float)Math.cos(outerAngle) * outerRadius;
	        float outerY = (float)Math.sin(outerAngle) * outerRadius;
	        vertices[index++] = new Vector4f(outerX, outerY, 0, 1);

	        // inner vertex
	        float innerX = (float)Math.cos(innerAngle) * innerRadius;
	        float innerY = (float)Math.sin(innerAngle) * innerRadius;
	        vertices[index++] = new Vector4f(innerX, innerY, 0, 1);
	    }

	    vertices[index] = new Vector4f(vertices[1]);

	    vertexBuffer = GLBuffers.createBuffer(vertices);
	}

	public void update(float dt) {
		// TODO: Make the flower head rotate. (TASK 5)
	}

	public void drawSelf(Matrix4f mvpMatrix) {
		// TODO: Add any appropriate draw code. (TASK 1)
		shader.enable();
		shader.setUniform("u_mvpMatrix", mvpMatrix);
		shader.setAttribute("a_position", vertexBuffer);
		shader.setUniform("u_colour", petalColour);

		glDrawArrays(GL_TRIANGLE_FAN, 0, vertices.length);
	}
}
