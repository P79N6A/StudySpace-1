package hdfs.datacollect;

import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimerTask;
import java.util.UUID;

public class CollectTask extends TimerTask {

  @Override
  public void run() {
    try {
      final Properties props = PropertyHolderLazy.getProp();

      //log4j日志

      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH");
      String day = sdf.format(new Date());
      System.out.println(day);
      File srcDir = new File(props.getProperty(Constants.LOG_SOURCE_DIR));
      File toUploadDir = new File(props.getProperty(Constants.LOG_TOUPLOAD_DIR));
      File[] listFiles = srcDir.listFiles(new FilenameFilter() {
        public boolean accept(File dir, String name) {
          if (name.startsWith(props.getProperty(Constants.LOG_LEGAL_PREFIX))) {
            return true;
          }
          return false;
        }
      });


      for (File file : listFiles) {
        try {
          FileUtils
              .moveFileToDirectory(file, toUploadDir, true);
        } catch (IOException e) {
          e.printStackTrace();
        }

      }

      FileSystem fs = FileSystem.get(new URI(props.getProperty(Constants.HDFS_URI)), new Configuration(), "hadoop");
      File[] toUploadFiles = toUploadDir.listFiles();
      //检查HDFS中日期目录是否存在，如果不存在则创建
      if (!fs.exists(new Path("/logs/" + day + "/"))) {
        fs.mkdirs(new Path("/logs/" + day + "/"));
      }
      //检查本地备份目录是否存在，如果不存在则创建
      File backupDir = new File(props.getProperty(Constants.LOG_BACKUP_BASE_DIR) + day + "\\");
      if (!backupDir.exists()) {
        backupDir.mkdirs();
      }

      for (File file : toUploadFiles) {
        //传输文件到HDFS
        Path destPath = new Path("/logs/" + day + "/access_log_" + UUID.randomUUID() + ".log");
        fs.copyFromLocalFile(new Path(file.getAbsolutePath()), destPath);
        //将传输完成的文件移动到备份目录
        FileUtils.moveFileToDirectory(file, backupDir, true);

      }

    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }
}
