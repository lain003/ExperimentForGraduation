package others;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;

import obje.JPG;
import obje.MyCharacter;
import obje.Pointing;

public class MyJpanel extends JPanel{
	Timer timer;
	DataBase dataBase;

	int oldDepth = 0;
	Graphics myGraphics;

	MyJpanel(DataBase database)
	{
		dataBase  = database;
		timer = new Timer(50, new TimerAction());//更新感覚
		timer.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if(oldDepth == 0){
			myGraphics = g;
		}

		int w = this.getWidth();
		int h = this.getHeight();

		/*if(dataBase.getHistoryLength() > oldDepth)
		{
			oldDepth++;
			drowHistory(myGraphics, oldDepth);
		}*/
		drowDataBase(g, dataBase);

		Rectangle rectangle = new Rectangle(0,0,500,500);//画面データ会得
		Robot r;
		try {
			r = new Robot();
			Image img = r.createScreenCapture(rectangle);
			dataBase.getMySystem().setScreenData(img);
		} catch (AWTException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
	}

	/**
	 * DataBaseのhistoryからデータを読み込んで、描画します
	 * @param depth 読み込みたいデータがある場所を指定します
	 */
	void drowHistory(Graphics g,int depth)
	{
		if(depth == 0){
			System.out.println("depth = 0");
		}
		else{
			Vector<OrderAndPlace> dataBaseHistroy = dataBase.getHistoryClone();

			OrderAndPlace orderAndPlace = dataBaseHistroy.get(depth -1);
			NodeInDataBase node = dataBase.getNodeToID(orderAndPlace.getId());

			drowNode(g, node);
		}
	}

	/**
	 * Nodeの内容を画面に書き込みます
	 * @param g
	 * @param node　書き込みたいNodeを指定します
	 */
	void drowNode(Graphics g,NodeInDataBase node)
	{
		if(node.getIdentNumber() == Define.obje.JPG)
		{
			JPG jpg = (JPG) node.getBody();
			Color color = new Color(100,100,100);
			g.drawImage(jpg.convertBufferdImage(),0,0,color,null);
		}
		else if(node.getIdentNumber() == Define.obje.CHARACTER)
		{
			if(node.getUserNumber() == 1)
			{
				g.setColor(Color.RED);
				MyCharacter myChar = (MyCharacter)node.getBody();
				g.setFont(new Font("Serif", Font.BOLD, 36));
				g.drawString(myChar.getChars(), myChar.getX(), myChar.getY());
			}
			else if(node.getUserNumber() == 2)
			{
				g.setColor(Color.BLUE);
				MyCharacter myChar = (MyCharacter)node.getBody();
				g.setFont(new Font("Serif", Font.BOLD, 36));
				g.drawString(myChar.getChars(), myChar.getX(), myChar.getY());
			}
		}
		else if(node.getIdentNumber() == Define.obje.POINTING){
			Pointing pointing = (Pointing) node.getBody();
			ArrayList<Coordinate> pointingList = pointing.getCoordinateList();
			for(int i = 1;i < pointingList.size();i++){
				if(node.getUserNumber() == 1){
					g.setColor(Color.RED);
				}
				else if(node.getUserNumber() == 2){
					g.setColor(Color.BLUE);
				}
				else if(node.getUserNumber() == 3){
					g.setColor(Color.GREEN);
				}
				else if(node.getUserNumber() == 4){
					g.setColor(Color.BLACK);
				}
				Coordinate coordinate = pointingList.get(i);
				Coordinate oldCoordinate = pointingList.get(i-1);
				//g.fillOval(coordinate.x - pointing.getR(), coordinate.y - pointing.getR(), pointing.getR(), pointing.getR());
				Graphics2D g2 = (Graphics2D)g;
				g2.draw(new Line2D.Double(coordinate.x, coordinate.y, oldCoordinate.x, oldCoordinate.y));
			}
		}
		else
		{
			//未実装
		}
	}

	/**
	 * データベースの中の内容を、全てJpanelに描画します
	 * @param dataBase
	 */
	public void drowDataBase(Graphics g,DataBase dataBase)//とりあえず限定的な機能の元に書く（要修正）
	{	
		Vector cloneDataBase = dataBase.getDataBaseClone();
		if(cloneDataBase.size() == 0)//何も入ってなかったら
		{
			System.out.println("DataBaseNodata");
		}
		else
		{
			Vector tempVec = (Vector) cloneDataBase.get(0);//////////
			System.out.println("tempVecSize = " + tempVec.size());

			for(int i = 0;i < tempVec.size();i++){
				Object temp = tempVec.get(i);
				if("others.NodeInDataBase" == temp.getClass().getName()){
					NodeInDataBase tempNode = (NodeInDataBase) temp;
					drowNode(g,tempNode);
				}
				else
				{
					//未実装
				}
			}
		}

		MySystem mySystem = dataBase.getMySystem();
		Majority majority = mySystem.getMajority();
		if(majority != null)
		{
			int ayeVote = majority.getAyeote();
			int nayVote = majority.getNayVote();

			g.setFont(new Font("Serif",Font.BOLD,50));
			g.setColor(Color.BLACK);
			g.drawString("YES = " + Integer.toString(ayeVote), 350, 80);
			g.drawString("NO = " + Integer.toString(nayVote), 350, 140);
			System.out.println(Integer.toString(ayeVote));
		}
		else{
		}
	}

	public class TimerAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(dataBase.changeFlag == true)
			{
				repaint();
				dataBase.changeFlag = false;
			}
		}
	}
}
