package others;

import java.awt.Image;
import java.net.Socket;
import java.util.ArrayList;


/**
 * プログラム全体に関わる情報をもつためのクラス
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
	 * 現在Serverに映し出されているスクリーンのデータ（現在使われていない。簡易画面共有用）
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
 * クライアントからの接続を管理しているクラス
 */
class Connector{
	/**
	 * 接続した際に利用したクライアントのSocketクラスを保存しているリスト
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
	 * 接続している人数を会得します
	 * @return
	 */
	public int getConnectorNumber()
	{
		return connectSocketList.size();
	}
}
