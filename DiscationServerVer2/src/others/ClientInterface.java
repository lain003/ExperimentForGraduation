package others;

import java.util.ArrayList;

import obje.*;

/**
 * Clinetが送ってきたパケットを元にNodeとOrderを作成する。つまりはUIのDataBase管理をこいつが行うと考えればよい
 * action:
 */
public class ClientInterface {
	String action;
	int jpgNumber;
	Pointing pointing;
	DirectionAction direcAction;
	byte[] jpgData;

	Node createNode;
	Majority majority;
	AgendaPacket agendaPacket;
	public AgendaPacket getAgendaPacket(){
		return agendaPacket;
	}

	/**
	 * 削除するときの対象になるID
	 */
	ID delId;
	public ID getDelId() {
		return delId;
	}

	/**
	 * objecがMajorityの場合のデータ部
	 */
	char dataOfMajority;

	/**
	 * このクラスはこの中身を参照することはあっても、変更してはいけません
	 */
	DataBase dataBase;

	/**
	 * orderを受ける対象(旧RequestHeaderのIdentNumberに対応するもの)
	 */
	int objec;
	/**
	 * Create or Edit or Deleate
	 */
	int order;

	public ClientInterface(DataBase dis){
		dataBase = dis;

		action = null;
		jpgNumber = 0;
		direcAction = null;
		pointing = null;
		majority = null;
		jpgData = null;
	}

	public char getDataOfMajority(){
		return dataOfMajority;
	}

	/**
	 * メンバからNodeを作成し、それをcreateNodeにセットします。
	 */
	public void createNode(){
		if(order == Define.order.CREATE){
			createNode = new Node();

			if(action != null){
				ID setId = new ID();
				setId.setDepth(1);////////
				ArrayList<Integer> setIdLocation = setId.getLocation();
				setIdLocation.add(1);///////

				MyCharacter setObje = new MyCharacter();
				setObje.setChars(action,50,50);

				createNode.setMember(Define.obje.CHARACTER, setId, setObje);

				action = null;
			}
			else if(jpgNumber != 0){
				ID setId = new ID();
				setId.setDepth(0);/////////
				//byte[] jpgData = Define.readJpg(jpgNumber);
				byte[] jpgData = Define.readJpg();
				JPG jpg = new JPG();
				jpg.setToMember(jpgData);

				createNode.setMember(Define.obje.JPG, setId, jpg);

				jpgNumber = 0;
			}
			else if(jpgData != null){
				ID setId = new ID();
				setId.setDepth(0);/////////
				JPG jpg = new JPG();
				jpg.setToMember(jpgData);

				createNode.setMember(Define.obje.JPG, setId, jpg);

				jpgData = null;
			}
			else if(pointing != null){
				ID setId = new ID();
				setId.setDepth(1);////////
				ArrayList<Integer> setIdLocation = setId.getLocation();
				setIdLocation.add(1);///////

				createNode.setMember(Define.obje.POINTING, setId, pointing); 
			}
			else
			{
				//System.out.println("ClientInterface:createNodeError");
			}
		}
		else if(order == Define.order.EDIT){
			if(jpgData != null){//setでcreteNodeに代入しているので不要
				jpgData = null;
			}
			/**
			 * 変更したpointingを元に、新しくNodeを作成します（こいつは前に作ったaddNodeに変更があると、正常に動きません）
			 */
			else if(pointing != null){
				ID oldId = createNode.getID();
				createNode.setMember(Define.obje.POINTING, oldId, pointing);
			}
			else
			{
				//未実装
			}
		}
	}

