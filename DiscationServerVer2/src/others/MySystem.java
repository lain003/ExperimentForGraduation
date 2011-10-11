package others;

import java.awt.Image;
import java.net.Socket;
import java.util.ArrayList;


/**
 * �v���O�����S�̂Ɋւ����������߂̃N���X
 */
public class MySystem {
	private Majority majority;
	public Majority getMajority()
	{
		return majority;
	}
	
	public void newMajority()
	{
		majority = new Majority();
	}
	private Connector connector = new Connector();
	public Connector getConnector()	{
		return connector;
	}
	/**
	 * ����Server�ɉf���o����Ă���X�N���[���̃f�[�^�i���ݎg���Ă��Ȃ��B�ȈՉ�ʋ��L�p�j
	 */
	private Image screenData = null;
	public void setScreenData(Image data){
		screenData = data;
	}
	public Image getScreenData()
	{
		return screenData;
	}
}

/**
 * �N���C�A���g����̐ڑ����Ǘ����Ă���N���X
 */
class Connector{
	/**
	 * �ڑ������ۂɗ��p�����N���C�A���g��Socket�N���X��ۑ����Ă��郊�X�g
	 */
	private ArrayList<Socket> connectSocketList = new ArrayList<Socket>();
	
	public void addConnectionList(Socket addSocket)
	{
		connectSocketList.add(addSocket);
	}
	
	public ArrayList<Socket> getConnectSocketListClone()
	{
		return (ArrayList<Socket>) connectSocketList.clone();
	}
	
	/**
	 * �ڑ����Ă���l��������܂�
	 * @return
	 */
	public int getConnectorNumber()
	{
		return connectSocketList.size();
	}
}
