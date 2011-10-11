package others;

import obje.Obje;

/**
 * ディスプレイデータベースに格納されるデータ。ClientからきたNodeを元に作られる
 */
public class NodeInDataBase extends Node{
	/**
	 * ClientThreadを識別するためのナンバー
	 */
	private int userNumber;
	public int getUserNumber(){
		return userNumber;
	}
	
	public void setMember(Node node,int usernumber)
	{
		super.setMember(node.identNumber, node.id, node.body);
		this.userNumber = usernumber;
	}
}
