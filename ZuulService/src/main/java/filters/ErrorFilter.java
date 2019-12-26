package filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Component
public class ErrorFilter extends ZuulFilter
{
    @Override
	public String filterType() {
	return "error";
	}
        @Override
	public int filterOrder() {
	return 1;
	}
        @Override
	public boolean shouldFilter() {
	return true;
	}
        @Override
	public Object run() {
	HttpServletResponse response = RequestContext.getCurrentContext().getResponse();

	//log.info("ErrorFilter: " + String.format("response status is %d", response.getStatus()));

	return null;
}
}
