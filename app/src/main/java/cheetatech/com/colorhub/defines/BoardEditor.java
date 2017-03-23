package cheetatech.com.colorhub.defines;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class BoardEditor {
    private static BoardEditor ourInstance = null;
    private Context context = null;

    public static BoardEditor getInstance()
    {
        if(ourInstance == null)
            ourInstance = new BoardEditor();
        return ourInstance;
    }

    private BoardEditor() {
    }

    public Context getContext()
    {
        return this.context;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }
    public void copyToClipBoard(String text)
    {
        ClipboardManager clipMan = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("text",text);
        clipMan.setPrimaryClip(clip);
    }
}
