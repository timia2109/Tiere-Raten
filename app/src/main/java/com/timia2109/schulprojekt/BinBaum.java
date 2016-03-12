package com.timia2109.schulprojekt;

import java.io.Serializable;
public class BinBaum implements Serializable
{   BinBaum nextL, nextR;
    Object content;
    public BinBaum() {}
    public BinBaum(Object pC){content=pC;}
    public BinBaum(Object pC, BinBaum pLeft, BinBaum pRight){content=pC;nextL=pLeft;nextR=pRight;}
    public void setContent(Object pOb){content=pOb;}
    public Object getContent(){return content;}
    public void setLeft(BinBaum pN){nextL=pN;}    
    public BinBaum getLeft(){return nextL;}
    public void setRight(BinBaum pN){nextR=pN;}
    public BinBaum getRight(){return nextR;}
    public boolean isEmpty(){return content==null;}
    public boolean isLeaf(){return nextL==null&&nextR==null;}
}
