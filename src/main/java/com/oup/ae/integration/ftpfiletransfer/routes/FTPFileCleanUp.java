package com.oup.ae.integration.ftpfiletransfer.routes;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component("FTPFileCleanUp")
public class FTPFileCleanUp extends RouteBuilder {

	@Override
	public void configure() throws Exception {		
		
        onCompletion()
        .onWhen(header("CamelBatchComplete")) 
        .log("*********** All File Consumed **************")
        .to("controlbus:route?async=true&action=stop&routeId=FTPFileCleanUp")
        .log("*********** FileMoverRoute Stoped **************");
        
        
		from("ftps:{{ftp.destination.server}}:{{ftp.destination.port}}{{ftp.destination.drop.location}}?password={{ftp.destination.password}}&username={{ftp.destination.username}}&ftpClient.strictReplyParsing=false&delete=true&filter=#CustomFileFilter&passiveMode=true")
		.routeId("FTPFileCleanUp")
		.autoStartup(false)
		.to("ftps:{{ftp.destination.server}}:{{ftp.destination.port}}{{ftp.destination.cleanup.location}}?password={{ftp.destination.password}}&username={{ftp.destination.username}}&ftpClient.strictReplyParsing=false&passiveMode=true")
		.log("*********** Moved the file ${header.CamelFileName} to backup location ***********");

	}

	
}
