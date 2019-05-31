package com.kanadem.mapreduce.FlowCount;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class FlowCountReducer extends Reducer<Text, FlowBean, Text, FlowBean> {

  @Override
  protected void reduce(Text key, Iterable<FlowBean> values, Context context)
      throws IOException, InterruptedException {
    int upSum = 0;
    int dSum = 0;
    for(FlowBean value : values){
      upSum += value.getUpFlow();
      dSum += value.getDownFlow();
    }

    context.write(key, new FlowBean(upSum, dSum, key.toString()));
  }
}
