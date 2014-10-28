import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.*;
import com.google.gson.Gson;


public class GameCont extends BasicGame{

	Player player;
	//Enemy birdEnemy;
	menuHandler mHandler;
	static int WIDTH=1024;
	static int HEIGHT =760;
	float gravity=3.7f;
	enum gameState {StartMenu, Playing, GameMenu, Exiting};
	gameState gState;
	LevelManager levelManager;
	EnemyManager enemyManager;
	WeaponManager weaponManager;
	boolean moveCamera=false;
	Camera camera;
	HUD hud;
		
	
	public GameCont()
	{
		super("Testing Game!");
	}
	
	public static void main(String[] args) throws SlickException
	{
		AppGameContainer app=new AppGameContainer(new GameCont());
		app.setDisplayMode(800, 600, false);
		app.setVSync(true);
		app.start();
	}

	
	//Initialized variables and objects
	@Override
	public void init(GameContainer gc) throws SlickException{
		
		
		
		player=new Player();//false, 150, 300,200,96,64, "Guy2.png", "Guy2.png","Guy2.png", 3.5f, 9.0f,200.0f, 100,10); 
		//backMap=new TiledMapPlus("/FirstArea.tmx");
	//	map=new BlockMap("/FirstArea.tmx");
		
		mHandler=new menuHandler();
		levelManager=new LevelManager();
		enemyManager=new EnemyManager();
		weaponManager=new WeaponManager();
		
		Gson gson=new Gson();
		try{
			BufferedReader br=new BufferedReader(new FileReader("Player.json"));
			player=gson.fromJson(br, Player.class);
			br=new BufferedReader(new FileReader("MenuHandler.json"));
			mHandler=gson.fromJson(br, menuHandler.class);
			br=new BufferedReader(new FileReader("LevelManager.json"));
			levelManager=gson.fromJson(br, LevelManager.class);
		}
		catch (IOException e){
			e.printStackTrace();
		}
		
		levelManager.initialize();
		camera=new Camera(gc,levelManager.levels.get(levelManager.currentLevel).map);
		hud=new HUD();
		player.Initialize();
		mHandler.initialize(camera.getCameraX(), camera.getCameraY());
		weaponManager.Initialize("Weapons.json");
		enemyManager.Initialize("Enemies.json");
		weaponManager.getWeapons(0);
		enemyManager.getEnemies(0);
		}
	
	//Runs on each frame. Makes game go.
	@Override
	public void update(GameContainer gc, int delta) throws SlickException{

		Input input=gc.getInput();
		player.updatePlayer(gc, delta, levelManager.levels.get(levelManager.currentLevel).map, input, gravity);
		camera.centerOn(player.getX(), player.getY());
		weaponManager.update(gc, camera);
		weaponManager.checkForPickUp(player.objectPolygon);
		enemyManager.update(gc, camera, player.getX(), player.getY(), levelManager.levels.get(levelManager.currentLevel).map, gravity, player.height, player.width, player.objectPolygon);
		if (weaponManager.equippedWeapon !=null)
			enemyManager.checkForDamage(weaponManager.equippedWeapon.objectPolygon, player.damage);
		hud.setValues(player.getX(), player.getY(), camera.getX(), camera.getY(), player.getHealth());
		mHandler.update(input);
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		levelManager.levels.get(levelManager.currentLevel).backMap.getLayer("Back").render(0, 0, 0, 0, WIDTH/32, (WIDTH/32)*(HEIGHT/32), false, 500,500);
		//backMap.getLayer("Back").render(0, 0, 0, 0, WIDTH/32, (WIDTH/32)*(HEIGHT/32), false, 500,500);
		camera.drawMap();
		camera.translateGraphics();
		enemyManager.renderEnemies(gc, g);
		weaponManager.renderWeapons(gc,g,camera, player.getX(), player.getY(), player.getDirection(), player.width);
		player.renderPlayer(gc, g);
		hud.drawHud(g);
		mHandler.render(gc, g, camera.getCameraX(), camera.getCameraY());
	}
	

}
