package com.auts.backstage.page;

import java.io.Serializable;

public class Page implements Serializable {  
	 
    private static final long serialVersionUID = -4973539948210269342L;  
    /** 
     * 默认页码 
     */  
    public static final int DEFAULT_PAGE_NO = 1;  
    /** 
     * 默认页面大小 
     */  
    public static final int DEFAULT_PAGE_SIZE = 10;  
    /** 
     * 默认的快速导航页码显示个数 
     */  
    public static final int DEFAULT_PAGE_NAV_SIZE = 5;  
  
    private int pageNo = DEFAULT_PAGE_NO; // 页码  
    private int pageSize = DEFAULT_PAGE_SIZE; // 页面大小  
    private int pageNaviSize = DEFAULT_PAGE_NAV_SIZE; // 页码快速导航显示的个数  
    private int totalCount; // 总的记录数  
//    private List<E> resultList; // 返回的查询结果集  
  
    public Page() {  
        super();  
    }  
  
    public Page(int pageNo, int pageSize) {  
        super();  
        setPageNo(pageNo);  
        setPageSize(pageSize);  
    }  
  
	public Page(int pageNo, int pageSize, int totalCount) {
		this(pageNo, pageSize);
		this.totalCount = totalCount;
	}
    	
//    public Page(int pageNo, int pageSize, int pageNaviSize) {  
//        this(pageNo, pageSize);  
//        setPageNaviSize(pageNaviSize);  
//    }  
  
    public int getPageNo() {  
        return pageNo;  
    }  
  
    public void setPageNo(int pageNo) {  
        if (pageNo < 1) {  
            pageNo = DEFAULT_PAGE_NO;  
        }  
        this.pageNo = pageNo;  
    }  
  
    public int getPageSize() {  
        return pageSize;  
    }  
  
    public void setPageSize(int pageSize) {  
        if (pageSize < 1) {  
            pageSize = DEFAULT_PAGE_SIZE;  
        }  
        this.pageSize = pageSize;  
    }  
  
    public int getTotalCount() {  
        return totalCount;  
    }  
  
    public void setTotalCount(int totalCount) {  
        this.totalCount = totalCount;  
    }  
  
//    public List<E> getResultList() {  
//        return resultList;  
//    }  
//  
//    public void setResultList(List<E> resultList) {  
//        this.resultList = resultList;  
//    }  
  
    public int getPageNaviSize() {  
        return pageNaviSize;  
    }  
  
    /** 
     * 返回快速导航页码 
     *  
     * @return 
     */  
    public int[] getPageNavis() {  
        // 先运算出左，右边界  
        int start = 0, end = 0;  
        int a = pageNaviSize / 2;  
        start = pageNo - a;  
        if (pageNaviSize % 2 == 0) {  
            end = pageNo + a - 1;  
        } else {  
            end = pageNo + a;  
        }  
        // 分三种情况处理  
        int totalPages = getTotalPage();  
        int[] b = new int[pageNaviSize];  
        // 左边界  
        if (start < 1) {  
            for (int i = 0, step = 1; i < pageNaviSize; i++, step++) {  
                if (step <= totalPages) {  
                    b[i] = step;  
                } else {  
                    break;  
                }  
            }  
        } else if (end > totalPages) {   // 右边界  
            for (int i = pageNaviSize - 1, step = totalPages; i >= 0; i--, step--) {  
                if (step > 0) {  
                    b[i] = step;  
                } else {  
                    break;  
                }  
            }  
        } else {    // 中间  
            for (int i = 0; i < pageNaviSize; i++) {  
                b[i] = start++;  
            }  
        }  
        return b;  
    }  
  
    public void setPageNaviSize(int pageNaviSize) {  
        if (pageNaviSize < 1) {  
            pageNaviSize = DEFAULT_PAGE_NAV_SIZE;  
        }  
        this.pageNaviSize = pageNaviSize;  
    }  
  
    /** 
     * 获得总的页码数量 
     *  
     * @return 
     */  
    public int getTotalPage() {  
        if (totalCount % pageSize > 0) {  
            return totalCount / pageSize + 1;  
        } else {  
            return totalCount / pageSize;  
        }  
    }  
  
    /** 
     * 获取从哪一条记录开始查询 
     *  
     * @return 
     */  
    public int getFirstIndex() {  
        return (pageNo - 1) * pageSize;  
    }  
      
    /** 
     * 获取最后一条记录的下标数（不包含） 
     *  
     * @return 
     */  
    public int getLastIndex() {  
        return getFirstIndex() + pageSize;  
    }  
  
    /** 
     * 判断是否还有下一页 
     *  
     * @return 
     */  
    public boolean isHasNextPage() {  
        return (pageNo + 1) <= getTotalPage();  
    }  
  
    /** 
     * 获取下一个页码，在调用之前先调用<code>isHasNextPage()</code>方法进行判断 
     *  
     * @return 
     */  
    public int getNextPage() {  
        return pageNo + 1;  
    }  
  
    /** 
     * 判断是否还有上一页 
     * 
     * @return 
     */  
    public boolean isHasPrePage() {  
        return (pageNo - 1) > 0;  
    }  
  
    /** 
     * 获取上一个页码，在调用之前先调用<code>isHasPrePage()</code>方法进行判断 
     *  
     * @return 
     */  
    public int getPrePage() {  
        return pageNo - 1;  
    }  
}  