package com.cvte.note.model;

/**
 * Created by user on 2016/10/18.
 */

public interface INoteModel {
    public static  final int XIANGPI=0;//橡皮
    public static final int BI=1;//笔
    public static final int XIAN = 2;//线
    public static final  int YUANXING =3;//圆形
    public static final int JUXING=4;//矩形
    public void SetXiangPiLocation(float x, float y,float startX,float startY);
}
