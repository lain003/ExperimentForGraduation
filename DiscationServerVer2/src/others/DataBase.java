package others;
import java.util.*;
import obje.*;
import obje.Obje;


/**
 * 様々なDataBaeを格納している
 */
public class DataBase {

	/**
	 * databaseが変更されたかどうかを示すためのフラグ
	 */
	public boolean changeFlag = false;
	
	private Vector<Vector> displayDataBase = new Vector<Vector>();//同期的な物にするためにVector
	/**
	 * dataBaseのクローンを作成して返します。
	 * 中身を変更したい場合は、別のAPIを使ってください
	 * @return 作成したクローンを返します。
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
	 * DataBaseにNodeを登録する。
	 * また登録時にIDを割り当てられる(強制)。そのため引数のNodeの中身はセット後の値に変更されます。
	 * またすでにIDの値が決まっているものを投げないようにしてください。
	 * JPGは特殊で、Locationが{1}の場合は、1-?(割り当てられた番号)+ID(1固定)となる。つまりJPGにおいては、最後のロケーションは割り当てられる物となる
	 * @param node 登録したいNode 
	 * @return 登録したIDを返す
	 */
	public ID registrationNode(Node node,int userNumber)
	{
		ID id = node.getID();
		if(id.getId() != 255)
		{
			System.out.println("すでにIDが登録されています " + id.getId());
		}

		ArrayList<Integer> idLocation = id.getLocation();
		Vector tempVector = displayDataBase;
		if(node.getIdentNumber() == Define.obje.JPG)//JPGなら
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
				System.out.println("JPG以外の物のIDに,１が割り当てられました");
			}
		}

		OrderAndPlace addOrderAndPlace = new OrderAndPlace();//履歴保存
		addOrderAndPlace.setMember(Define.order.CREATE, id);
		history.add(addOrderAndPlace);

		System.out.println("データベースにデータを登録しました tempVectorSize = " + tempVector.size());

		changeFlag = true;

		return id;
	}

	/**
	 * DataBaseのNodeの内容を引数の物に変更します。変更する場所は引数のNodeのIDに書いてある場所を変更します
	 * @param node 変更したいNodeを指定します。
	 */
	public void EditNode(Node node,int userNumber)
	{
		ID id = node.getID();
		if(id.getId() == 0)
		{
			System.out.println("IDが登録されていません");
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

		OrderAndPlace addOrderAndPlace = new OrderAndPlace();//履歴保存
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
		tempVector.remove(delID.getId() - 1);//////////////消してもIDは河原な

		OrderAndPlace addOrderAndPlace = new OrderAndPlace();//履歴保存
		addOrderAndPlace.setMember(Define.order.DELETE, delID);
		history.add(addOrderAndPlace);

		changeFlag = true;
	}

	/**
	 * データベースに登録されているNodeを返します
	 * @param id 欲しいNodeがあるIDを指定します
	 * @return dataBaseから会得したNodeです
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
	 * Idのlocationから適切なNumberを決定し割り振る
	 * @return 割り振ったnumberを返す。0はError 
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
	 * dataBase内の引数によって指定されたスコープを含むObjectの、IDを返します
	 * @param searchCoordinate 探したいCoordinateを指定します
	 * @param dataBaseLine dataBaseの何列目を探すか指定します(現在の仕様では1Lineしかないので、０で)
	 * @return searchCoordinateを含むObjectのIDです
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


