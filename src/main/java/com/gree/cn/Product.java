package com.gree.cn;

public class Product {

    private String dmdq ;
    private String xmbj ;
    private String devdt;



    @Override
    public String toString() {
        return "Product{" +
                "dmdq='" + dmdq + '\'' +
                ", xmbj='" + xmbj + '\'' +
                ", devdt='" + devdt + '\'' +
                '}';
    }




    public String getDmdq() {
        return dmdq;
    }

    public void setDmdq(String dmdq) {
        this.dmdq = dmdq;
    }

    public String getXmbj() {
        return xmbj;
    }

    public void setXmbj(String xmbj) {
        this.xmbj = xmbj;
    }

    public String getDevdt() {
        return devdt;
    }

    public void setDevdt(String devdt) {
        this.devdt = devdt;
    }




}
