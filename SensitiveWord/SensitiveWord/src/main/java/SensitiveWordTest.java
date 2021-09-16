import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SensitiveWordTest {

    public static void main(String[] args) throws IOException {
        SensitiveWordFilter filter = new SensitiveWordFilter();
        SensitiveWordInit init=new SensitiveWordInit();
        init.path=args[0];
        filter.setSensitiveWordMap(init.initKeyWord());

        File file = new File(args[1]);//Text
        int index=0;
        int sum=0;

        BufferedReader br = new BufferedReader(new FileReader(file));
        String s = null;
        BufferedWriter out = new BufferedWriter(new FileWriter(args[2]));

        while((s = br.readLine())!=null){
            index++;
            String newString = s.replaceAll("[\\pP\\p{Punct}]", "");
            newString=PinyinUtil.getPinyin(newString,"");
            newString=newString.toLowerCase();
            newString=newString.replaceAll(" ", "");
            Set<String> set = filter.getSensitiveWord(newString, 1);
            int count=set.size();
            int i=0;
            sum+=set.size();
            while(count !=0)
            {
                List<String> res=new ArrayList<>(set);
                String newKey=res.get(i);
                String str="Line" +index+":<"+SensitiveWordInit.ChineseMap.get(newKey)+ ">";
                System.out.println(str);
                out.write(str+"\r\n");
                count--;
                i++;
            }
        }
        System.out.println("Total:"+sum+"\n");
        out.write("Total:"+sum);
        br.close();;
        out.close();
    }

}
