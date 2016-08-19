package cheetatech.com.colorhub.listeners;

public class ListenerModel {

    public interface OnModelStateListener {
        void onSelectedColorIndex(int index);
    }

    private static ListenerModel mInstance = null;
    private OnModelStateListener  mListener = null;
    private boolean mState;

    private ListenerModel () {}

    public static ListenerModel getInstance() {
        if(mInstance == null) {
            mInstance = new ListenerModel();
        }
        return mInstance;
    }

    public void setListener(OnModelStateListener listener) {
        mListener = listener;
    }


    public void setListenerIndex(int index)
    {
        if(mListener!=null)
            mListener.onSelectedColorIndex(index);
    }
}
