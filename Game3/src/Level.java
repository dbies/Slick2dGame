import java.util.List;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMapPlus;


public class Level {
	String name;
	int levelNumber;
	float gravity;
	String blockMapFileName;
	String backMapFileName;
	List<Enemy> enemies;

	int minimumLevel;
	int maximumLevel;
	String primaryType;
	String SecondaryType;
	
	BlockMap map;
	TiledMapPlus backMap;
	
	public Level(){
		
	}
	public void initialize() throws SlickException{
		backMap=new TiledMapPlus(backMapFileName);
		map=new BlockMap(blockMapFileName);
	}
}
	