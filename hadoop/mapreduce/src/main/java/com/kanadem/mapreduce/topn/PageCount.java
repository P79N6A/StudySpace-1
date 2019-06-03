package com.kanadem.mapreduce.topn;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

/**
 * 因为要用map传输出去，所以这次需要实现序列化和反序列化接口Writable
 * 并且因为要用reduce排序，所以还要实现Comparable接口
 * Hadoop中封装了这个接口，就是WritableComparable
 */
public class PageCount implements WritableComparable<PageCount> {
  private String Page;
  private int count;

  public void set(String page, int count) {
    this.Page = page;
    this.count = count;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public String getPage() {
    return Page;
  }

  public void setPage(String page) {
    Page = page;
  }

  @Override
  public int compareTo(PageCount o) {

    return o.getCount() - this.count ==0?this.Page.compareTo(o.getPage()):o.getCount()-this.count;
  }

  @Override
  public void write(DataOutput dataOutput) throws IOException {
    dataOutput.writeUTF(this.Page);
    dataOutput.writeInt(this.count);
  }

  @Override
  public void readFields(DataInput dataInput) throws IOException {
    this.Page = dataInput.readUTF();
    this.count = dataInput.readInt();
  }

  @Override
  public String toString() {
    return "PageCount{" +
        "Page='" + Page + '\'' +
        ", count=" + count +
        '}';
  }
}
