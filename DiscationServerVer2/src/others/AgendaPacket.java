package others;

/**
 * AgendaにIDをくっつけたもの。主にデータの送信の際に、Agendaに場所を付加したいときに使う
 */
public class AgendaPacket extends AgendaNode{
	private ID id = new ID();
	public ID getID()
	{
		return id;
	}
	
	public String getAgenda()
	{
		return agenda;
	}
	
	public void setAgenda(String s)
	{
		agenda = s;
	}
}
