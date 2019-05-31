package hdfs.datacollect;

import java.util.Timer;

public class DataCollectMain {
    public static void main(String[] args){
        Timer timer = new Timer();
        //采集日志
        timer.schedule(new CollectTask(), 0, 60*60*1000L);
        //删除超过24小时的备份日志
        timer.schedule(new BackupCleanTask(), 0, 60*60*1000L);
    }
}
