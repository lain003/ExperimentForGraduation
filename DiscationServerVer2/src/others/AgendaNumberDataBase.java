package others;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 会議の流れを表示する際の番号に関するdataBase
 */
public class AgendaNumberDataBase {
	/**
	 * データベースに登録する際に必要とされるIDと、ディスプレイ上に表示しクライアントが指定してくる番号の対応表(Integerの初期値は１)
	 */
	private HashMap<ID, Integer> dataBase = new HashMap<ID, Integer>();

	public AgendaNumberDataBase(){
	}

	public void addDataBase(ID id){
		int dataBaseSize = dataBase.size();
		dataBase.put(id, dataBaseSize + 1);
	}
	public Integer searchDataBase(ID id){
		Integer i = dataBase.get(id);
		return i;
	}
	/**
	 * 引数の番号に一致したIDを返します。ただし０の場合は深さ０、idは設定されていない物を返します。また該当する物がない場合はnullを返します。
	 * @param number 探したい番号を指定します。
	 * @return 合致したIDを返します。ただしIDはidをlocationに足して、idを空にした物を返します。これははりつけ先として指定しやすい様にするためです。
	 */
	public ID searchDataBase(int number){
		if(number == 0){
			ID id = new ID();
			id.setDepth(0);

			return id;
		}
		else{
			Set set = dataBase.entrySet();
			Iterator it = set.iterator();

			while(it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				if((Integer)entry.getValue() == number){//もしデータベース内にデータがあったなら
					ID searchId = (ID)entry.getKey();
					ID returnId = new ID();
					ArrayList<Integer> searchLocation = searchId.getLocation();
					ArrayList<Integer> returnLocation = returnId.getLocation();

					for(int i = 0;i < searchLocation.size();i++){
						returnLocation.add(searchLocation.get(i));
					}
					returnLocation.add(searchId.getId());
					returnId.setDepth(returnLocation.size());

					return returnId;
				}
			}
		}

		return null;
	}
}
