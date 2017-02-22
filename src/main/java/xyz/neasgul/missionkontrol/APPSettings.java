package xyz.neasgul.missionkontrol;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

/**
 * Created by benoitchopinet on 02/08/2016.
 */
public class APPSettings {
    private static APPSettings  settings;
    private static final String APP_DIRECTORY = ".KerbalAutoPilot";
    private static final String APP_SETTING_NAME = "config.properties";

    private static HashMap<String, String> mSettings_map;

    public static String DEFAULT_CONNEXION_ADDRESS = "DEFAULT_CONNEXION_ADDRESS";
    public static String DEFAULT_CONNEXION_NAME = "DEFAULT_CONNEXION_NAME";
    public static String SAVE_CONNEXION_INFO = "SAVE_CONNEXION_INFO";


    public static APPSettings getSettings() {
        if(settings==null){
            settings = new APPSettings();
        }
        return settings;
    }

    private APPSettings(){
        mSettings_map = new HashMap<>();
        checkSettings();
        loadSettings();
    }

    private void loadSettings(){
        Properties prop = new Properties();
        InputStream input;
        try {
            input = new FileInputStream(getSettingsFile());
            // load a properties file
            prop.load(input);
            if(prop.isEmpty()){
                // if empty set it to default value
                mSettings_map.put(DEFAULT_CONNEXION_ADDRESS,"127.0.0.1");
                mSettings_map.put(DEFAULT_CONNEXION_NAME,"Localhost");
                mSettings_map.put(SAVE_CONNEXION_INFO,"true");
                // and save it
                saveSettings();
            }else {
                // get the property value and print it out
                prop.forEach((k, v) -> {
                    mSettings_map.put((String) k, (String) v);
                });
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveSettings(){
        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream(getSettingsFile());

            // set the properties value
            mSettings_map.forEach((k, v) -> {
                prop.setProperty(k,v);
            });

            // save properties to project root folder
            prop.store(output, "Kerbal Space Program Autopilot Config file \nDO NOT MODIFY !!!");

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void addSetting(String SettingType, String value){
        mSettings_map.put(SettingType,value);
    }

    public String getSetting(String SettingType) {
        return mSettings_map.get(SettingType);
    }

    private void checkSettings(){
        Path path = Paths.get(getSettingsDirectory().getAbsolutePath());
        System.out.println(path);
        try{
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path);
            Iterator<Path> iterator = directoryStream.iterator();
            boolean exist = false;
            while (iterator.hasNext()){
                Path current = iterator.next();
                if(current.endsWith(APP_SETTING_NAME)){
                    exist = true;
                    break;
                }
            }
            directoryStream.close();
            if (exist == false) {
                FileWriter fw = new FileWriter(getSettingsFile());
                fw.close();

            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    private static File getSettingsDirectory() {
        String userHome = System.getProperty("user.home");
        if(userHome == null) {
            throw new IllegalStateException("user.home==null");
        }
        File home = new File(userHome);
        File settingsDirectory = new File(home, APP_DIRECTORY);
        if(!settingsDirectory.exists()) {
            if(!settingsDirectory.mkdir()) {
                throw new IllegalStateException(settingsDirectory.toString());
            }
        }
        return settingsDirectory;
    }

    private String getSettingsFile(){
        return getSettingsDirectory().getAbsolutePath()+'/'+APP_SETTING_NAME;
    }



}
