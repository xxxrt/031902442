import java.util.*;
public class SensitiveWordFilter {
	@SuppressWarnings("rawtypes")
	private Map sensitiveWordMap = null;
	public static int minMatchTYpe = 1;      //��Сƥ�����
	public static int maxMatchType = 2;      //���ƥ�����
	
	/**
	 * ���캯������ʼ�����дʿ�
	 */
	public Map getSensitiveWordMap() {
		return sensitiveWordMap;
	}

	public void setSensitiveWordMap(Map sensitiveWordMap) {
		this.sensitiveWordMap = sensitiveWordMap;
	}
	/**
	 * �ж������Ƿ���������ַ�
	 * @param txt  ����
	 * @param matchType  ƥ�����&nbsp;1����Сƥ�����2�����ƥ�����
	 * @return ����������true�����򷵻�false
	 * @version 1.0
	 */
	public boolean isContaintSensitiveWord(String txt,int matchType){
		boolean flag = false;
		for(int i = 0 ; i < txt.length() ; i++){
			int matchFlag = this.CheckSensitiveWord(txt, i, matchType); //�ж��Ƿ���������ַ�
			if(matchFlag > 0){    //����0���ڣ�����true
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * ��ȡ�����е����д�
	 * @param txt ����
	 * @param matchType ƥ�����&nbsp;1����Сƥ�����2�����ƥ�����
	 * @return
	 * @version 1.0
	 */
	public Set<String> getSensitiveWord(String txt , int matchType){
		Set<String> sensitiveWordList = new HashSet<String>();
		
		for(int i = 0 ; i < txt.length() ; i++){
			int length = CheckSensitiveWord(txt, i, matchType);    //�ж��Ƿ���������ַ�
			if(length > 0){    //����,����list��
				sensitiveWordList.add(txt.substring(i, i+length));
				i = i + length - 1;    //��1��ԭ������Ϊfor������
			}
		}
		
		return sensitiveWordList;
	}
	
	/**
	 * �滻�������ַ�
	 * @param txt
	 * @param matchType
	 * @param replaceChar �滻�ַ���Ĭ��*
	 * @version 1.0
	 */
	public String replaceSensitiveWord(String txt,int matchType,String replaceChar){
		String resultTxt = txt;
		Set<String> set = getSensitiveWord(txt, matchType);     //��ȡ���е����д�
		Iterator<String> iterator = set.iterator();
		String word = null;
		String replaceString = null;
		while (iterator.hasNext()) {
			word = iterator.next();
			replaceString = getReplaceChars(replaceChar, word.length());
			resultTxt = resultTxt.replaceAll(word, replaceString);
		}
		
		return resultTxt;
	}
	
	/**
	 * ��ȡ�滻�ַ���
	 * @param replaceChar
	 * @param length
	 * @return
	 * @version 1.0
	 */
	private String getReplaceChars(String replaceChar,int length){
		String resultReplace = replaceChar;
		for(int i = 1 ; i < length ; i++){
			resultReplace += replaceChar;
		}
		
		return resultReplace;
	}
	
	/**
	 * ����������Ƿ���������ַ�
	 */
	@SuppressWarnings({ "rawtypes"})
	public int CheckSensitiveWord(String txt,int beginIndex,int matchType){
		boolean  flag = false;    //���дʽ�����ʶλ���������д�ֻ��1λ�����
		int matchFlag = 0;     //ƥ���ʶ��Ĭ��Ϊ0
		char word = 0;
		Map nowMap = sensitiveWordMap;
		for(int i = beginIndex; i < txt.length() ; i++){
			word = txt.charAt(i);
			nowMap = (Map) nowMap.get(word);     //��ȡָ��key
			if(nowMap != null){     //���ڣ����ж��Ƿ�Ϊ���һ��
				matchFlag++;     //�ҵ���Ӧkey��ƥ���ʶ+1 
				if("1".equals(nowMap.get("isEnd"))){       //���Ϊ���һ��ƥ�����,����ѭ��������ƥ���ʶ��
					flag = true;       //������־λΪtrue   
					if(SensitiveWordFilter.minMatchTYpe == matchType){    //��С����ֱ�ӷ���,���������������
						break;
					}
				}
			}
			else{     //�����ڣ�ֱ�ӷ���
				break;
			}
		}
		if(matchFlag < 2 || !flag){        //���ȱ�����ڵ���1��Ϊ�� 
			matchFlag = 0;
		}
		return matchFlag;
	}
	

}
