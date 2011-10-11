package others;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/*画面描画定義プログラム*/

public class MyJFrame extends JFrame implements ActionListener{
	MyJpanel myjpane;

	public MyJFrame(DataBase dataBase){
		myjpane = new MyJpanel(dataBase); 
		this.setSize(500, 500);
		this.add(myjpane, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void run()
	{
		setVisible(true);
	}	

	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

}

