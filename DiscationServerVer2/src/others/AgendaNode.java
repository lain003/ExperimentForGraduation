package others;

import java.util.ArrayList;
import java.util.Vector;

/**
 * AgendaDataBaae�Ɋi�[�����Node
 */
public class AgendaNode {
	/**
	 * �c��
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
	 * �c��ɑ΂��ẴR�����g��ۑ�����(���݂͎g���Ă��Ȃ�)
	 */
	private ArrayList<String> commentList = new ArrayList<String>();
	public void addCommentList(String s){
		commentList.add(s);
	}
	public ArrayList<String> getCommentList(){
		return commentList;
	}
}
