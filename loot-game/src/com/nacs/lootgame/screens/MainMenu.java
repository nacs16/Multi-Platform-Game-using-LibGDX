package com.nacs.lootgame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nacs.lootgame.game.Assets;
import com.nacs.lootgame.utils.Constants;

public class MainMenu extends AbstractGameScreen{
	private static final String TAG = MainMenu.class.getName();
		
	private Stage stage;
	private Skin skin;
	
	private TextButton button01;
	private TextButton button02;
	//private Label titleMessage;
	
	
	// debug
	private final float DEBUG_REBUILD_INTERVAL = 5.0f;
	private boolean debugEnabled = false;
	private float debugRebuildStage;
	
	public MainMenu(Game game) {
		super(game);
	}
	
	private void rebuildStage() {

		int buttonWidth = 200;
		int buttonHeight = 50;
		
		skin = new Skin(Gdx.files.internal(Constants.SKIN_LOOTGAME_UI));
		
		// define stage actors
		button01 = new TextButton("Button01", skin, "default");
		button02 = new TextButton("Button02", skin, "default");
		final Label titleMessage = new Label("Title Message", skin);
		
		// set actor parameters
		button01.setWidth(buttonWidth);
		button01.setHeight(buttonHeight);
		button02.setWidth(buttonWidth);
		button02.setHeight(buttonHeight);
		
		button01.setPosition((stage.getWidth() / 2) - (button01.getWidth() / 2), (stage.getHeight() / 2) - (button01.getHeight() / 2) + (button01.getHeight() / 1.25f));
		button02.setPosition((stage.getWidth() / 2) - (button01.getWidth() / 2), (stage.getHeight() / 2) - (button01.getHeight() / 2) - (button01.getHeight() / 1.25f));
		
		titleMessage.setPosition((stage.getWidth() / 2) - (titleMessage.getWidth() / 2), (0.85f * stage.getHeight()));
		titleMessage.setVisible(true);
		
		button01.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onButton01Clicked();
			}
		});
		
		button02.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (titleMessage.isVisible()) {
					titleMessage.setVisible(false);
				} else {
					titleMessage.setVisible(true);
				}
			}
		});
		
		// add all actors
		stage.addActor(titleMessage);
		stage.addActor(button01);
		stage.addActor(button02);
	}
	
	private void onButton01Clicked() {
		game.setScreen(new LootScreen(game));
	}
	
	@Override
	public void render(float deltaTime) {
		//glClearColor expects color values between 0 and 1, so divide by 255 for desired color.
		Gdx.gl.glClearColor(20.0f/255.0f, 57.0f/255.0f, 235.0f/255.0f, 0.0f);	
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (debugEnabled) {
			debugRebuildStage -= deltaTime;
			if (debugRebuildStage <= 0) {
				debugRebuildStage = DEBUG_REBUILD_INTERVAL;
				rebuildStage();
			}
		}
		
		stage.act(deltaTime);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void show() {
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		rebuildStage();
	}

	@Override
	public void hide() {
		stage.dispose();
		skin.dispose();
	}

	@Override
	public void pause() {
		
	}
}
