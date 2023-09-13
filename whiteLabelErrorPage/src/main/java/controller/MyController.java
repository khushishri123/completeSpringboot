package controller;

import com.example.whiteLabelErrorPage.ErrorJson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.swing.text.html.Option;
import java.util.Collections;
import java.util.Map;

@RestController
public class MyController implements ErrorController {

    @Value("${debug}" )
    private boolean debug;

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public String home()
    {
        return "Welcome";
    }
    //in the below method we are returning a simple text error message , see the next method to return JSON
//    @RequestMapping(value = "/error",method = RequestMethod.GET)
//    public String defaultErrorPage()
//    {
//        return "Requested resource not found!!!!!";
//    }

    @RequestMapping(value="/error",method = RequestMethod.GET)
    ErrorJson error(HttpServletRequest request, HttpServletResponse response)
    {
        //appropriate HTTP response code(eg: 404 , 500) will be automatically set by Spring
        //we just need to define response body
        return new ErrorJson (response.getStatus (),getErrorAttributes(request,debug));
    }

    //we have taken second parameter as includeStackTrace which shows that if the debug value is set as true ,
    // then it will do debug else no
    Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
       WebRequest webRequest=new ServletWebRequest ( request );
       ErrorAttributeOptions errorAttributeOptions=
               includeStackTrace ? ErrorAttributeOptions.of ( ErrorAttributeOptions.Include.STACK_TRACE ):
                       ErrorAttributeOptions.defaults ();
       return errorAttributes.getErrorAttributes ( webRequest,errorAttributeOptions );
    }


}
