package cheetatech.com.colorhub.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cheetatech.com.colorhub.R;

/**
 * Created by erkan on 20.03.2017.
 */

public class SaveDialog extends DialogFragment {

    @BindString(R.string.save_dialog)
    String mSaveDialog;

    @BindView(R.id.edittext_palette_name)
    TextInputEditText mPaletteName;

    private Unbinder unbinder;
    private OnSaveListener mListener;

    public interface OnSaveListener{
        void onSavedName(String name);
    }

    public SaveDialog(){
    }

    public static SaveDialog newInstance(OnSaveListener listener){
        SaveDialog fragment = new SaveDialog();
        fragment.setListener(listener);
        return fragment;
    }

    public OnSaveListener getListener() {
        return mListener;
    }

    public void setListener(OnSaveListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        Dialog dialog = new Dialog(getActivity(), R.style.CustomDialog);
        View view = getActivity().getLayoutInflater().inflate(R.layout.save_dialog, null);
        dialog.getWindow().setContentView(view);
        unbinder = ButterKnife.bind(this,view);

        dialog.setTitle(mSaveDialog);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
    @OnClick(R.id.cancel_button) void cancelButtonClick(){
        dismiss();
    }

    @OnClick(R.id.add_button) void addButtonClick(){
        String paletteName = mPaletteName.getText().toString().trim();
        if(paletteName.length() == 0)
            return;
        if(this.mListener != null)
            this.mListener.onSavedName(paletteName);
        dismiss();
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
