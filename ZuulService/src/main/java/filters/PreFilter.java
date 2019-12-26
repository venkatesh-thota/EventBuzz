package filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class PreFilter extends ZuulFilter {

    @Override
    public String filterType()
    {
        return "pre";
    }
    @Override
    public int filterOrder()
    {
        return 1;
    }
     @Override
     public boolean shouldFilter()
     {
         return true;
     }
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

         return null;
    }


}
