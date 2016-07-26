package tATAmI.fwk_logging;

import java.util.HashMap;

import tATAmI.utils.Const;
import tATAmI.utils.parsing.ParseConfig;

public class Log {
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
    
    private static String DEFAULT_TAG = "N/A";

    private static OSType mOSType = OSType.DEFAULT;
    
    private static LogContext mContext = LogContext.NOT_SPECIFIED;
    
    private static int mLogLevel = LogLevel.ALL;
    
    private static HashMap<String, String> mLogTags = new HashMap<String, String>();

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
            if(map.containsKey("log-tags")){
                String[] tagAssignments = map.get("log-tags").split(",");
                for(int i = 0; i < tagAssignments.length; ++i){
                    tagAssignments[i] = tagAssignments[i].substring(1, tagAssignments[i].length()-1);
                    String[] tokens = tagAssignments[i].split(";");
                    String tag = tokens[0].trim();
                    for(int j = 1; j < tokens.length; ++j){
                        mLogTags.put(tokens[j].trim(), tag);
                    }
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
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        
        String stackLine = stack[stack.length-2].toString();
        String className = stackLine.toString().substring(0, stackLine.indexOf("("));
        className = className.substring(0, className.lastIndexOf("."));
        
        if(mLogTags.containsKey(className)){
            message = mLogTags.get(className) + "\t" + message;
        }
        else{
            message = DEFAULT_TAG + "\t" + message;
        }
        
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
