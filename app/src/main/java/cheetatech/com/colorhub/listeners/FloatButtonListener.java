package cheetatech.com.colorhub.listeners;

public class FloatButtonListener {

    public interface OnFabListener {
        void onFabSetColor(int color);
    }

    private static FloatButtonListener mInstance = null;
    private OnFabListener  mListener = null;
    private boolean mState;

    private FloatButtonListener () {}

    public static FloatButtonListener getInstance() {
        if(mInstance == null) {
            mInstance = new FloatButtonListener();
        }
        return mInstance;
    }

    public void setListener(OnFabListener listener) {
        mListener = listener;
    }


    public void setFabColor(int color)
    {
        if(mListener!=null)
            mListener.onFabSetColor(color);
    }
}
