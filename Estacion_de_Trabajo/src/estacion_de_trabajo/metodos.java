package estacion_de_trabajo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class metodos {
    public int  getCSVCount(File f) throws IOException {
            FileReader fr=new FileReader(f);
            BufferedReader br=new BufferedReader(fr);
            int item =0;
            boolean isEOF=false;
            do{
                String t=br.readLine();
                if(t!=null){
                    isEOF=true;
                    t=t.replaceAll("\\n|\\t|\\s|", "");
                    if (t != null){
                        item = item + 1;
                    }
                }
                else {
                    isEOF=false;
                }
            }while(isEOF);
            br.close();
            fr.close();
            return item;
        }
}
