package com.deificindia.firebasefragment;

public class Comment {
    private String comment;
    private String publisher;
    private String commentid;
    private String postid;
    private String time;

    public Comment(String comment,String time,String publisher, String commentid,String postid) {
        this.comment = comment;
        this.publisher = publisher;
        this.commentid = commentid;
        this.postid=postid;
        this.time=time;

    }

    public Comment() {
    }
    public String getTime(){
        return time;
    }
    public void setTime(String time){
        this.time=time;
    }

public String getPostid(){
        return postid;
}
public void setPostid(String postid){
        this.postid=postid;
}
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }
}
