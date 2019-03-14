package com.oup.ae.integration.ftpfiletransfer.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component("FTPTransferRoute")
public class FTPTransferRoute extends RouteBuilder{

	
	@Override
	public void configure() throws Exception {

		from("ftp:{{ftp.source.server}}:{{ftp.source.port}}{{ftp.source.pickup.location}}?password={{ftp.source.password}}&username={{ftp.source.username}}&include=EXTSTK.*.dat&doneFileName=${file:name}.go&delete=true&passiveMode=true")       
        .to("controlbus:route?async=true&action=start&routeId=FTPFileCleanUp")
        .delay(2000)
        .log("*********** Polled the file $simple{header.CamelFileName} from the FTP server ***********")
        .convertBodyTo(String.class)
        .wireTap("file:{{file.backup.location}}/FileProcessed?fileName=${date:now:yyyy/MM/dd/}$simple{header.CamelFileName}")
        .toD("ftps:{{ftp.destination.server}}:{{ftp.destination.port}}{{ftp.destination.drop.location}}?password={{ftp.destination.password}}&username={{ftp.destination.username}}&ftpClient.strictReplyParsing=false&passiveMode=true")
        .log("*********** File $simple{header.CamelFileName} transfered to HK SAP server ***********");

	}

}
