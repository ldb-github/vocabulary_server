package com.ldb.vocabulary.server.domain;

import java.sql.Timestamp;
import java.util.List;

public class Vocabulary {

	
	private List<String> cIdList;
	private List<String> cSubIndexList;
	private String id;
	private int subIndex;
	private String name;
	private String image;
	private String imageRemote;
	private String language;
	private String username;
	private Timestamp createTime;
	
	public List<String> getCIdList() {
		return cIdList;
	}
	public void setCIdList(List<String> cIdList) {
		this.cIdList = cIdList;
	}
	public List<String> getcSubIndexList() {
		return cSubIndexList;
	}
	public void setcSubIndexList(List<String> cSubIndexList) {
		this.cSubIndexList = cSubIndexList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getSubIndex() {
		return subIndex;
	}
	public void setSubIndex(int subIndex) {
		this.subIndex = subIndex;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getImageRemote() {
		return imageRemote;
	}
	public void setImageRemote(String imageRemote) {
		this.imageRemote = imageRemote;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	
}
