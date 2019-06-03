package com.kanadem.mapreduce.FlowCount;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.Writable;

/**
 * 自定义数据类型实现序列化接口
 * 1.一定要有空构造函数
 * 2.write方法中的输出字段二进制数据的顺序要与readFields的顺序一致。
 */

public class FlowBean implements Writable {
  private int upFlow;
  private int downFlow;
  private String phoneNumber;
  private int amountFlow;
  public FlowBean(){}
  public FlowBean(int upFlow, int downFlow, String phoneNumber) {
    this.upFlow = upFlow;
    this.downFlow = downFlow;
    this.phoneNumber = phoneNumber;
    this.amountFlow = upFlow + downFlow;
  }



  public int getDownFlow() {
    return downFlow;
  }

  public void setDownFlow(int downFlow) {
    this.downFlow = downFlow;
  }

  public int getUpFlow() {
    return upFlow;
  }

  public void setUpFlow(int upFlow) {
    this.upFlow = upFlow;
  }

  public int getAmountFlow() {
    return amountFlow;
  }

  public void setAmountFlow(int amountFlow) {
    this.amountFlow = amountFlow;
  }

  /**
   *
   * @param dataOutput
   * @throws IOException
   * 序列化
   */
  @Override
  public void write(DataOutput dataOutput) throws IOException {

    dataOutput.writeInt(upFlow);
    dataOutput.writeUTF(phoneNumber);
    dataOutput.writeInt(downFlow);
    dataOutput.writeInt(amountFlow);
  }

  /**
   *
   * @param dataInput
   * @throws IOException
   * 反序列化
   */
  @Override
  public void readFields(DataInput dataInput) throws IOException {
    this.upFlow = dataInput.readInt();
    this.phoneNumber = dataInput.readUTF();
    this.downFlow = dataInput.readInt();
    this.amountFlow = dataInput.readInt();

  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   *
   * @return
   */
  @Override
  public String toString(){
    return this.phoneNumber + "," + this.upFlow + "," + this.downFlow + "," + this.amountFlow;
  }
}
