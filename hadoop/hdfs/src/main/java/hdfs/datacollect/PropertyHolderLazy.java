package hdfs.datacollect;

import java.io.IOException;
import java.util.Properties;

public class PropertyHolderLazy {
    private static Properties prop = null;
    public static Properties getProp() throws IOException {
        if(prop == null){
            synchronized (PropertyHolderLazy.class){
                if(prop == null){
                    prop = new Properties();
                    prop.load(PropertyHolderHungry.class.getClassLoader().getResourceAsStream("./collect.properties"));
                }
            }
        }
        return prop;
    }
}
