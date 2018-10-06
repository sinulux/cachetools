package com.sinosoft.vms.rtmptools;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sinosoft.vms.rtmptools.dao")
public class RtmptoolsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RtmptoolsApplication.class, args);
	}
}
