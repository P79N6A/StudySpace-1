package hdfs.wordcount;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

public class HdfsWordCount {

  public static void main(String[] args)
      throws URISyntaxException, IOException, InterruptedException, ClassNotFoundException, IllegalAccessException, InstantiationException {

    Properties props = new Properties();
    props.load(HdfsWordCount.class.getClassLoader().getResourceAsStream("job.properties"));
    //根据配置文件选择接口的实现类

    Class<?> mapper_class = Class.forName(props.getProperty("MAPPER_CLASS"));
    Mapper mapper = (Mapper) mapper_class.newInstance();
    Context context = new Context();
    //从hdfs读取文件，一次读取一行

    FileSystem fs = FileSystem.get(new URI("hdfs://192.168.1.100:9000"), new Configuration(), "hadoop");
    Path inPath = new Path("/wordcount/input/");
    if(!fs.exists(inPath)){
      fs.create(inPath);
    }
    RemoteIterator<LocatedFileStatus> iter = fs
        .listFiles(new Path("/wordcount/input"), false);
    while (iter.hasNext()) {
      LocatedFileStatus file = iter.next();
      FSDataInputStream in = fs.open(file.getPath());
      //逐行读取
      BufferedReader br = new BufferedReader(
          new BufferedReader(new InputStreamReader(in)));
      String line = null;
      while ((line = br.readLine()) != null) {
        mapper.map(line, context);
      }
      br.close();
      in.close();
    }

    //输出结果
    HashMap<Object, Object> contextMap = context.getContextMap();
    Path outPath = new Path("/wordcount/output/");
    if(!fs.exists(outPath)){
      fs.create(outPath);
    }
    FSDataOutputStream out = fs
        .create(new Path(props.getProperty("OUTPUT_FILE")), true);
    Set<Entry<Object, Object>> entrySet = contextMap.entrySet();
    for(Entry<Object, Object> entry : entrySet){
      out.write((entry.getKey().toString() +  "\t" + entry.getValue() + "\n").getBytes());
    }

    out.close();
    System.out.println("流程完成");

    //调用一个方法对每一行进行业务处理

    //将这一行的处理结果放入缓存

    //调用一个方法将缓存中的结果数据输出道HDFS结果文件
  }

}
