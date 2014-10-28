import java.util.List;

import org.newdawn.slick.SlickException;


public class LevelManager {
	int currentLevel;
	List<Level> levels;
	
	
	public LevelManager(){
		
	}
	public void initialize() throws SlickException{
		for (int i=0; i<levels.size();i++){
			levels.get(i).initialize();
		}
	}
}
