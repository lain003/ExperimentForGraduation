package others;

import java.util.ArrayList;
import java.util.Vector;

/**
 * AgendaDataBaae‚ÉŠi”[‚³‚ê‚éNode
 */
public class AgendaNode {
	/**
	 * ‹c‘è
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
	 * ‹c‘è‚É‘Î‚µ‚Ä‚ÌƒRƒƒ“ƒg‚ğ•Û‘¶‚·‚é(Œ»İ‚Íg‚í‚ê‚Ä‚¢‚È‚¢)
	 */
	private ArrayList<String> commentList = new ArrayList<String>();
	public void addCommentList(String s){
		commentList.add(s);
	}
	public ArrayList<String> getCommentList(){
		return commentList;
	}
}
