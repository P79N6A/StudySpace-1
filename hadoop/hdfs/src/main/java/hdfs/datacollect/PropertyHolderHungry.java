package hdfs.datacollect;

import java.io.IOException;
import java.util.Properties;

public class PropertyHolderHungry {
    private static Properties prop = new Properties();
    static{
        try {
            prop.load(PropertyHolderHungry.class.getClassLoader().getResourceAsStream("./collect.properties"));
        }catch(Exception e){

        }
    }

    public static Properties getProps() throws IOException {
        return prop;
    }

    public static void main(String[] args) throws IOException {
        getProps();
    }
}
