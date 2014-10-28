import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class aiHandler {
	Map<aiState, Set<aiState>> adjacency;
	int numVertices;
	int edges;
		
	public aiHandler(int numOfVerts){
		numVertices=numOfVerts;
		edges=0;
		adjacency=new HashMap<aiState, Set<aiState>>();
	}
	public aiHandler(List<aiState> aList){
		numVertices=aList.size();
		edges=0;
		adjacency=new HashMap<aiState, Set<aiState>>();
		for (int i=0; i< aList.size();i++){
			if (!adjacency.containsKey(aList.get(i))){
				adjacency.put(aList.get(i), null);
			}
		}
		//adds them to the map fine
	}
	public void addEdge(aiState source, aiState destination){
		Set<aiState> edgeList=adjacency.get(source);
		if (edgeList==null){
			edgeList=new HashSet<aiState>();
			adjacency.put(source, edgeList);
		}
		edgeList.add(destination);
	}
	public aiState getNext(aiState curState){
		List<aiState> mostLikelyNext=new ArrayList<aiState>();
		int highestLikelihood=-1;
		
		Set<aiState> curSet=adjacency.get(curState);
		for (Iterator<aiState> it=curSet.iterator();it.hasNext(); ){
			aiState nState=it.next();
			if (nState.getLikelihood()>highestLikelihood){
				highestLikelihood=nState.getLikelihood();
				mostLikelyNext.clear();
				mostLikelyNext.add(nState);
				}
			else if (nState.getLikelihood()==highestLikelihood){
				mostLikelyNext.add(nState);
			}
		}
		
		Random randGen=new Random();
		int ind=randGen.nextInt(mostLikelyNext.size());
		return mostLikelyNext.get(ind);
	}
	public void increaseLikelihood(aiState curState, String nameToIncrease){
		Set<aiState> curSet=adjacency.get(curState);
		for (Iterator<aiState> it=curSet.iterator();it.hasNext(); ){//iterating through the set
			aiState nState=it.next(); //each element of set
			if (nState.name==nameToIncrease){
				nState.incLikelihood();
				break;
			}
		}
	}
	public void decreaseLikelihood(aiState curState,String nameToDecrease){
		Set<aiState> curSet=adjacency.get(curState);
		for (Iterator<aiState> it=curSet.iterator();it.hasNext(); ){//iterating through the set
			aiState nState=it.next(); //each element of set
			if (nState.name==nameToDecrease){
				nState.decLikelihood();
				break;
			}
		}
	}
		
	public int showLikelihood(aiState curState,String state){
		Set<aiState> curSet=adjacency.get(curState);
		for (Iterator<aiState> it=curSet.iterator();it.hasNext(); ){//iterating through the set
			aiState nState=it.next(); //each element of set
			if (nState.name==state){
				return nState.likelihood;
			}
		}
		return -100;
	}
	public void resetLikelihood(aiState curState, String nameToReset){
		Set<aiState> curSet=adjacency.get(curState);
		for (Iterator<aiState> it=curSet.iterator();it.hasNext(); ){//iterating through the set
			aiState nState=it.next(); //each element of set
			if (nState.name==nameToReset){
				nState.resetLikelihood();
				break;
			}
		}
	}
}
