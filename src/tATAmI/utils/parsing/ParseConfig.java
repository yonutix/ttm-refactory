package tATAmI.utils.parsing;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;

public class ParseConfig {
    
    private ParseConfig(){
    }
    
    public static HashMap<String, String> parse(String filename) throws IOException, ConfigParsingException{
        HashMap<String, String> result = new HashMap<String, String>();
        RandomAccessFile configFile = new RandomAccessFile(filename, "r");
        String line = "";
        while((line = configFile.readLine()) != null){
            if(line.startsWith("#")) continue;
            
            String[] tokens = line.split(":");
            
            if(tokens.length != 2 && tokens.length != 0){
                configFile.close();
                throw new ConfigParsingException();
            }
            if(result.containsKey(tokens[0])){
                configFile.close();
                throw new ConfigParsingException(tokens[0]);
            }
            result.put(tokens[0], tokens[1]);
        }
        
        configFile.close();
        
        return result;
    }
}
