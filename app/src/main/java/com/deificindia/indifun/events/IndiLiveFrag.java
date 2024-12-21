package com.deificindia.indifun.events;

/*
if(EventBus.getDefault().hasSubscriberForEvent(IndiLiveFrag.class)){
      EventBus.getDefault().post(new IndiLiveFrag(1));
}
* */
public class IndiLiveFrag {

    public static final int IndiLiveFrag_PAGER_ENABLE =1;
    public static final int IndiLiveFrag_PAGER_DISENABLE =2;


    public int WHAT;
    public boolean boo;

    public IndiLiveFrag() { }

    public IndiLiveFrag(int WHAT) {
        this.WHAT = WHAT;
    }

    public IndiLiveFrag(int WHAT, boolean boo) {
        this.WHAT = WHAT;
        this.boo = boo;
    }
}
