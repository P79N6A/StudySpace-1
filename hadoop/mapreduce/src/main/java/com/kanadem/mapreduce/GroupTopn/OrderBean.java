package com.kanadem.mapreduce.GroupTopn;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

public class OrderBean implements WritableComparable<OrderBean> {

  private String OrderId;
  private String UserId;
  private String pdtName;
  private float price;
  private int number;
  private float amount;

  public OrderBean() {
  }

  public void set(String orderId, String userId, String pdtName, float price, int number) {
    OrderId = orderId;
    UserId = userId;
    this.pdtName = pdtName;
    this.price = price;
    this.number = number;
    this.amount = price * number;
  }

  public String getOrderId() {
    return OrderId;
  }

  public void setOrderId(String orderId) {
    OrderId = orderId;
  }

  public String getUserId() {
    return UserId;
  }

  public void setUserId(String userId) {
    UserId = userId;
  }

  public String getPdtName() {
    return pdtName;
  }

  public void setPdtName(String pdtName) {
    this.pdtName = pdtName;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public float getAmount() {
    return amount;
  }

  public void setAmount(float amount) {
    this.amount = amount;
  }

  @Override
  public String toString() {
    return  OrderId + ',' + UserId + ','  + pdtName + ',' + price +
        "," + number +
        "," + amount;
  }

  @Override
  public void write(DataOutput dataOutput) throws IOException {
    dataOutput.writeUTF(this.OrderId);
    dataOutput.writeUTF(this.UserId);
    dataOutput.writeUTF(this.pdtName);
    dataOutput.writeFloat(this.price);
    dataOutput.writeInt(this.number);
  }

  @Override
  public void readFields(DataInput dataInput) throws IOException {
    this.OrderId = dataInput.readUTF();
    this.UserId = dataInput.readUTF();
    this.pdtName = dataInput.readUTF();
    this.price = dataInput.readFloat();
    this.number = dataInput.readInt();
    this.amount = this.price * this.number;
  }

  @Override
  public int compareTo(OrderBean o) {
    return Float.compare(o.getAmount(), this.getAmount()) == 0?this.pdtName.compareTo(o.getPdtName()):Float.compare(o.getAmount(), this.getAmount());

  }
}
