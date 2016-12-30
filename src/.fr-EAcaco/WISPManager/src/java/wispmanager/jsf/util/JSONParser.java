/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wispmanager.jsf.util;

import java.util.HashMap;
import java.util.StringTokenizer;

/**
 *
 * @author torsello
 */
public class JSONParser {
    
    private HashMap mapJSON=new HashMap();

    public JSONParser(String strJSON) {
        ParseJSONLine(strJSON);
    }
    
    

    public HashMap getMap()
    {
        return mapJSON;
    }

    public void ParseJSONLine(String strJSON)
    {
        StringTokenizer strTok=new StringTokenizer(strJSON,"(){}");
        while(strTok.hasMoreElements())
        {
            String strLine=strTok.nextToken();
            StringTokenizer TokField=new StringTokenizer(strLine,",");
            while(TokField.hasMoreElements())
            {
                String strTokReq=TokField.nextToken();
                StringTokenizer TokReq=new StringTokenizer(strTokReq,":\"");
                String FieldName="",FieldValue="";
                if (TokReq.hasMoreElements())
                    FieldName=TokReq.nextToken();
                if (TokReq.hasMoreElements())
                    FieldValue=TokReq.nextToken();
                mapJSON.put(FieldName, FieldValue);
            }
        
        }                
    }

}
