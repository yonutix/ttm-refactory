package tATAmI.fwk_logging;

import java.util.HashMap;

import tATAmI.Const;
import tATAmI.utils.parsing.ParseConfig;

public class Log {

    private static String DEFAULT_LOG = "N/A";

    private enum OSType {
        DEFAULT, PC
    };

    private enum LogContext {
        NOT_SPECIFIED, ECLIPSE, TERMINAL
    };
    
    private class LogLevel {
        public static final int NONE = 0x0;
        public static final int VERBOSE = 0x1;
        public static final int INFO = 0x2;
        public static final int DEBUG = 0x4;
        public static final int WARNING = 0x8;
        public static final int ERROR = 0x10;
        public static final int ALL = 0x1F;
    };

    private static OSType mOSType = OSType.DEFAULT;
    
    private static LogContext mContext = LogContext.NOT_SPECIFIED;
    
    private static int mLogLevel = LogLevel.ALL;

    public static boolean initLog() {
        mLogLevel = LogLevel.NONE;
        mContext = LogContext.NOT_SPECIFIED;
        
        try{
            HashMap<String, String> map = ParseConfig.parse(Const.getLoggingConfig());

            if(map.containsKey("vebose")){
                mLogLevel |= LogLevel.VERBOSE;
            }
            if(map.containsKey("info")){
                mLogLevel |= LogLevel.INFO;
            }
            if(map.containsKey("debug")){
                mLogLevel |= LogLevel.DEBUG;
            }
            if(map.containsKey("warning")){
                mLogLevel |= LogLevel.WARNING;
            }
            if(map.containsKey("error")){
                mLogLevel |= LogLevel.ERROR;
            }
            if(map.containsKey("log-context")){
                String logContext = map.get("log-context");
                if(logContext.equals("eclipse")){
                    mContext = LogContext.ECLIPSE;
                }
                if(logContext.equals("terminal")){
                    mContext = LogContext.TERMINAL;
                }
                
            }
        }
        catch(Exception e){
            e(e);
            return false;
        }
        return true;
    }

    public static void v(String message) {
        if((mLogLevel & LogLevel.VERBOSE)  == 0)
            return;
        printContextBased(message, Const.ANSI_YELLOW, Const.ANSI_RESET);
    }

    public static void v(Exception e) {
        if((mLogLevel & LogLevel.VERBOSE)  == 0)
            return;
        v(e.getMessage());
        e.printStackTrace();
    }
    
    public static void e(Exception e) {
        if((mLogLevel & LogLevel.ERROR)  == 0)
            return;
        e(e.getMessage());
        e.printStackTrace();
    }

    public static void i(String message) {
        if((mLogLevel & LogLevel.INFO)  == 0)
            return;
        printContextBased(message, Const.ANSI_GREEN, Const.ANSI_RESET);
    }

    public static void w(String message) {
        if((mLogLevel & LogLevel.WARNING)  == 0)
            return;
        printContextBased(message, Const.ANSI_YELLOW, Const.ANSI_RESET);
    }

    public static void e(String message) {
        if((mLogLevel & LogLevel.ERROR)  == 0)
            return;
        printContextBased(message, Const.ANSI_RED, Const.ANSI_RESET);
    }
    
    private static void printContextBased(String message, String startColor, String endColor) {
        switch (mContext) {
        case NOT_SPECIFIED:
            printOSBased(message);
            break;
        case ECLIPSE:
            printOSBased(message);
            break;
        case TERMINAL:
            printOSBased(startColor + message + endColor);
            break;
        }
    }
    
    private static void printOSBased(String message){
        switch (mOSType) {
        case PC:
            System.out.println(message);
            break;
        default:
            System.out.println(message);
            break;
        }
    }

}
