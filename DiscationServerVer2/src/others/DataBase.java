package others;
import java.util.*;
import obje.*;
import obje.Obje;


/**
 * �l�X��DataBae���i�[���Ă���
 */
public class DataBase {

	/**
	 * database���ύX���ꂽ���ǂ������������߂̃t���O
	 */
	public boolean changeFlag = false;
	
	private Vector<Vector> displayDataBase = new Vector<Vector>();//�����I�ȕ��ɂ��邽�߂�Vector
	/**
	 * dataBase�̃N���[�����쐬���ĕԂ��܂��B
	 * ���g��ύX�������ꍇ�́A�ʂ�API���g���Ă�������
	 * @return �쐬�����N���[����Ԃ��܂��B
	 */
	public Vector getDataBaseClone(){
		Vector cloneDataBase = (Vector) displayDataBase.clone();

		return cloneDataBase;
	}
	
	private Vector<OrderAndPlace> history = new Vector<OrderAndPlace>();
	
	private MySystem mySystem = new MySystem();
	public MySystem getMySystem(){
		return mySystem;
	}
	
	private AgendaDataBase agendaDataBase = new AgendaDataBase();
	public AgendaDataBase getAgendaDataBase(){
		return agendaDataBase;
	}
	public void addMajority(char vote){
		Majority addMajority = mySystem.getMajority();
		addMajority.addVote(vote);
		changeFlag = true;
	}
	public void createMajority(char vote){
		mySystem.newMajority();
		Majority newMajority = mySystem.getMajority();

		newMajority.addVote(vote);
		changeFlag = true;
	}
	public void resetMajority(){
		Majority majorityBase = mySystem.getMajority();
		majorityBase.reset();
		changeFlag = true;
	}

	/**
	 * DataBase��Node��o�^����B
	 * �܂��o�^����ID�����蓖�Ă���(����)�B���̂��߈�����Node�̒��g�̓Z�b�g��̒l�ɕύX����܂��B
	 * �܂����ł�ID�̒l�����܂��Ă�����̂𓊂��Ȃ��悤�ɂ��Ă��������B
	 * JPG�͓���ŁALocation��{1}�̏ꍇ�́A1-?(���蓖�Ă�ꂽ�ԍ�)+ID(1�Œ�)�ƂȂ�B�܂�JPG�ɂ����ẮA�Ō�̃��P�[�V�����͊��蓖�Ă��镨�ƂȂ�
	 * @param node �o�^������Node 
	 * @return �o�^����ID��Ԃ�
	 */
	public ID registrationNode(Node node,int userNumber)
	{
		ID id = node.getID();
		if(id.getId() != 255)
		{
			System.out.println("���ł�ID���o�^����Ă��܂� " + id.getId());
		}

		ArrayList<Integer> idLocation = id.getLocation();
		Vector tempVector = displayDataBase;
		if(node.getIdentNumber() == Define.obje.JPG)//JPG�Ȃ�
		{
			for(int i = 0;i < idLocation.size();i++)
			{
				tempVector = (Vector)tempVector.get(idLocation.get(i) - 1);
			}
			Vector newVec = new Vector();
			NodeInDataBase nodeDataBase = new NodeInDataBase();
			nodeDataBase.setMember(node,userNumber);
			newVec.add(nodeDataBase);
			tempVector.add(newVec);
			id.setDepth(id.getDepth() + 1);
			idLocation.add(tempVector.size());
			id.setId(1);
			//System.out.println("JPGID = " + id.getDepth() + " " + id.getId());///////////////////
		}
		else
		{
			for(int i = 0;i < idLocation.size();i++)
			{
				tempVector = (Vector)tempVector.get(idLocation.get(i) - 1);
			}
			NodeInDataBase nodeDataBase = new NodeInDataBase();
			nodeDataBase.setMember(node,userNumber);
			tempVector.add(nodeDataBase);
			id.setId(tempVector.size());
			if(id.getId() == 1)
			{
				System.out.println("JPG�ȊO�̕���ID��,�P�����蓖�Ă��܂���");
			}
		}

		OrderAndPlace addOrderAndPlace = new OrderAndPlace();//����ۑ�
		addOrderAndPlace.setMember(Define.order.CREATE, id);
		history.add(addOrderAndPlace);

		System.out.println("�f�[�^�x�[�X�Ƀf�[�^��o�^���܂��� tempVectorSize = " + tempVector.size());

		changeFlag = true;

		return id;
	}

