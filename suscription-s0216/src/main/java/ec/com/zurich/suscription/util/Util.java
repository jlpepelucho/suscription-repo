package ec.com.zurich.suscription.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class Util {

    public static boolean isNullOrEmptyAfterTrim(String s) {
        if (s == null) {
            return true;
        }
        return s.trim().isEmpty();
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    public static Timestamp actualDateTime() {
        Calendar calendario = Calendar.getInstance();
        return new Timestamp(calendario.getTime().getTime());
    }
    
    
    public static Date actualDate() {
        Calendar calendario = Calendar.getInstance();
        return new Date(calendario.getTime().getTime());
    }

    
    public static long diasTranscurridos(Timestamp fechaA, Timestamp fechaB) {
        long MINUTE = 60 * 1000;
        long HOUR = MINUTE * 60;
        long DAY = HOUR * 24;
        //long WEEK = DAY * 7;
        return (fechaA.getTime() - fechaB.getTime()) / DAY;
    }
    
    /**
     * CALCULA LOS DIAS TRANSCURRIDOS PERO SE COME UN DIA
     * UTILIZAR diasTranscurridosFacturacioLetras(String sDate_one, String sDate_two)
     *
     * @param fechaA Description of Parameter
     * @param fechaB Description of Parameter
     * @return Description of the Returned Value
     */
    public static long diasTranscurridos(Date fechaA, Date fechaB) {
        long MINUTE = 60 * 999;
        long HOUR = MINUTE * 60;
        long DAY = HOUR * 24;
        //long WEEK = DAY * 7;
        return (fechaA.getTime() - fechaB.getTime()) / DAY;
    }

    /**
     * CALCULA LOS DIAS TRANSCURRIDOS  COPIADA DEL INTERNET VHBV
     *
     * @param sDate_one s
     * @param sDate_two s
     * @return long
     */
    public static long diasTranscurridosFacturacioLetras(String sDate_one, String sDate_two) {
        long diff_days = 0;
        Date date_one = null;
        Date date_two = null;
        String datePattern = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);

        try {
            date_one = new Date(dateFormat.parse(sDate_one).getTime());
        } catch (ParseException e) {
            System.out.println("La Fecha [" + sDate_one + "] debe tener formato  [" + datePattern + "] ! Error: " + e.getMessage());
        }

        try {
            date_two = new Date(dateFormat.parse(sDate_two).getTime());
        } catch (ParseException e) {
            System.out.println("La Fecha [" + sDate_two + "] debe tener formato  [" + datePattern + "] ! Error: " + e.getMessage());
        }

        if (date_one != null && date_two != null) {
            long diff_ms = Math.abs(date_two.getTime() - date_one.getTime());

            diff_days = diff_ms / (24 * 60 * 60 * 1000);

            // System.out.println("La diferencia entre" + sDate_one + " y " + sDate_two + " es " + diff_ms + " ms, o " + diff_days + " días.");
        }
        //return diff_days<0?-diff_days:0;

        	return diff_days;
    }
    
    public static Timestamp dateToTimestamp(Date fecha) {
        Timestamp d = null;
        try {
            LocalDate localDate = fecha.toLocalDate();

            String valor_fechas;
            int ah;
            int mh;
            int dh;
            int xh = 16;
            int yh = 0;
            int zh = 0;
            ah = localDate.getYear();
            mh = localDate.getMonthValue();
            dh = localDate.getDayOfMonth();
            String tresula = Integer.toString(ah);
            String tresulm = Integer.toString(mh);
            String tresuld = Integer.toString(dh);
            String tresulx = Integer.toString(xh);
            String tresuly = Integer.toString(yh);
            String tresulz = Integer.toString(zh);
            valor_fechas = tresuld + "/" + tresulm + "/" + tresula + " " + tresulx + ":" + tresuly + ":" + tresulz;
            d = new Timestamp((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(valor_fechas).getTime());
        } catch (ParseException e) {
            return d;
        }
        return d;
    }

    public static Date getDateFromLocalDate(LocalDate localDate)
	{
		return Date.valueOf(localDate);
	}
	
    public static Date getDateFromLocalDateTime(LocalDateTime localDateTime)
	{
		return Date.valueOf(localDateTime.toLocalDate());
	}

    public static Date getDateFromOffSetDateTime(OffsetDateTime offsetDateTime )
	{
    	if (null != offsetDateTime) {
    		return Date.valueOf(offsetDateTime.toLocalDate());
    	}
		return null;
	}
    
    public static OffsetDateTime getOffsetDateTimeFromDate(Date date) {

		try {
            long milliseconds = date.getTime();
            return OffsetDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneOffset.UTC);

		} catch (Exception e) {
			return null;
		}
	}

    /**
     * Retorna el campo de fecha de una fecha determinada
     *
     * @param campoFecha Campo que se desea obtener
     * @param fecha      Fecha de la que se desea obtener el campo
     * @return Valor del campo de la fecha
     */
    public static int getCampoFecha(int campoFecha, Timestamp fecha) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fecha);
        return calendario.get(campoFecha);
    }
    
    public static int getCampoFecha(int campoFecha, Date fecha) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fecha);
        return calendario.get(campoFecha);
    }
    
    public static Date stringToDate(String fecha) {
		Date d = null;
		try {
			d = new Date((new SimpleDateFormat("dd/MM/yyyy")).parse(fecha).getTime());
		} catch (ParseException e) {
			return d;
		}
		return d;
	}
    
    public static Timestamp yearAheadVigenciaDateTime(Timestamp fecha) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fecha);
		calendario.add(Calendar.YEAR, 1);
        return new Timestamp(calendario.getTime().getTime());
	}
    
    public static Timestamp yearAheadVigenciaDateTime(Date fecha) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fecha);
		calendario.add(Calendar.YEAR, 1);
        return new Timestamp(calendario.getTime().getTime());
	}
    /**
     * Transformar un Timestamp a Date
     *
     * @param fechaT Description of Parameter
     * @return Description of the Returned Value
     */
    public static Date timestampToDate(Timestamp fechaT) {
        Date d = null;
        try {
            LocalDate localDate = new Date(fechaT.getTime()).toLocalDate();
            String valor_fechas;
            int ah;
            int mh;
            int dh ;
            ah = localDate.getYear() ;
            mh = localDate.getMonthValue() ;
            dh = localDate.getDayOfMonth();
            String tresula = Integer.toString(ah);
            String tresulm = Integer.toString(mh);
            String tresuld = Integer.toString(dh);
            valor_fechas = tresuld + "/" + tresulm + "/" + tresula;
            d = new Date((new SimpleDateFormat("dd/MM/yyyy")).parse(valor_fechas).getTime());
        } catch (ParseException e) {
            return d;
        }
        return d;
    }
    
    public static Date fechaIncrementoPeriodoTiempo(Date fechaInicial, int incremento, int tipoPeriodo) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fechaInicial);
        switch (tipoPeriodo) {
            case 1 -> calendario.add(Calendar.YEAR, incremento);
            case 5 -> calendario.add(Calendar.DATE, incremento);
            case 2 -> calendario.add(Calendar.MONTH, incremento);
            case 10 -> calendario.add(Calendar.HOUR, incremento);
            case 12 -> calendario.add(Calendar.MINUTE, incremento);
            case 13 -> calendario.add(Calendar.SECOND, incremento);
        }
        return new Date(calendario.getTime().getTime());
    }
   
    /**
	 * Transforma una fecha a cadena
	 *
	 * @param fecha
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */


	public static String dateToString(Date fecha, String format) {
		if (null == fecha) {
			return "";
		} else {
			return (new SimpleDateFormat(format)).format(fecha);
		}
	}

    /**
     * Transforma una cadena de texto en una fecha siempre y cuando cumpla con el formato dd/MM/yyyy HH:mm:ss o dd/MM/yyyy
     *
     * @param time Description of Parameter
     * @return Description of the Returned Value
     */
    public static Timestamp stringToDateTime(String time) {
        if (time.length() <= 10) time = time + " 00:00:00";
        Timestamp d = null;
        try {
            d = new Timestamp((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(time).getTime());
        } catch (ParseException e) {
            return d;
        }
        return d;
    }
    
    public static int getMes(Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        int mes = cal.get(Calendar.MONTH);
        return mes+1;
    }
    
    /**
     * Retorna el número de períodos de vigencia transcurrida entre las fechas que se pasan como parámetros
     *
     * @param fechaA Fecha final
     * @param fechaB Fecha inicial
     * @return Número de periodos de vigencia, retorna 0 si la fecha final es anterior a la fecha inicial
     */
    public static long mesesTranscurridos(Timestamp fechaA, Timestamp fechaB) {
        long meses = 1;
        if (fechaA.compareTo(fechaB) < 0) {
            meses = 0;
        } else {
            Calendar calendario = Calendar.getInstance();
            calendario.setTime(fechaB);
            calendario.add(Calendar.MONTH, (int)(meses));
            Timestamp fechaSql = new Timestamp(calendario.getTime().getTime());
            while (fechaSql.compareTo(fechaA) < 0) {
                meses++;
                calendario.setTime(fechaB);
                calendario.add(Calendar.MONTH, (int)(meses));
                fechaSql = new Timestamp(calendario.getTime().getTime());
            }
        }
        return meses;
    }

    public static String dateImprimeToString(Timestamp fecha, String formato) {
        // Formato puede ser "MMMMMM dd 'de' yyyy"
        if (null == fecha) {
            return "";
        } else {
            return (new SimpleDateFormat(formato)).format(fecha);
        }
    }

    public static Timestamp fechaMasPeriodoTiempo(Timestamp fechaInicial, int incremento, int tipoPeriodo) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fechaInicial);
        switch (tipoPeriodo) {
            case 1 -> calendario.add(Calendar.DATE, incremento);
            case 2 -> calendario.add(Calendar.MONTH, incremento);
            case 3 -> calendario.add(Calendar.YEAR, incremento);
            case 4 -> calendario.add(Calendar.HOUR, incremento);
            case 5 -> calendario.add(Calendar.MINUTE, incremento);
            case 6 -> calendario.add(Calendar.SECOND, incremento);
        }
        return new Timestamp(calendario.getTime().getTime());
    }

    public static String LastDayMonth(Date fecha) {
		Calendar cal = Calendar.getInstance();
		if (null == fecha) {
			return "";
		} else {
			cal.setTime(fecha);
			cal.add(Calendar.MONTH, -1);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
			fecha =new Date(cal.getTime().getTime());
			return (new SimpleDateFormat("dd/MM/yyyy")).format(fecha);
		}
	}

	public static String dateToString(Date laFechaDeCorte) {
		if (null == laFechaDeCorte) {
			return "";
		} else {
			return (new SimpleDateFormat("dd/MM/yyyy")).format(laFechaDeCorte);
		}
	}

	public static String dateTimeToString(Date laFechaDeCorte) {
		if (null == laFechaDeCorte) {
			return "";
		} else {
			return (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).format(laFechaDeCorte);
		}
	}
	
	public static String replace(String sourceString, String matchString, String replaceString) {
		String result = sourceString;
		if (matchString != null && sourceString != null) {
			StringBuilder modifiedString = new StringBuilder();
			int pos = -1;
			do {
				pos = sourceString.indexOf(matchString);
				if (pos >= 0) {
					modifiedString.append(sourceString, 0, pos).append((replaceString != null) ? replaceString : "");
					if (sourceString.length() > pos + matchString.length()) {
						sourceString = sourceString.substring(pos + matchString.length());
					} else {
						sourceString = "";
					}
				}
			} while (pos >= 0);
			modifiedString.append(sourceString);
			result = modifiedString.toString();
		}
		return result;
	}

	public static long periodosTranscurridos(Date fechaA, Date fechaB) {
		long periodos = 1;
		if (fechaA.compareTo(fechaB) <= 0) {
			periodos = 0;
		} else {
			Date fechaUnAnio =new Date(yearAheadVigenciaDateTime(fechaB).getTime()) ;
			while (fechaUnAnio.compareTo(fechaA) < 0) {
				periodos++;
				fechaUnAnio = new Date(yearAheadVigenciaDateTime(fechaUnAnio).getTime()) ;
			}
		}
		return periodos;
	}


    public static Long edadVida(Date fechaA, Date fechaB) {
	        long numeroAnios;
        LocalDate localDateA = fechaA.toLocalDate();
        LocalDate localDateB = fechaB.toLocalDate();
            int anios = (localDateA.getYear() - localDateB.getYear());
	        int mes = (localDateA.getMonthValue() - localDateB.getMonthValue());
	        if (mes >= 6) {
	            numeroAnios =  (anios + 1);
	        } else if (mes < -6)
	            numeroAnios =  (anios - 1);
	        else
	            numeroAnios =  anios;
	        return numeroAnios;
	    }

	    public static String dateTimeToString(Timestamp fecha) {
	        if (null == fecha) {
	            return "";
	        } else {

	            return (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).format(fecha);
	        }
	    }

	public static Date dateFromString(String fecha, String formato) {
		try {
			if (null == fecha) {
				return null;
			} else {
				if (null == formato) formato = "dd/MM/yyyy HH:mm:ss";
                String datePattern = "dd/MM/yyyy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);

				return  new Date(dateFormat.parse(fecha).getTime());

			}
		} catch (ParseException e) {
			
		}
		return null;
	}

	    /**
	     * Cambia un dateTime a String
	     *
	     * @param fecha Description of Parameter
	     * @return Description of the Returned Value
	     */
	    public static String dateToString(Timestamp fecha) {
	        if (null == fecha) {
	            return "";
	        } else {

	            return (new SimpleDateFormat("dd/MM/yyyy")).format(fecha);
	        }
	    }

	    


}
