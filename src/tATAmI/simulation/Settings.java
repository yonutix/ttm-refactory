package tATAmI.simulation;

public class Settings {
    
    private static Settings singleton = new Settings();
    
    private Settings(){
        
    }
    
    
    public static Settings getInst(){
        return singleton;
    }
    
    
}
