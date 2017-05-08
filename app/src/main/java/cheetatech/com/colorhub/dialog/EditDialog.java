package cheetatech.com.colorhub.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.view.View;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cheetatech.com.colorhub.R;

/**
 * Created by erkan on 22.03.2017.
 */

public class EditDialog extends DialogFragment {

    @BindString(R.string.edit_dialog)
    String mEditDialog;

    @BindView(R.id.edittext_palette_name)
    TextInputEditText mPaletteName;

    private String name;
    private OnEditListener mListener = null;
    private Unbinder unbinder;

    public interface OnEditListener{
        void onEditedName(String name);
    }


    public EditDialog(){
    }

    public static EditDialog newInstance(String name,OnEditListener listener){
        EditDialog fragment = new EditDialog();
        fragment.setName(name);
        fragment.setListener(listener);
        return fragment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OnEditListener getListener() {
        return mListener;
    }

    public void setListener(OnEditListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.CustomDialog);
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_edit_palette, null);
        dialog.getWindow().setContentView(view);
        unbinder = ButterKnife.bind(this,view);
        dialog.setTitle(mEditDialog);
        dialog.setCanceledOnTouchOutside(false);
        mPaletteName.setText(name);
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
            this.mListener.onEditedName(paletteName);
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

