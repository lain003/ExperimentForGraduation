package others;

import java.util.ArrayList;
import java.util.Vector;

/**
 * AgendaDataBaaeに格納されるNode
 */
public class AgendaNode {
	/**
	 * 議題
	 */
	protected String agenda = null;
	public String getAgenda()
	{
		return agenda;
	}
	public void setAgenda(String s){
		agenda = s;
	}
	
	/**
	 * 議題に対してのコメントを保存する(現在は使われていない)
	 */
	private ArrayList<String> commentList = new ArrayList<String>();
	public void addCommentList(String s){
		commentList.add(s);
	}
	public ArrayList<String> getCommentList(){
		return commentList;
	}
}
