package com.sinosoft.vms.rtmptools.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sinosoft.vms.rtmptools.entity.AuthUser;
import com.sinosoft.vms.rtmptools.service.AuthUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 可以接收的POST参数： 
app=live
flashver=
swfurl=
tcurl=rtmp://111.75.227.181/live
pageurl=
addr=218.87.16.97
clientid=5
call=publish
name=st
type=live
pass=mm
 * @author zkr
 *
 */
@Controller
public class AuthController {
	public static Logger log = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private AuthUserService authUserService;

	@RequestMapping("/on_publish")
	public void on_publish(HttpServletRequest request, HttpServletResponse response) throws IOException {
		/*/log.info("当前登录地址:" +request.getServletPath()+"?"+ request.getQueryString());
		Map<String, String[]> params = request.getParameterMap();    
        String queryString = "";    
        for (String key : params.keySet()) {    
            String[] values = params.get(key);    
            for (int i = 0; i < values.length; i++) {    
                String value = values[i];    
                queryString += key + "=" + value + "\n";    
            }    
        }    
        // 去掉最后一个空格    
        // queryString = queryString.substring(0, queryString.length() - 1); 
        log.info("当前请求参数:"+queryString);
        //*/
		long startTime = System.currentTimeMillis();
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");
		log.info(name+"正在请求发布直播，验证密码为："+pass);
		String password = authUserService.getAuthUser(name);
		if (pass.equals(password)) {
			response.setStatus(200);
		} else {
			response.setStatus(404);
		}
		response.getWriter().print("OK");
		response.getWriter().flush();
		long endTime = System.currentTimeMillis();
		log.info("本次请求消耗：{}毫秒",endTime - startTime);
	}
}
