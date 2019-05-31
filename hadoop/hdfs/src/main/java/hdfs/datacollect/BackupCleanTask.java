package hdfs.datacollect;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.TimerTask;

public class BackupCleanTask extends TimerTask {

  @Override
  public void run() {
    // 探测备份目录

    try {
      Properties props = PropertyHolderHungry.getProps();

      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH");

      long now = new Date().getTime();
      File backupBaseDir = new File(props.getProperty(Constants.LOG_BACKUP_BASE_DIR));
      File[] dayBackDir = backupBaseDir.listFiles();
      for (File dir : dayBackDir) {
        long time = sdf.parse(dir.getName()).getTime();
        if (now - time > 24 * 60 * 60 * 1000L) {
          FileUtils.deleteDirectory(dir);
        }
      }
    } catch (ParseException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
