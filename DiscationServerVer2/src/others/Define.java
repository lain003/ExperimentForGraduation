package others;

import java.io.File;
import java.io.FileInputStream;

/**
 * íËêîÇíËã`Ç∑ÇÈÉNÉâÉX
 */
public final class Define {
	public static final Obje obje = new Obje();
	public static final Order order = new Order();
	public static final Objec objec = new Objec();

	static byte[] readJpg(){
		byte[] jpgData = null;

		try{
			File jpgPath = new File("./image/Default.png");
			FileInputStream jpgInput = new FileInputStream(jpgPath); 
			int jpgLength = jpgInput.available();
			jpgData = new byte[jpgLength];
			jpgInput.read(jpgData);
			System.out.println("ReadJpgDataLength = " + jpgLength);
		}
		catch(Exception e){
			e.printStackTrace();
		}

		return jpgData;
	}

	static byte[] readJpg(int jpgNumber){
		byte[] jpgData = null;

		try{
			String folder = "/Users/akitani/Documents/eclipse/workspace/bizinServer/bizinJpg/";
			String number;
			if(jpgNumber / 1 == 0){
				number = "0000";
			}
			else if(jpgNumber / 10 == 0){
				number = "000" + String.valueOf(jpgNumber);
			}
			else if(jpgNumber / 100 == 0){
				number = "00" + String.valueOf(jpgNumber);
			}
			else if(jpgNumber / 1000 == 0){
				number = "0" + String.valueOf(jpgNumber);
			}
			else{
				number = String.valueOf(jpgNumber);
			}

			String path = folder + number + ".jpg";
			File jpgPath = new File(path);
			FileInputStream jpgInput = new FileInputStream(jpgPath); 
			int jpgLength = jpgInput.available();
			jpgData = new byte[jpgLength];
			jpgInput.read(jpgData);
			System.out.println("ReadJpgDataLength = " + jpgLength);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return jpgData;
	}
}

final class Obje
{
	public final int JPG = 2;
	public final int CHARACTER = 3;
	public final int POINTING = 4;
	public final int MAJORITY = 5;
}

final class Objec
{
	public final int SYSTEM = 1;
	public final int NODE = 2;
	public final int AGENDA = 3;
}

final class Order
{
	public final int CREATE = 1;
	public final int EDIT = 2;
	public final int DELETE = 3;

	public final int MAJORITYCREATE = 5;
	public final int MAJORITYADD = 6;
	public final int MAJORITYRESET = 7;
}
