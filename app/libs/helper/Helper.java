package libs.helper;

import play.Play;

public class Helper {

    public static String getConf(String str) {
        return Play.application().configuration().getString(str);
    }

}
