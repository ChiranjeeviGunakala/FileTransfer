package com.oup.ae.integration.ftpfiletransfer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.file.GenericFileFilter;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Component;

@Component("CustomFileFilter")
public class CustomFileFilter<T> implements GenericFileFilter<T>  {	
	
	@Override
	public boolean accept(GenericFile<T> file) {
		
		
		Calendar today = DateUtils.toCalendar(new Date());		
		if((file.getLastModified() - today.getTimeInMillis()) / (1000.0 * 60 * 60 * 24) < 0) 
			return true;		
		else 
			return false;
		
	}
	
	public int daysBetween(Date d1, Date d2) {
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }
		
}