	/**
	 * DataBase��Node�̓��e�������̕��ɕύX���܂��B�ύX����ꏊ�͈�����Node��ID�ɏ����Ă���ꏊ��ύX���܂�
	 * @param node �ύX������Node���w�肵�܂��B
	 */
	public void EditNode(Node node,int userNumber)
	{
		ID id = node.getID();
		if(id.getId() == 0)
		{
			System.out.println("ID���o�^����Ă��܂���");
		}

		ArrayList<Integer> idLocation = id.getLocation();
		Vector tempVector = displayDataBase;
		for(int i = 0;i < idLocation.size();i++)
		{
			tempVector = (Vector)tempVector.get(idLocation.get(i) - 1);
		}
		NodeInDataBase nodeDataBase = new NodeInDataBase();
		nodeDataBase.setMember(node,userNumber);
		tempVector.set(id.getId() - 1, nodeDataBase);

		OrderAndPlace addOrderAndPlace = new OrderAndPlace();//����ۑ�
		addOrderAndPlace.setMember(Define.order.EDIT, id);
		history.add(addOrderAndPlace);

		changeFlag = true;
	}

	public void DelateNode(ID delID)
	{
		ArrayList<Integer> idLocation = delID.getLocation();
		Vector tempVector = displayDataBase;
		for(int i = 0;i < idLocation.size();i++)
		{
			tempVector = (Vector)tempVector.get(idLocation.get(i) - 1);
		}
		tempVector.remove(delID.getId() - 1);//////////////�����Ă�ID�͉͌���

		OrderAndPlace addOrderAndPlace = new OrderAndPlace();//����ۑ�
		addOrderAndPlace.setMember(Define.order.DELETE, delID);
		history.add(addOrderAndPlace);

		changeFlag = true;
	}

	/**
	 * �f�[�^�x�[�X�ɓo�^����Ă���Node��Ԃ��܂�
	 * @param id �~����Node������ID���w�肵�܂�
	 * @return dataBase��������Node�ł�
	 */
	public NodeInDataBase getNodeToID(ID id){
		if(id.getId() == 255){
			System.out.println("getNodeToID Error");
			return null;
		}

		ArrayList<Integer> idLocation = id.getLocation();
		Vector cloneDataBase = (Vector) displayDataBase.clone();
		for(int i = 0;i < id.getDepth();i++){
			cloneDataBase = (Vector) cloneDataBase.get(idLocation.get(i) - 1);
		}
		NodeInDataBase returnNode = (NodeInDataBase)cloneDataBase.get(id.getId() - 1);
		return returnNode;
	}

	/**
	 * Id��location����K�؂�Number�����肵����U��
	 * @return ����U����number��Ԃ��B0��Error 
	 */
	int numberAllot(ID id)
	{	
		ArrayList<Integer> idLocation = id.getLocation();

		Vector vectorInDataBase = displayDataBase;
		for(int i = 0;i < idLocation.size();i++)
		{
			vectorInDataBase = displayDataBase.get(idLocation.get(i-1));
		}

		return vectorInDataBase.size();
	}



	public Vector<OrderAndPlace> getHistoryClone()
	{
		Vector<OrderAndPlace> historyClone = (Vector<OrderAndPlace>) history.clone();
		return historyClone;
	}

	public int getHistoryLength()
	{
		return history.size();
	}

	/**
	 * dataBase���̈����ɂ���Ďw�肳�ꂽ�X�R�[�v���܂�Object�́AID��Ԃ��܂�
	 * @param searchCoordinate �T������Coordinate���w�肵�܂�
	 * @param dataBaseLine dataBase�̉���ڂ�T�����w�肵�܂�(���݂̎d�l�ł�1Line�����Ȃ��̂ŁA�O��)
	 * @return searchCoordinate���܂�Object��ID�ł�
	 */
	public ID getIdToHitObject(Coordinate searchCoordinate,int dataBaseLine)
	{	
		Vector getLine = displayDataBase.get(dataBaseLine);

		for(int i = 0;i < getLine.size();i++)
		{
			Object temp = getLine.get(i);
			Node tempNode = (Node) temp;
			Obje getObje = tempNode.getBody();
			Coordinate[] getCoordinate = getObje.getScope();
			for(int j = 0;j < getCoordinate.length;j++)
			{
				if(true == getCoordinate[j].compare(searchCoordinate))
				{
					System.out.println("DataBase-getIdToHitObject:FindID");
					return tempNode.getID();
				}
			}
		}
		System.out.println("DataBase-getIdToHitObject:NotFindID");
		return null;
	}
}


