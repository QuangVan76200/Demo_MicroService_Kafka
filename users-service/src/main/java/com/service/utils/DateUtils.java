package com.service.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateUtils {

	public LocalDateTime convertDateToLocalDateTime(Date date) {
		try {
			String dateFormat = "dd/MM/yyyy HH:mm";
			SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
			String dateString = formatter.format(date);
			LocalDateTime localDateTime = LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(dateFormat));
			return localDateTime;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}