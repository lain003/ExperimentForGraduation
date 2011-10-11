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
		timer = new Timer(50, new TimerAction());//�X�V���o
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

		Rectangle rectangle = new Rectangle(0,0,500,500);//��ʃf�[�^�
		Robot r;
		try {
			r = new Robot();
			Image img = r.createScreenCapture(rectangle);
			dataBase.getMySystem().setScreenData(img);
		} catch (AWTException e1) {
			// TODO �����������ꂽ catch �u���b�N
			e1.printStackTrace();
		}
	}

	/**
	 * DataBase��history����f�[�^��ǂݍ���ŁA�`�悵�܂�
	 * @param depth �ǂݍ��݂����f�[�^������ꏊ���w�肵�܂�
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
	 * Node�̓��e����ʂɏ������݂܂�
	 * @param g
	 * @param node�@�������݂���Node���w�肵�܂�
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
			//������
		}
	}

	/**
	 * �f�[�^�x�[�X�̒��̓��e���A�S��Jpanel�ɕ`�悵�܂�
	 * @param dataBase
	 */
	public void drowDataBase(Graphics g,DataBase dataBase)//�Ƃ肠��������I�ȋ@�\�̌��ɏ����i�v�C���j
	{	
		Vector cloneDataBase = dataBase.getDataBaseClone();
		if(cloneDataBase.size() == 0)//���������ĂȂ�������
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
					//������
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
