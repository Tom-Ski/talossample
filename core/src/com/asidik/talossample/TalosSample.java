package com.asidik.talossample;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.talosvfx.talos.runtime.ParticleEffectDescriptor;
import com.talosvfx.talos.runtime.ParticleEffectInstance;
import com.talosvfx.talos.runtime.render.Particle3DRenderer;
import com.talosvfx.talos.runtime.render.p3d.Simple3DBatch;

public class TalosSample extends ApplicationAdapter {

	private PerspectiveCamera camera;
	private ParticleEffectInstance effect;

	private Particle3DRenderer defaultRenderer;

	private ShaderProgram shaderProgram;
	private FirstPersonCameraController firstPersonCameraController;

	private ShapeRenderer shapeRenderer;

	@Override
	public void create () {
		camera = new PerspectiveCamera(70, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.near = 0.01f;
		camera.far = 200;

		firstPersonCameraController = new FirstPersonCameraController(camera);
		Gdx.input.setInputProcessor(firstPersonCameraController);

		TextureRegion fireRegion = new TextureRegion(new Texture(Gdx.files.internal("fire.png")));
		TextureRegion spotRegion = new TextureRegion(new Texture(Gdx.files.internal("spot.png")));
		TextureAtlas textureAtlas = new TextureAtlas();
		textureAtlas.addRegion("fire", fireRegion);
		textureAtlas.addRegion("spot", spotRegion);

		/**
		 * Creating particle effect instance from particle effect descriptor
		 */
		ParticleEffectDescriptor effectDescriptor = new ParticleEffectDescriptor(Gdx.files.internal("test.p"), textureAtlas);
		effect = effectDescriptor.createEffectInstance();

		defaultRenderer = new Particle3DRenderer(camera);

		shaderProgram = new ShaderProgram(Gdx.files.classpath("shaders/gl2/core/particle.vert.glsl"), Gdx.files.classpath("shaders/gl2/core/particle.frag.glsl"));
		if (!shaderProgram.isCompiled()) {
			System.out.println(shaderProgram.getLog());
		}

		shapeRenderer = new ShapeRenderer();

	}

	@Override
	public void render () {
		//update
		float delta = Gdx.graphics.getDeltaTime();
		effect.update(delta);

		firstPersonCameraController.update(Gdx.graphics.getDeltaTime());

		// now to render
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		Gdx.gl.glEnable(GL20.GL_BLEND);



		final Simple3DBatch batch = defaultRenderer.getBatch();
		batch.begin(camera, shaderProgram);
		effect.render(defaultRenderer);
		batch.end();

		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.line(-10, 0, 0, 10, 0, 0);
		shapeRenderer.setColor(Color.GREEN);
		shapeRenderer.line(0, -10, 0, 0, 10, 0);
		shapeRenderer.setColor(Color.BLUE);
		shapeRenderer.line(0, 0, -10, 0, 0, 10);
		shapeRenderer.end();

	}

	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void dispose () {

	}
}
