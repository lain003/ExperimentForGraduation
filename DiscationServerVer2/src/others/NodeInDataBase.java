package others;

import obje.Obje;

/**
 * �f�B�X�v���C�f�[�^�x�[�X�Ɋi�[�����f�[�^�BClient���炫��Node�����ɍ����
 */
public class NodeInDataBase extends Node{
	/**
	 * ClientThread�����ʂ��邽�߂̃i���o�[
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
