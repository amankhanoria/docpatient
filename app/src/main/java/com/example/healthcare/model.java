package com.example.healthcare;

public class model {
    private String FullName;
    private String Uid;

    private model(){ }

    public model(String FullName,String Uid){
        this.FullName=FullName;
        this.Uid=Uid;
    }


    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        this.Uid = uid;
    }

    // public long getPrice(){
    //    return price;
   // }
   // public void setPrice(){
    //    this.price=price;
   // }
}
