package tann.village.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


public class Prefs {


    private static Preferences prefs;
    private static Preferences get(){
        if (prefs==null){
            prefs = Gdx.app.getPreferences("my_prefs");
        }
        return prefs;
    }

    public static void setString(String key, String value){
        get().putString(key, value);
        get().flush();
    }

    public static void setInt(String key, int value){
        get().putInteger(key, value);
        get().flush();
    }

    public static void setFloat(String key, float value){
        get().putFloat(key, value);
        get().flush();
    }

    public static String getString(String key, String def){
        return get().getString(key, def);
    }

    public static Integer getInt(String key, int def){
        return get().getInteger(key, def);
    }

    public static float getFloat(String key, float def){
        return get().getFloat(key, def);
    }


}
