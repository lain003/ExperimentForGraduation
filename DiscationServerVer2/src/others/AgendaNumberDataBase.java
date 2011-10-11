package others;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * ��c�̗����\������ۂ̔ԍ��Ɋւ���dataBase
 */
public class AgendaNumberDataBase {
	/**
	 * �f�[�^�x�[�X�ɓo�^����ۂɕK�v�Ƃ����ID�ƁA�f�B�X�v���C��ɕ\�����N���C�A���g���w�肵�Ă���ԍ��̑Ή��\(Integer�̏����l�͂P)
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
	 * �����̔ԍ��Ɉ�v����ID��Ԃ��܂��B�������O�̏ꍇ�͐[���O�Aid�͐ݒ肳��Ă��Ȃ�����Ԃ��܂��B�܂��Y�����镨���Ȃ��ꍇ��null��Ԃ��܂��B
	 * @param number �T�������ԍ����w�肵�܂��B
	 * @return ���v����ID��Ԃ��܂��B������ID��id��location�ɑ����āAid����ɂ�������Ԃ��܂��B����͂͂����Ƃ��Ďw�肵�₷���l�ɂ��邽�߂ł��B
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
				if((Integer)entry.getValue() == number){//�����f�[�^�x�[�X���Ƀf�[�^���������Ȃ�
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
