package others;

import java.util.*;

/**
 * 議題に関するDataBase。IDの初期値は１とした
 */
public class AgendaDataBase {
	private Map<ID, AgendaNode> agendaNodeMap = Collections.synchronizedMap(new HashMap<ID, AgendaNode>());
	public Map<ID,AgendaNode> getAgendaNodeMap(){
		return agendaNodeMap;
	}
	/**
	 * AgendaDataBase内のデータがどの様に変更されたのかのログをとるクラス
	 */
	private ArrayList<AgendaLogNode> logList = new ArrayList<AgendaLogNode>();
	/**
	 * logListを何回読み込んだを数える
	 */
	protected int logListCount = 0;
	
	protected AgendaNumberDataBase agendaNumberDataBase = new AgendaNumberDataBase();
	public AgendaNumberDataBase getAgendaNumberDataBase(){
		return agendaNumberDataBase;
	}
	
	public AgendaDataBase(){
		ID id = new ID();
		id.setDepth(0);
		id.setId(1);
		
		AgendaNode addNode = new AgendaNode();
		addNode.setAgenda("　");
		this.addNode(addNode, id);
	}
	
	/**
	 * まだ読まれていないLoglist内の最新のAgendaLogNodeを返します
	 * @return 
	 */
	public AgendaLogNode getLogNode(){
		try{
			AgendaLogNode returnNode = logList.get(logListCount);
			logListCount++;
			return returnNode;
		}
		catch(Exception e){
			//e.printStackTrace();//////
			return null;
		}
	}
	
	/**
	 * DataBase内のデータが変更されたかどうかを返します
	 * @return 変更があればtrue なければfalse
	 */
	public boolean getChangeFlag(){
		if(logListCount < logList.size()){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * dataBaseに引数で指定したnodeを追加します。
	 * @param addNode 加えたいNodeを指定します
	 * @param id 加えたいIdを指定します。ただしid.idが初期値の場合は適切なIDを割り振り登録します。
	 */
	public void addNode(AgendaNode addNode,ID id){
		if(id.getId() == 255){//もしidに何も値が設定されていなかったら
			int serachDepth = id.getDepth();

			Set keySet = agendaNodeMap.keySet();
			Iterator keyIte = keySet.iterator();
			int searchCounter = 0;
			while(keyIte.hasNext()){
				ID key = (ID)keyIte.next();
				if(key.getDepth() == serachDepth){
					searchCounter++;
				}
			}

			id.setId(searchCounter + 1);
		}

		agendaNodeMap.put(id, addNode);
		logList.add(new AgendaLogNode(id,addNode));
		agendaNumberDataBase.addDataBase(id);
	}

	/**
	 * angedaNodeMapの深さを測定します（IDの中のDepthが最大の物を返します）
	 * @return 最大の深さです
	 */
	public int searchDepthest(){
		Set keySet = agendaNodeMap.keySet();
		Iterator keyIte = keySet.iterator();

		int depthest = 0;
		while(keyIte.hasNext()){
			ID key = (ID)keyIte.next();
			if(depthest <= key.getDepth()){
				depthest = key.getDepth();
			}
		}

		return depthest;
	}

	/**
	 * agendaNodeMap内で、指定されたdepthの深さに該当する列の幅をかえします(IDの中の最大のidを返します)
	 * @param searchDepth 幅を知りたい深さを指定します
	 * @return 最大の幅を返します
	 */
	public int searchWidest(int searchDepth){
		Set keySet = agendaNodeMap.keySet();
		Iterator keyIte = keySet.iterator();

		int widest = 0;
		while(keyIte.hasNext()){
			ID key = (ID)keyIte.next();
			if(searchDepth  == key.getDepth()){
				if(widest <= key.getId()){
					widest = key.getId();
				}
			}
		}

		return widest;
	}
}
