package com.aknela.simpel.models;

import com.google.gson.annotations.SerializedName;

public class BeritaItem{

	@SerializedName("gambar")
	private String foto;

//	@SerializedName("publisher")
//	private String publisher;

	@SerializedName("judul")
	private String title;

	@SerializedName("id_info")
	private String idBerita;

	@SerializedName("waktu")
	private String publishDate;

	@SerializedName("isi")
	private String content;

//	@SerializedName("update_date")
//	private String updateDate;

	public void setFoto(String foto){
		this.foto = foto;
	}

	public String getFoto(){
		return foto;
	}

//	public void setPublisher(String publisher){
//		this.publisher = publisher;
//	}
//
//	public String getPublisher(){
//		return publisher;
//	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setIdBerita(String idBerita){
		this.idBerita = idBerita;
	}

	public String getIdBerita(){
		return idBerita;
	}

	public void setPublishDate(String publishDate){
		this.publishDate = publishDate;
	}

	public String getPublishDate(){
		return publishDate;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return content;
	}

//	public void setUpdateDate(String updateDate){
//		this.updateDate = updateDate;
//	}
//
//	public String getUpdateDate(){
//		return updateDate;
//	}

	@Override
 	public String toString(){
		return 
			"BeritaItem{" + 
			"gambar = '" + foto + '\'' +
//			",publisher = '" + publisher + '\'' +
			",judul = '" + title + '\'' +
			",id_info = '" + idBerita + '\'' +
			",waktu = '" + publishDate + '\'' +
			",isi = '" + content + '\'' +
//			",update_date = '" + updateDate + '\'' +
			"}";
		}
}