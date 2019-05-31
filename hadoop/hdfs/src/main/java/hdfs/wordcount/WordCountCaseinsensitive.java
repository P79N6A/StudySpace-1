package hdfs.wordcount;

public class WordCountCaseinsensitive implements Mapper {
  public void map(String line, Context context){

    String[] words = line.toUpperCase().split(" ");

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