	/**
	 * Clinetから送られてきたデータを元に、メンバをセットします。ここでいうメンバにセットする内容は、擬似的なリクエストヘッダのような物。つまりこのメソッドの中身はClientが実行するべき操作も混じっている
	 * @param headerData socketから読み込んだByteデータ
	 */
	public void setMember(byte[] headerData)
	{	
		if(headerData[4] == 'Z')//指の方向
		{
			objec = Define.objec.NODE;
			order = Define.order.CREATE;
			char[] dataChar = new char[headerData.length - 5];
			for(int i = 0;i < dataChar.length;i++)
			{
				dataChar[i] = (char)headerData[i + 5];
			}
			for(int i = 1;i < headerData.length;i++)
			{
				action = String.valueOf(dataChar);
			}
		}
		else if(headerData[4] == 'A')//agenda
		{
			objec = Define.objec.AGENDA;
			order = Define.order.CREATE;
			
			AgendaNumberDataBase numberDataBase = dataBase.getAgendaDataBase().getAgendaNumberDataBase();
			
			ID searchID = numberDataBase.searchDataBase((headerData[5] & 0xFF));
			if(searchID == null){
				System.out.println("No ID in numberDataBase");
			}
			else{
				agendaPacket = new AgendaPacket();

				ID id = agendaPacket.getID();
				//id = searchID;
				id.setDepth(searchID.getDepth());
				id.setId(searchID.getId());
				ArrayList<Integer> idList = id.getLocation();
				idList.clear();
				ArrayList<Integer> searchList = searchID.getLocation();
				for(int i = 0;i < searchList.size();i++){
					idList.add(searchList.get(i));
				}
				
				String s = new String();
				for(int i = 0;i < headerData.length - 6;i++){
					s += (char)headerData[6 + i];
				}
				agendaPacket.setAgenda(s);
			}

			/*agendaPacket = new AgendaPacket();

			ID id = agendaPacket.getID();
			id.setDepth((headerData[5] & 0xFF));
			ArrayList<Integer> idLocation = id.getLocation();
			for(int i = 0;i < id.getDepth();i++)
			{
				idLocation.add((headerData[5 + 1 + i] & 0xFF));
			}

			int stringDepth = (headerData[6 + id.getDepth() - 1 + 1] & 0xFF);
			String s = new String();
			for(int i = 0;i < stringDepth;i++){
				s += (char)headerData[6 + id.getDepth() - 1 + 2 + i];
			}
			agendaPacket.setAgenda(s);*/
		}
		else if(headerData[4] == 'J'){//jpgData(byte)
			if(headerData[5] == 'E')//もし編集なら
			{
				objec = Define.obje.JPG;
				//objec = Define.obje.NODE;
				order = Define.order.EDIT;

				int jpgLength = (headerData[6] & 0xFF)  + (headerData[7] & 0xFF) * 255 + (headerData[8] & 0xFF) * 255 * 255 + (headerData[9] & 0xFF) * 255 * 255 * 255;
				jpgData = new byte[jpgLength];
				for(int i = 0;i < jpgLength;i++){
					jpgData[i] = headerData[10 + i];
				}
				createNode = new Node();
				ID createID = new ID();
				int idLength = (headerData[10 + jpgLength + 1] & 0xFF);
				ArrayList<Integer> idLocation = createID.getLocation();
				for(int i = 0;i < idLength - 1;i++){
					idLocation.add(headerData[10 + jpgLength + 2 + i] & 0xFF);
				}
				createID.setId((headerData[10 + jpgLength + 2 + idLength - 1] & 0xFF));
				JPG jpg = new JPG();
				jpg.setToMember(jpgData);

				createNode.setMember(Define.obje.JPG, createID, jpg);
			}
			else//作成なら
			{
				objec = Define.obje.JPG;
				order = Define.order.CREATE;

				int length = (headerData[5] & 0xFF)  + (headerData[6] & 0xFF) * 255 + (headerData[7] & 0xFF) * 255 * 255 + (headerData[8] & 0xFF) * 255 * 255 * 255;
				jpgData = new byte[length];
				for(int i = 0;i < jpgData.length;i++)
				{
					jpgData[i] = headerData[9 + i];
				}
			}
		}
		else if(headerData[4] == 'S')//Pointing Start
		{
			objec = Define.objec.NODE;
			order = Define.order.CREATE;
			pointing = new Pointing();
			pointing.setR((headerData[5] & 0xFF) + (headerData[6] & 0xFF) * 255);
		}
		else if(headerData[4] == 'P')//Pointing
		{
			objec = Define.objec.NODE;
			order = Define.order.EDIT;
			ArrayList<Coordinate> coordinateList = pointing.getCoordinateList();
			Coordinate addCoordinate = new Coordinate();
			addCoordinate.x = (headerData[5] & 0xFF)  + (headerData[6] & 0xFF) * 255;
			addCoordinate.y = (headerData[7] & 0xFF)  + (headerData[8] & 0xFF) * 255;
			coordinateList.add(addCoordinate);

			System.out.println("X + " + addCoordinate.x + " Y + " + addCoordinate.y);///////////////
		}
		else if(headerData[4] == 'M')//Majority
		{
			objec = Define.objec.SYSTEM;
			if(headerData[5] == 'R'){//Reset
				order = Define.order.MAJORITYRESET;
			}
			else{
				Majority dataBaseMajority = dataBase.getMySystem().getMajority();
				if(dataBaseMajority == null)//もし過去に統計をとっていなかったら
				{
					order = Define.order.MAJORITYCREATE;

					dataOfMajority = (char) headerData[5];
				}
				else
				{
					order = Define.order.MAJORITYADD;
					dataOfMajority = (char) headerData[5];
				}
			}
		}
		else if(headerData[4] == 'R')//Remove
		{
			objec = Define.objec.NODE;
			order = Define.order.DELETE;

			Coordinate searchCoordinate = new Coordinate();
			searchCoordinate.x = (headerData[5] & 0xFF)  + (headerData[6] & 0xFF) * 255;
			searchCoordinate.y = (headerData[7] & 0xFF)  + (headerData[8] & 0xFF) * 255;
			System.out.println("DeleteX " + searchCoordinate.x + "DeleteY " + searchCoordinate.y);/////////////////
			delId = dataBase.getIdToHitObject(searchCoordinate, 0);
			System.out.println("delId = " + delId.getId());/////////////////
		}
		else//Jpg
		{
			objec = Define.obje.JPG;
			order = Define.order.CREATE;
			if(headerData.length == 5){
				jpgNumber = new Integer(headerData[4] - '0');
			}
			else if(headerData.length == 6){
				jpgNumber = new Integer(headerData[5] - '0');
				int i = new Integer(headerData[4] - '0');
				jpgNumber += i*10;
			}
			else if(headerData.length == 7){
				jpgNumber = new Integer(headerData[6] - '0');
				int i = new Integer(headerData[5] - '0');
				jpgNumber += i*10;
				int i2 = new Integer(headerData[4] - '0');
				jpgNumber += i2*100;
			}
			else if(headerData.length == 8){
				jpgNumber = new Integer(headerData[7] - '0');
				int i = new Integer(headerData[6] - '0');
				jpgNumber += i*10;
				int i2 = new Integer(headerData[5] - '0');
				jpgNumber += i2*100;
				int i3 = new Integer(headerData[4] - '0');
				jpgNumber += i3*1000;
			}
		}

		createNode();
	}

	public int getObjec()
	{
		return objec;
	}

	/**
	 * デバック用の表示関数
	 */
	public void printMember()
	{
		System.out.println("Action = " + action);
		System.out.println("JpgNumber = " + jpgNumber);
	}

	public int getOrder()
	{
		return order;
	}

	public Node getCreateNode()
	{
		return createNode;
	}
}
