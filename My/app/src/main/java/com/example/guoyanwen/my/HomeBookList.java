package com.example.guoyanwen.my;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeBookList {
    private String[] bookid;
    private int num;
    private int[] inoc;
    private String[] bookname;
    public String[] getBookid() {
        return bookid;
    }

    public List initData(int kind){
        num = 0;
        List<Map<String,Object>> list;
        if(kind==1){
            int count=0;
            bookid = new String[Book.count1];
            inoc = new int[Book.count1];
            bookname = new String[Book.count1];
            for(int i=0;i<Book.sum;i++){
                if((Book.kind[i]).equals("1")){
                    bookid[count] = Book.bookid[i];
                    inoc[count] = Book.inoc[i];
                    bookname[count]=Book.bookname[i];
                    count++;
                }
            }
        }else if(kind==2){
            int count=0;
            bookid = new String[Book.count1];
            inoc = new int[Book.count1];
            bookname = new String[Book.count1];
            for(int i=0;i<Book.sum;i++){
                if((Book.kind[i]).equals("2")){
                     bookid[count] = Book.bookid[i];
                     inoc[count] = Book.inoc[i];
                     bookname[count]=Book.bookname[i];
                     count++;
                }
            }
        }
        Map<String,Object> map;
        list = new ArrayList<Map<String,Object>>();
        for(int i=0;i<Book.count1;i++){
            map = new HashMap<String,Object>();
            map.put("img",inoc[i]);
            map.put("text",bookname[i]);
            list.add(map);
        }
        return list;
    }

}
