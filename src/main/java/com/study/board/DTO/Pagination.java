package com.study.board.DTO;

public class Pagination {

    private int ppPage;
    private int pPage;
    private int nPage;
    private int nnPage;
    private int cPage;
    private int sPage;
    private int ePage;

    private int pageSize;

    public int getPpPage() {
        return ppPage;
    }
    public void setPpPage(int ppPage) {
        this.ppPage = ppPage;
    }
    public int getpPage() {
        return pPage;
    }
    public void setpPage(int pPage) {
        this.pPage = pPage;
    }
    public int getnPage() {
        return nPage;
    }
    public void setnPage(int nPage) {
        this.nPage = nPage;
    }
    public int getNnPage() {
        return nnPage;
    }
    public void setNnPage(int nnPage) {
        this.nnPage = nnPage;
    }
    public int getcPage() {
        return cPage;
    }
    public void setcPage(int cPage) {
        this.cPage = cPage;
    }
    public int getsPage() {
        return sPage;
    }
    public void setsPage(int sPage) {
        this.sPage = sPage;
    }
    public int getePage() {
        return ePage;
    }
    public void setePage(int ePage) {
        this.ePage = ePage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
