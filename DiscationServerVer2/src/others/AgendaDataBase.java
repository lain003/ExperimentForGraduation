package others;

import java.util.*;

/**
 * �c��Ɋւ���DataBase�BID�̏����l�͂P�Ƃ���
 */
public class AgendaDataBase {
	private Map<ID, AgendaNode> agendaNodeMap = Collections.synchronizedMap(new HashMap<ID, AgendaNode>());
	public Map<ID,AgendaNode> getAgendaNodeMap(){
		return agendaNodeMap;
	}
	/**
	 * AgendaDataBase���̃f�[�^���ǂ̗l�ɕύX���ꂽ�̂��̃��O���Ƃ�N���X
	 */
	private ArrayList<AgendaLogNode> logList = new ArrayList<AgendaLogNode>();
	/**
	 * logList������ǂݍ��񂾂𐔂���
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
		addNode.setAgenda("�@");
		this.addNode(addNode, id);
	}
	
	/**
	 * �܂��ǂ܂�Ă��Ȃ�Loglist���̍ŐV��AgendaLogNode��Ԃ��܂�
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
	 * DataBase���̃f�[�^���ύX���ꂽ���ǂ�����Ԃ��܂�
	 * @return �ύX�������true �Ȃ����false
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
	 * dataBase�Ɉ����Ŏw�肵��node��ǉ����܂��B
	 * @param addNode ��������Node���w�肵�܂�
	 * @param id ��������Id���w�肵�܂��B������id.id�������l�̏ꍇ�͓K�؂�ID������U��o�^���܂��B
	 */
	public void addNode(AgendaNode addNode,ID id){
		if(id.getId() == 255){//����id�ɉ����l���ݒ肳��Ă��Ȃ�������
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
	 * angedaNodeMap�̐[���𑪒肵�܂��iID�̒���Depth���ő�̕���Ԃ��܂��j
	 * @return �ő�̐[���ł�
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
	 * agendaNodeMap���ŁA�w�肳�ꂽdepth�̐[���ɊY�������̕����������܂�(ID�̒��̍ő��id��Ԃ��܂�)
	 * @param searchDepth ����m�肽���[�����w�肵�܂�
	 * @return �ő�̕���Ԃ��܂�
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
