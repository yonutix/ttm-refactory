package tATAmI.simulation;

import tATAmI.Const;
import tATAmI.fwk_logging.Log;

public class Boot {
    
    public void boot(){
        if(Const.initConstants()){
            Log.i("All constants loaded successfully");
        }
        else{
            Log.e("Constants could not be loaded");
        }
        
        if(Log.initLog()){
            Log.i("Log settings loaded");
        }
        else{
            Log.v("Log settings could not be loaded");
        }
        
    }
    
   
    public static void main(String[] args) {
       new Boot().boot();
    }

}
