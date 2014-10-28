import java.util.ArrayList;
import java.util.List;


public class PositionTree {
	PositionTree leftTree;
	PositionTree rightTree;
	PositionTreeNode positionTreeNode;
	
	public PositionTree(){
		leftTree=null;
		rightTree=null;
		positionTreeNode=null;

	}
	public void addNode(PositionTreeNode pTreeNode){
		if (positionTreeNode==null){
			positionTreeNode.copy(pTreeNode);
		}
		else if (positionTreeNode.weaponInfo.x >pTreeNode.weaponInfo.x){
			leftTree.addNode(pTreeNode);
		}
		
		else if (rightTree.positionTreeNode.weaponInfo.x<pTreeNode.weaponInfo.x){
			rightTree.addNode(pTreeNode);
		}
				
	}
	//public List<PositionTreeNode> getVisible(float x, float y){
//		List<PositionTreeNode> visibleList=new ArrayList<PositionTreeNode>();
//		
//	}
}
