package hdfs.wordcount;

public interface Mapper {

  public void map(String line, Context countext);
}
