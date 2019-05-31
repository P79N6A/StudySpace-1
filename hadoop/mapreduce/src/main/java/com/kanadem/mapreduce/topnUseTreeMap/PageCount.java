package com.kanadem.mapreduce.topnUseTreeMap;

public class PageCount implements Comparable<PageCount>{
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
}
