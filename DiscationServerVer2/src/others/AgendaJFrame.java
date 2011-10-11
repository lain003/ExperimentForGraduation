package others;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.Timer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import others.MyJpanel.TimerAction;

/**
 * DisplayDataBase内のAgendaDataBaseの中を、表示するためのクラス
 */
public class AgendaJFrame extends JFrame implements ActionListener{
	AgendaJpanel myjpane;
	int frameSizeX = 300;
	int frameSizeY = 300;
	AgendaDataBase agendaDataBase;
	DefaultMutableTreeNode root;
	JTree tree;

	public AgendaJFrame(DataBase dataBase){
		root = new DefaultMutableTreeNode();
		tree = new JTree(root);
		tree.setRootVisible(true);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().setView(tree);
		scrollPane.setPreferredSize(new Dimension(frameSizeX, frameSizeY));
		
		agendaDataBase = dataBase.getAgendaDataBase();
		myjpane = new AgendaJpanel(dataBase,frameSizeX,frameSizeY,root);
		myjpane.add(scrollPane);
							
		this.setSize(frameSizeX, frameSizeY);
		this.add(myjpane, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void run(){
		setVisible(true);
		
		Timer timer = new Timer(50, new TimerAction());//更新感覚
		timer.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO 自動生成されたメソッド・スタブ	
	}
	
	private class TimerAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if(agendaDataBase.getChangeFlag() == true){
				repaint();
				//expandAll(tree);
			}
		}
	}
	
	private void expandAll(JTree tre) {
		int row = 0;
		while(row<tre.getRowCount()) {
			tre.expandRow(row);
			row++;
		}
	}
}
