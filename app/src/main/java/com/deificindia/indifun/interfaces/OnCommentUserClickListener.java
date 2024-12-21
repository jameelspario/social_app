package com.deificindia.indifun.interfaces;

import android.view.View;

public interface OnCommentUserClickListener {
    boolean onComment(int pos, String id, String postby, View v);
    void onLikeClick();

}
