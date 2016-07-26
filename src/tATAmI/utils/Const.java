package tATAmI.utils;

import java.io.IOException;
import java.util.HashMap;

import tATAmI.fwk_logging.Log;
import tATAmI.utils.parsing.ConfigParsingException;
import tATAmI.utils.parsing.ParseConfig;

public class Const {
    
    public final static String ROOT_PATH="root.config";
    
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
    private static String LOGGING_CONFIG;
    private static String AGENTS_CONFIG;
    private static String AGENTS_FOLDER;
    private static String ARTIFACTS_CONFIG;
    
    public static boolean initConstants(){
        try{
            HashMap<String, String> map = ParseConfig.parse(ROOT_PATH);
            
            if(!map.containsKey("logging-config")){
                Log.e(LOGGING_CONFIG + " missing from " + ROOT_PATH);
                return false;
            }
            if (!map.containsKey("agents-config")) {
                Log.e(AGENTS_CONFIG + " missing from " + ROOT_PATH);
                return false;
            }
            if (!map.containsKey("agents-folder")) {
                Log.e(AGENTS_FOLDER + " missing from " + ROOT_PATH);
                return false;
            }
            if (!map.containsKey("artifacts-config")) {
                Log.e(ARTIFACTS_CONFIG + " missing from " + ROOT_PATH);
                return false;
            }
            
            LOGGING_CONFIG = map.get("logging-config");
            AGENTS_CONFIG = map.get("agents-config");
            AGENTS_FOLDER = map.get("agents-folder");
            ARTIFACTS_CONFIG = map.get("artifacts-config");
            
        }
        catch(ConfigParsingException e){
            Log.v(e);
            return false;
        }
        catch(IOException e){
            Log.v(e);
            return false;
        }
        return true;
    }
    
    public static String getLoggingConfig(){
        return LOGGING_CONFIG;
    }
}
