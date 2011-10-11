package others;

/**
 * AgendaDataBase内のLogListに保存する際の中身。IDとAgendaNodeをセットにしたクラス
 */
public class AgendaLogNode {
	public ID id;
	public AgendaNode agendaNode;
	
	public AgendaLogNode(ID i,AgendaNode a){
		id = i;
		agendaNode = a;
	}
}
