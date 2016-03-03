package com.nacs.lootgame.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.nacs.lootgame.actors.BattleActor.ActorType;
import com.nacs.lootgame.actors.Battle;
import com.nacs.lootgame.actors.BattleActor;
import com.nacs.lootgame.game.Assets;
import com.nacs.lootgame.loot.Item;
import com.nacs.lootgame.utils.Constants;


public class LootScreen extends AbstractGameScreen {
	private static final String TAG = LootScreen.class.getName();
	
	private Stage stage;
	private Skin skin;
	
	private Battle battle;
	private Table layerBattle;
	private BattleActor player;
	private ArrayList<BattleActor> enemies;
	
	private Table layerButtons;
	private TextButton b1;
	private TextButton b2;
	private TextButton b3;
	private TextButton b4;
	private TextButton b5;
	private TextButton b6;
	
	private TextButton bGetItem;
	private Window battle_message;
	private Label battle_text_label;
	
	private Image imgBackground;
	public static final int backgroundCount = 3;
	
	private boolean paused;
	private boolean button_lock;
	
	private int targetIndex = 0;
	
	public LootScreen(Game game) {
		super(game);
	}
	
	private Window buildBattleMessage() {
		Window layer = new Window("", skin);
		
		battle_text_label = new Label("", skin, "default-font", Item.BLACK);
		
		battle_text_label.setSize(345.0f, 90.0f);
		battle_text_label.setName("battle_text_label");
		//battle_text_label.setPosition(0, 0);
		
		layer.setSize(345.0f, 90.0f);
		layer.setPosition((stage.getWidth() / 2) - (345.0f / 2.0f), 0.0f);
		layer.setBackground(new Image(new Texture("images/backgrounds/battle_message_bg.png")).getDrawable());		
		
		layer.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!button_lock) {
					battle_text_label.setText("");
					battle_message.setVisible(false);
					layerButtons.setVisible(true);
				}
			}
		});
		
		layer.add(battle_text_label);
		return layer;
	}
	
	private void updateBattleMessage(String message) {
		battle_text_label.setText(message);
		battle_message.setVisible(true);
	}
	
	private void buildLootScreen() {
		
		skin = new Skin(Gdx.files.internal(Constants.SKIN_LOOTGAME_UI));
			
		// build all layers
		player = getPlayerActor();
		enemies = getEnemyActors();
		layerBattle = buildBattle();
		Table layerBackground = buildBackground();
		layerButtons = buildButtons();
		battle_message = buildBattleMessage();
		
		// set initial battle message
		String start_message = player.getName() + " vs ";
		if (enemies.size() > 1) {
			for (int i = 0; i < enemies.size() - 1; i++){
				start_message += enemies.get(i).getName() + ", ";
			}
			start_message += "and " + enemies.get(enemies.size()-1).getName() + "!";
		} else {
			start_message += enemies.get(0).getName() + "!";
		}		
		battle_text_label.setText(start_message);
		
		// assemble stage
		stage.clear();
		
		Stack stack = new Stack();		
		stage.addActor(stack);
		stack.setSize(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		stack.add(layerBackground);
		stack.add(layerButtons);
		stack.add(layerBattle);
		stage.addActor(battle_message);
		
		
	}
	
	private BattleActor getPlayerActor() {
		BattleActor actor = new BattleActor(com.nacs.lootgame.actors.BattleActor.ActorType.PLAYER, new Item(50), "Player");
		return actor;
	}
	
	private ArrayList<BattleActor> getEnemyActors() {
		int num_enemies = MathUtils.random(1,3);
		ArrayList<BattleActor> enemies = new ArrayList<BattleActor>();
		for (int i = 0; i < num_enemies; i++){
			enemies.add(new BattleActor(com.nacs.lootgame.actors.BattleActor.ActorType.ENEMY00, new Item(50), "Enemy"+i));
		}
		return enemies;
	}
	
	private Table buildBackground() {
		Table layer = new Table();
		
		String bgPath = "images/backgrounds/bg";
		int bgNum = (int) MathUtils.random(0, backgroundCount-1);
		bgPath += bgNum + ".png";
		imgBackground = new Image(new Texture(bgPath));
		layer.addActor(imgBackground);
		return layer;
	}
	
	private Table buildButtons() {
		Table layer = new Table();
		
		// action button width & height
		int bWidth = 150;
		int bHeight = 45;
		
		// extra button width & height
		int bxWidth = bHeight;
		int bxHeight = bxWidth; // square
		
		float x = stage.getWidth() / 2;
		float y = stage.getHeight() / 2;
		
		// get item button
		/*
		bGetItem = new TextButton("Get Item", skin, "default");
		bGetItem.setHeight(75);
		bGetItem.setWidth(200);
		bGetItem.setPosition(x-100, y-37);
		bGetItem.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				Item item = new Item(50);
				Window itemWindow = item.getItemWindow();
				//bGetItem.setVisible(false);
				stage.addActor(itemWindow);
				itemWindow.setPosition(650, 150);
			}
		});
		layer.addActor(bGetItem);
		*/
		// define action buttons
		b1 = new TextButton("Button01", skin, "default");
		b2 = new TextButton("Button02", skin, "default");
		b3 = new TextButton("Button03", skin, "default");
		b4 = new TextButton("Button04", skin, "default");
		
		
		ArrayList<TextButton> buttons = new ArrayList<TextButton>();
		buttons.add(b1); buttons.add(b2); buttons.add(b3); buttons.add(b4);
			
		// set action button parameters
		b1.setPosition(x - bWidth,  bHeight);
		b2.setPosition(x - bWidth, 0);
		b3.setPosition(x, bHeight);
		b4.setPosition(x, 0);
		
		for (TextButton b : buttons) {
			b.setHeight(bHeight);
			b.setWidth(bWidth);
			layer.addActor(b);
		}
		
		b1.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				lockButtons();
				String battle_message = battle.actionOne(player, layerBattle.findActor(player.getName()), enemies.get(0), layerBattle.findActor(enemies.get(0).getName()));
				delayButtons(Battle.action_durations[0]);
				layerButtons.setVisible(false);
				updateBattleMessage(battle_message);
				
			}
		});
		
		// define extra buttons
		b5 = new TextButton("B05", skin, "default");
		b6 = new TextButton("B06", skin, "default");
		
		b5.setHeight(bxHeight);
		b5.setWidth(bxWidth);
		b6.setHeight(bxHeight);
		b6.setWidth(bxWidth);
		
		b5.setPosition(x + bWidth, bHeight);
		b6.setPosition(x + bWidth, 0);
		layer.addActor(b5);
		layer.addActor(b6);
		
		return layer;
		
	}
		
	private Table buildBattle() {
		battle = new Battle(player, enemies);
		
		Table layer = new Table();
		Group playerGroup = player.getActorGroup();
		
		for (BattleActor enemy : enemies) {
			layer.addActor(enemy.getActorGroup());
		}
		
		float enemyWidth = 0.0f;
		float enemyHeight = 0.0f;
		for (Actor enemy : layer.getChildren()){
			enemyWidth += enemy.getWidth();
			enemyHeight += enemy.getHeight();
		}
		enemyWidth = enemyWidth / layer.getChildren().size;
		enemyHeight = enemyHeight / layer.getChildren().size;
		
		layer.setSize(stage.getWidth(), stage.getHeight());
		layer.setPosition(0.0f, 0.0f);
		
		playerGroup.setPosition((stage.getWidth() / 4) - (playerGroup.getWidth() / 2), (stage.getHeight() / 2) - (playerGroup.getHeight() / 2));
		System.out.println("playerX: " + playerGroup.getX() + "\nplayerY: " + playerGroup.getY());
		float enemyX = ((stage.getWidth() / 4) * 3) - (enemyWidth / 2);
		float enemyY = (stage.getHeight() / 2) - (enemyHeight / 2);
		for (Actor enemy : layer.getChildren()) {
			enemy.setPosition(enemyX, enemyY);
			enemy.setName(enemy.getName());
			enemyX += enemy.getWidth();
		}
		layer.addActor(playerGroup);
		layer.setName("battle");
		return layer;
	}
	
	public void delayButtons(float delay) {
		layerButtons.addAction(Actions.sequence(Actions.delay(delay), Actions.run(new Runnable() {
			public void run() {
				unlockButtons();
			}
		})));
	}
	
	public void lockButtons(){
		button_lock = true;
	}
	
	public void unlockButtons(){
		button_lock = false;
	}
	
	@Override
	public void render(float deltaTime) {
		// no update when paused
		if (!paused) {
			
		}
		// set clear screen color
		Gdx.gl.glClearColor(0x64/255.0f, 0x95/255.0f, 0xed/255.0f, 0xff/255.0f);
		// clear the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// render game world to screen
		stage.act(deltaTime);
		stage.draw();		
	}
	
	@Override
	public void resize(int width, int height) {
	}
	
	@Override
	public void show() {
		Gdx.input.setCatchBackKey(true);
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		buildLootScreen();
		layerButtons.setVisible(false);
		battle_message.setVisible(true);
		lockButtons();
		battle_message.addAction(Actions.sequence(Actions.delay(1.0f), Actions.run(new Runnable() {
		    public void run () {
		        battle_message.setVisible(false);
		        unlockButtons();
		        layerButtons.setVisible(true);
		    }
		})));
	}
	
	@Override
	public void hide() {
		Gdx.input.setCatchBackKey(false);
	}
	
	@Override
	public void pause() {
		paused = true;
	}
	
	@Override
	public void resume() {
		super.resume();
		// only called on android
		paused = false;
	}
}
