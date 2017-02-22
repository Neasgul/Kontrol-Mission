package xyz.neasgul.missionkontrol.Utils;

/**
 * Created by benoit chopinet on 04/05/2016.
 */
public class Utils {
    public static boolean isIPv4(String ip){
        try {
            if ( ip == null || ip.isEmpty() ) {
                return false;
            }

            String[] parts = ip.split( "\\." );
            if ( parts.length != 4 ) {
                return false;
            }

            for ( String s : parts ) {
                int i = Integer.parseInt( s );
                if ( (i < 0) || (i > 255) ) {
                    return false;
                }
            }
            if ( ip.endsWith(".") ) {
                return false;
            }

            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }


    public static final String MainTitle = "Kerbal AutoPilot";
    public static final String SettingsTitle = "KAP Settings";
}
