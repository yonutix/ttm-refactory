package tATAmI.utils.parsing;

public class ConfigParsingException extends Exception{
    private static final long serialVersionUID = 1L;
    
    public ConfigParsingException(){
        super("Invalid config file, the config file must have lines"
                + "of type: <key>:<value> or #comment");
    }
    
    public ConfigParsingException(String key){
        super("Te key " + key + "is used several times, it should be used only once");
    }

}
