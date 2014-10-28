
public class aiState {
	String name;
	int likelihood;
	
	public aiState(){
		name="";
		likelihood=0;
	}
	public aiState(String nm){
		name=nm;
		likelihood=0;
	}
	public aiState(String nm, int likeHood){
		name=nm;
		likelihood=likeHood;
	}
	public void incLikelihood(){
		likelihood++;
	}
	public void decLikelihood(){
		likelihood--;
	}
	public int getLikelihood(){
		return likelihood;
	}
	public void resetLikelihood(){
		likelihood=0;
	}
}
