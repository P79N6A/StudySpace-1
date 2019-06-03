package hdfs.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.Before;
import org.junit.Test;

public class HdfsClientDemo {

  FileSystem fs = null;

  @Before
  public void init() throws URISyntaxException, IOException, InterruptedException {
    Configuration conf = new Configuration();
    conf.set("dfs.replication", "2");
    conf.set("dfs.blocksize", "64M");

    fs = FileSystem.get(new URI("hdfs://192.168.1.100:9000"), conf, "hadoop");
  }

  /*
   *   上传文件
   */
  @Test
  public void uploadtest() throws Exception {
    try {
      fs.copyFromLocalFile(new Path("D:\\Download\\Chrome\\GF-Trader.exe"), new Path("/"));
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      fs.close();
    }
  }

  /*
   *   下载文件
   */
  @Test
  public void downloadtest() throws IOException {
    try {
      fs.copyToLocalFile(new Path("/WebStorm-2018.3.4.exe"), new Path("D:\\Download\\"));
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      fs.close();
    }
  }

  /*
   *   删除文件或文件夹
   */
  @Test
  public void rmtest() throws IOException {
    try {
      fs.delete(new Path("/aaa"), true);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      fs.close();
    }
  }

  /*
   *   查询文件信息
   */
  @Test
  public void lstest() throws IOException {
    try {
      RemoteIterator<LocatedFileStatus> iter = fs.listFiles(new Path("/"), true);
      while (iter.hasNext()) {
        LocatedFileStatus status = iter.next();
        System.out.println("文件路径：" + status.getPath());
        System.out.println("块大小：" + status.getBlockSize());
        System.out.println("文件长度：" + status.getLen());
        System.out.println("副本数量：" + status.getReplication());
        System.out.println("块信息：" + Arrays.toString(status.getBlockLocations()));
        System.out.println("----------------------------");


      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      fs.close();
    }
  }

  /*
   *   查询文件或信息
   */
  @Test
  public void ls2test() throws IOException {
    try {
      FileStatus[] fileStatuses = fs.listStatus(new Path("/"));
      for (FileStatus status : fileStatuses) {
        System.out.println("文件路径：" + status.getPath());
        System.out.println("块大小：" + status.getBlockSize());
        System.out.println("副本数量：" + status.getReplication());
        System.out.println("----------------------------");
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      fs.close();
    }
  }

  /*
   * 读取HDFS中的文件内容
   * */
  @Test
  public void testReadData() throws IOException {
    FSDataInputStream in = fs.open(new Path("/test.txt"));
    byte[] byteBuffer = new byte[1024];
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
    String line = null;
    while ((line = bufferedReader.readLine()) != null) {
      System.out.println(line);
    }
    bufferedReader.close();
    in.close();
    fs.close();
  }

  /*
   * 读取HDFS中的文件内容
   * */
  @Test
  public void ReadDatatest2() throws IOException {
    FSDataInputStream in = fs.open(new Path("/test2.txt"));
    in.seek(13);
    byte[] byteBuffer = new byte[13];
    in.read(byteBuffer);
    System.out.println(new String(byteBuffer));
    in.close();
    fs.close();
  }

  /*
   * 向HDFS写入数据
   * */
  @Test
  public void WriteDatatest() throws IOException {
    FSDataOutputStream fsDataOutputStream = fs.create(new Path("/x.jpg"), true);
    byte[] buf = new byte[1024];
    fsDataOutputStream.write(0);

    fsDataOutputStream.close();
    fs.close();
  }

  /*
   * 从本地文件读取数据，向HDFS写入数据
   * */
  @Test
  public void WriteDatatest2() throws IOException {
    FSDataOutputStream out = fs.create(new Path("/x.jpg"), true);
    FileInputStream in = new FileInputStream(
        "E:\\Projects\\hadoop\\src\\main\\java\\hdfs\\images\\1.jpg");
    byte[] buf = new byte[1024];
    int read = 0;
    while ((read = in.read(buf)) != -1) {
      out.write(buf, 0, read);
    }
    in.close();
    out.close();
    fs.close();
  }
}
