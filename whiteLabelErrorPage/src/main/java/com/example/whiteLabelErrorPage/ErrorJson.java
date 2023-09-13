package com.example.whiteLabelErrorPage;

import lombok.Data;

import java.util.Map;

@Data
public class ErrorJson
{
    private Integer status;
    private String error;
    private String message;
    private String timestamp;
    private String trace;
    public ErrorJson(int status, Map<String,Object> errorAttributes)
    {
        this.status=status;
        this.error=(String) errorAttributes.get ( "error" );
        this.message=(String) errorAttributes.get ( "message" );
        this.trace=(String) errorAttributes.get("trace");
        this.timestamp=errorAttributes.get ( "timestamp" ).toString ();
    }

}
