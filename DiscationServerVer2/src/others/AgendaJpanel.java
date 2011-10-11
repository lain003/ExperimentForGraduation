package others;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class AgendaJpanel extends JPanel{
	AgendaDataBase agendaDataBase;
	DefaultMutableTreeNode root;

	int jFrameSizeX;
	int jFrameSizeY;

	private static final long serialVersionUID = 1L;

	public AgendaJpanel(DataBase ArrayDataBase,int frameSizeX,int frameSizeY,DefaultMutableTreeNode r){
		agendaDataBase = ArrayDataBase.getAgendaDataBase();
		jFrameSizeX = frameSizeX;
		jFrameSizeY = frameSizeY;
		root = r;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		AgendaLogNode getLogNode = agendaDataBase.getLogNode();
		if(getLogNode == null){

		}
		else{
			addTree(getLogNode);
		}
	}


	/**
	 * 引数で指定したNodeを、木構造表示の元になっているrootに追加します
	 * @param node 追加したいNode
	 */
	public void addTree(AgendaLogNode node){
		ID id = node.id;
		if(id.getDepth() == 0){
			String s = node.agendaNode.getAgenda() + " Number:" + agendaDataBase.getAgendaNumberDataBase().searchDataBase(id);
			root.setUserObject(s);
		}
		else{
			String s = node.agendaNode.getAgenda() + " Number:" + agendaDataBase.getAgendaNumberDataBase().searchDataBase(id);
			DefaultMutableTreeNode addNode = new DefaultMutableTreeNode(s);
			ArrayList<Integer> idLocation = id.getLocation();
			DefaultMutableTreeNode targetNode = root;
			for(int i = 0; i < id.getDepth()-1;i++){
				targetNode = (DefaultMutableTreeNode)targetNode.getChildAt(idLocation.get(i) - 1);
			}
			targetNode.insert(addNode, id.getId()-1);
		}
	}

	public void paintAgendaJpanel(Graphics g){

		//		if(agendaDataBase.searchDepthest() != 0)
		//		{
		//			int charBoxSizeX =  jFrameSizeX / agendaDataBase.searchDepthest();
		//
		//			g.setColor(Color.RED);
		//
		//			Map<ID,AgendaNode> agendaMap = agendaDataBase.getAgendaNodeMap();
		//			Set keySet = agendaMap.keySet();
		//			Iterator keyIte = keySet.iterator();
		//			while(keyIte.hasNext()){
		//				int charSize = 36;
		//				ID key = (ID)keyIte.next();
		//				AgendaNode getNode = agendaMap.get(key);
		//				g.setFont(new Font("Serif", Font.BOLD, charSize));
		//
		//				int startX = (key.getDepth() - 1) * charBoxSizeX;
		//				int endX = startX + charBoxSizeX;
		//				int wide = agendaDataBase.searchWidest(key.getDepth());
		//				int charBoxSizeY = jFrameSizeY / wide;
		//				int startY = (key.getId() - 1) * charBoxSizeY;
		//				int endY = startY + charBoxSizeY;
		//
		//				g.drawRect(startY, startX, charBoxSizeY, charBoxSizeX);
		//				g.drawString(getNode.getAgenda(), (startY*2 + charBoxSizeY)/2, (startX*2 + charBoxSizeX)/2);
		//			}
		//		}
	}
}
