package com.deificindia.indifun.pojo.dashboardpojo;

import com.google.gson.annotations.SerializedName;

public class Result{

	@SerializedName("followers")
	private int followers;

	@SerializedName("following")
	private int following;

	@SerializedName("groups")
	private int groups;

	@SerializedName("friends")
	private int friends;

	public void setFollowers(int followers){
		this.followers = followers;
	}

	public int getFollowers(){
		return followers;
	}

	public void setFollowing(int following){
		this.following = following;
	}

	public int getFollowing(){
		return following;
	}

	public void setGroups(int groups){
		this.groups = groups;
	}

	public int getGroups(){
		return groups;
	}

	public void setFriends(int friends){
		this.friends = friends;
	}

	public int getFriends(){
		return friends;
	}

	@Override
 	public String toString(){
		return 
			"Result{" + 
			"followers = '" + followers + '\'' + 
			",following = '" + following + '\'' + 
			",groups = '" + groups + '\'' + 
			",friends = '" + friends + '\'' + 
			"}";
		}
}