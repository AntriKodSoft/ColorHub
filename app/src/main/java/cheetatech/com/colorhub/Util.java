package cheetatech.com.colorhub;


import android.content.res.Resources;
import android.util.Log;

import java.math.BigInteger;

public class Util{
    public static final boolean TEST = true;
    public static final int REQUEST_READ_PHONE_STATE = 0;
    public static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    public static final int REQUEST_INTERNET = 2;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 3;

    public Util(){}


    public static ColorX stringToDec(String color){

        String O = color.substring(1,3);
        String R = color.substring(3,5);
        String G = color.substring(5,7);
        String B = color.substring(7);
        int red, green, blue, opacity;

        try {
            red = Integer.parseInt(R.trim(),16);
            green = Integer.parseInt(G.trim(),16);
            blue = Integer.parseInt(B.trim(),16);
            opacity = Integer.parseInt(O.trim(),16);
        }catch (NumberFormatException e){
            red = green = blue = 0;
            opacity = 255;
            e.printStackTrace();
        }

        Log.e("TAG","R: " + red + " G: " + green + " B: " + blue + " : " + opacity);

        return new ColorX(red,green,blue,opacity);
    }

    public String inverseColor(String color)
    {
        String O = color.substring(1,3);
        String R = color.substring(3,5);
        String G = color.substring(5,7);
        String B = color.substring(7);
        int red, green, blue;
        try {
            red = Integer.parseInt(R);
            green = Integer.parseInt(G);
            blue = Integer.parseInt(B);
        }catch (NumberFormatException e){
            red = green = blue = 0;
            e.printStackTrace();
        }

        String ff = "FF";
        String r = String.format("%02x", red);
        String g = String.format("%02x", green);
        String b = String.format("%02x", blue);

        BigInteger ri = new BigInteger(r,16);
        BigInteger gi = new BigInteger(g,16);
        BigInteger bi = new BigInteger(b,16);

        BigInteger fi = new BigInteger(ff,16);

        BigInteger rs = ri.xor(fi);
        BigInteger gs = gi.xor(fi);
        BigInteger bs = bi.xor(fi);

        String res = String.format("#%02x%02x%02x",rs,gs,bs);
        res = res.toUpperCase();
        return res;
    }

    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }


    public static class ColorX{
        private int red;
        private int green;
        private int blue;
        private int opacity;

        public ColorX(){}

        public ColorX(int red, int green, int blue, int opacity) {
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.opacity = opacity;
        }

        public int getRed() {
            return red;
        }

        public void setRed(int red) {
            this.red = red;
        }

        public int getGreen() {
            return green;
        }

        public void setGreen(int green) {
            this.green = green;
        }

        public int getBlue() {
            return blue;
        }

        public void setBlue(int blue) {
            this.blue = blue;
        }

        public int getOpacity() {
            return opacity;
        }

        public void setOpacity(int opacity) {
            this.opacity = opacity;
        }

        @Override
        public String toString() {
            return "ColorX{" +
                    "red=" + red +
                    ", green=" + green +
                    ", blue=" + blue +
                    ", opacity=" + opacity +
                    '}';
        }
    }
}
