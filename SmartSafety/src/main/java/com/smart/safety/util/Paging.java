package com.smart.safety.util;
/**
 * Paging
*
* @author whitelife
* @since 2014.10.05
* @version 0.1
*/
public class Paging {
   private int pageSize; // 게시 글 수
   private int firstPageNo; // 첫 번째 페이지 번호
   private int prevPageNo; // 이전 페이지 번호
   private int startPageNo=1; // 시작 페이지 (페이징 네비 기준)
   private int pageNo; // 페이지 번호
   private int endPageNo=1; // 끝 페이지 (페이징 네비 기준)
   private int nextPageNo; // 다음 페이지 번호
   private int finalPageNo; // 마지막 페이지 번호
   private int totalCount; // 게시 글 전체 수
   private int maxPageNum; //한번에 보여지는 페이지 수
   
   /**
    * 
    * @param pageNo current Page Number
    * @param totalCount  Count of current result Rows
    * @param pageSize count of rows per page(ex 10)
    * @param maxPageNum page max number for move(ex) 10) 
    */
   public Paging(int pageNo, int totalCount, int pageSize, int maxPageNum) {
	   	this.pageNo = pageNo;
	   	this.totalCount = totalCount;
	   	this.pageSize = pageSize;
	   	this.maxPageNum = maxPageNum;
   }

/**
    * @return the pageSize
    */
   public int getPageSize() {
       return pageSize;
   }

   /**
    * @param pageSize the pageSize to set
    */
   public void setPageSize(int pageSize) {
       this.pageSize = pageSize;
   }

   /**
    * @return the firstPageNo
    */
   public int getFirstPageNo() {
       return firstPageNo;
   }

   /**
    * @param firstPageNo the firstPageNo to set
    */
   public void setFirstPageNo(int firstPageNo) {
       this.firstPageNo = firstPageNo;
   }

   /**
    * @return the prevPageNo
    */
   public int getPrevPageNo() {
       return prevPageNo;
   }

   /**
    * @param prevPageNo the prevPageNo to set
    */
   public void setPrevPageNo(int prevPageNo) {
       this.prevPageNo = prevPageNo;
   }

   /**
    * @return the startPageNo
    */
   public int getStartPageNo() {
       return startPageNo;
   }

   /**
    * @param startPageNo the startPageNo to set
    */
   public void setStartPageNo(int startPageNo) {
       this.startPageNo = startPageNo;
   }

   /**
    * @return the pageNo
    */
   public int getPageNo() {
       return pageNo;
   }

   /**
    * @param pageNo the pageNo to set
    */
   public void setPageNo(int pageNo) {
       this.pageNo = pageNo;
   }

   /**
    * @return the endPageNo
    */
   public int getEndPageNo() {
       return endPageNo;
   }

   /**
    * @param endPageNo the endPageNo to set
    */
   public void setEndPageNo(int endPageNo) {
       this.endPageNo = endPageNo;
   }

   /**
    * @return the nextPageNo
    */
   public int getNextPageNo() {
       return nextPageNo;
   }

   /**
    * @param nextPageNo the nextPageNo to set
    */
   public void setNextPageNo(int nextPageNo) {
       this.nextPageNo = nextPageNo;
   }

   /**
    * @return the finalPageNo
    */
   public int getFinalPageNo() {
       return finalPageNo;
   }

   /**
    * @param finalPageNo the finalPageNo to set
    */
   public void setFinalPageNo(int finalPageNo) {
       this.finalPageNo = finalPageNo;
   }

   /**
    * @return the totalCount
    */
   public int getTotalCount() {
       return totalCount;
   }

   /**
    * @param totalCount the totalCount to set
    */
   public void setTotalCount(int totalCount) {
       this.totalCount = totalCount;
      
   }

   /**
    * 페이징 생성
    */
   public void makePaging() {
       if (this.totalCount == 0) {this.setPageNo(1);return; }// 게시 글 전체 수가 없는 경우
       if (this.pageNo == 0) this.setPageNo(1); // 기본 값 설정
       if (this.pageSize == 0) this.setPageSize(10); // 기본 값 설정

       int finalPage = (totalCount + (pageSize - 1)) / pageSize; // 마지막 페이지
       if (this.pageNo > finalPage) this.setPageNo(finalPage); // 기본 값 설정

       if (this.pageNo < 0 || this.pageNo > finalPage) this.pageNo = 1; // 현재 페이지 유효성 체크

       boolean isNowFirst = pageNo == 1 ? true : false; // 시작 페이지 (전체)
       boolean isNowFinal = pageNo == finalPage ? true : false; // 마지막 페이지 (전체)

       int startPage = ((pageNo - 1) / maxPageNum) * maxPageNum + 1; // 시작 페이지 (페이징 네비 기준)
       int endPage = startPage + maxPageNum - 1; // 끝 페이지 (페이징 네비 기준)

       if (endPage > finalPage) { // [마지막 페이지 (페이징 네비 기준) > 마지막 페이지] 보다 큰 경우
           endPage = finalPage;
       }

       this.setFirstPageNo(1); // 첫 번째 페이지 번호

       if (isNowFirst) {
           this.setPrevPageNo(1); // 이전 페이지 번호
       } else {
           this.setPrevPageNo(((pageNo - 1) < 1 ? 1 : (pageNo - 1))); // 이전 페이지 번호
       }

       this.setStartPageNo(startPage); // 시작 페이지 (페이징 네비 기준)
       this.setEndPageNo(endPage); // 끝 페이지 (페이징 네비 기준)

       if (isNowFinal) {
           this.setNextPageNo(finalPage); // 다음 페이지 번호
       } else {
           this.setNextPageNo(((pageNo + 1) > finalPage ? finalPage : (pageNo + 1))); // 다음 페이지 번호
       }

       this.setFinalPageNo(finalPage); // 마지막 페이지 번호
   }

public int getMaxPageNum() {
	return maxPageNum;
}

public void setMaxPageNum(int maxPageNum) {
	this.maxPageNum = maxPageNum;
}


}