package com.jdy.collections;


public class PushData{
	private int errorno=0;
	private String messsage="ok";
	private String actiontype="";
	private String data = "";
	
	public void setactiontype(String actionType){
		actiontype = actionType;
	}
	
	public String getactiontype(){
		return actiontype;
	}

	public void setmesssage(String Messsage){
		messsage = Messsage;
	}
	
	public String getmesssage(){
		return messsage;
	}

	public void setdata(String Data){
		data = Data;
	}
	
	public String getdata(){
		return data;
	}
	
	public void seterrorno(int errorNo){
		errorno = errorNo;
	}
	
	public int geterrorno(){
		
		return errorno;
	}
}