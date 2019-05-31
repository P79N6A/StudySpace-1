package hdfs.wordcount;

import java.util.Map;

public class WordCountMapper implements Mapper {

  public void map(String line, Context context){

    String[] words = line.split(" ");

    for(String word : words){
      Object value = context.get(word);
      if(null == value){
        context.write(word, 1);
      }else{
        int v = Integer.parseInt(value==null?"":value.toString());
        context.write(word, (v + 1));
      }
    }
  }
}
