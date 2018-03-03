package com.auts.backstage.model.response;


//":{"pager":{"pageNo":1,"pageSize":1,"totalCount":63,"pageCount":63,"nextPage":2,"prePage":0,"hasNaxtPage":true,"
//		+ ""hasPrePage":false,"startIndex":0},"
public class Pager {

	private int pageNo;
	private int pageSize;
	private int totalCount;
	private int pageCount;
	private int nextPage;
	private int prePage;
	private boolean hasNaxtPage;
	private boolean hasPrePage;
	private int startIndex;
	
    public Pager() {  
        super();  
    }  
  
    public Pager(int pageNo, int pageSize, int pageCount, int totalCount) {  
        super();  
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.pageCount = pageCount;
        this.totalCount = totalCount;
    } 
    
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	public int getPrePage() {
		return prePage;
	}
	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}
	public boolean isHasNaxtPage() {
		return hasNaxtPage;
	}
	public void setHasNaxtPage(boolean hasNaxtPage) {
		this.hasNaxtPage = hasNaxtPage;
	}
	public boolean isHasPrePage() {
		return hasPrePage;
	}
	public void setHasPrePage(boolean hasPrePage) {
		this.hasPrePage = hasPrePage;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	
	
}
