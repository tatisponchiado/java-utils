package info.agilite.utils;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;

public class StringUtils {
	public static int count(String texto, String character) {
		return texto.length() - texto.replace(character, "").length();
	}
	
	public static String capitalize(String value) {
		return capitalize(value, false);
	}

	public static String capitalize(String value, boolean lowerRest) {
		if(value.length() == 1)return value.toUpperCase();

		return value.substring(0, 1).toUpperCase()+(lowerRest ? value.substring(1).toLowerCase() : value.substring(1));
	}

	public static String decapitalize(String value) {
		if(value.length() == 1)return value.toLowerCase();

		return value.substring(0, 1).toLowerCase()+(value.substring(1));
	}

	public static String extractNumbers(String texto){
		if(texto == null || texto.length() == 0)return null;

		return texto.chars().filter(caracter -> Character.isDigit(caracter)).mapToObj(value -> String.valueOf((char)value)).collect(Collectors.joining());
	}

	public static String extractLetters(String texto){
		if(texto == null || texto.length() == 0)return null;

		return texto.chars().filter(value -> Character.isAlphabetic(value)).mapToObj(value -> String.valueOf((char)value)).collect(Collectors.joining());
	}

	public static String concat(Object ... values) {
		return Arrays.stream(values).map(v -> v == null ? "" : v.toString()).collect(Collectors.joining());
	}
	public static Stream<String> stream(Object ... values) {
		return Arrays.stream(values).map(v -> v == null ? "" : v.toString());
	}
	
	public static boolean isNullOrEmpty(String val) {
		return val == null || val.trim().length() == 0;
	}

	public static boolean isEquals(String string, String value) {
		if(isNullOrEmpty(string))return false;
		return string.equalsIgnoreCase(value);
	}
	
	@SafeVarargs
	public static String firstNotEmpty(String ... values) {
		for(String val : values) {
			if(!isNullOrEmpty(val))return val;
		}
		return "";
	}
	
	public static String firstNotNull(String ... values) {
		for(String val : values) {
			if(val != null)return val;
		}
		return "";
	}
	
	public static String getStackTrace(Throwable t) {
		try {
			try(StringWriter sr = new StringWriter(); PrintWriter wr = new PrintWriter(sr)){
				t.printStackTrace(wr);
				return sr.getBuffer().toString(); 
			}
		} catch (Exception e) {
			return Arrays.stream(t.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.joining("\n"));
		}
	}

	public static String extractBetweenText(String templateStart, String templateEnd, String message) {
		int templateStartPosition = message.toUpperCase().indexOf( templateStart.toUpperCase() );
		if ( templateStartPosition < 0 ) {
			templateStartPosition = 0;
		}

		int start = templateStartPosition + templateStart.length();
		int end = message.toUpperCase().indexOf( templateEnd.toUpperCase(), start );
		if ( end < 0 ) {
			end = message.length();
		}

		return message.substring( start, end );
	}

	public static boolean equalsIgnoreCase(String a, String b) {
		if(a == null)return false;
		return a.equalsIgnoreCase(b);
	}

	public static String substringAfterLast(String texto, String character) {
		int index = texto.toUpperCase().lastIndexOf(character.toUpperCase());
		if(index == -1)return texto;

		index += character.length();
		return texto.substring(index);
	}
	public static String start(String texto, int length) {
		if(length >= texto.length())return texto;
		
		return texto.substring(0, length);
	}
	public static String substringAfterFirst(String texto, String str) {
		int index = texto.toUpperCase().indexOf(str.toUpperCase());
		if(index == -1)return texto;

		index += str.length();
		return texto.substring(index);
	}
	public static String substringBetween(String texto, String strStart, String strEnd) {
		texto = substringAfterFirst(texto, strStart);
		return substringBeforeLast(texto, strEnd);
	}
	public static String substringBeforeFirst(String texto, String character) {
		int index = texto.toUpperCase().indexOf(character.toUpperCase());
		if(index == -1)index = texto.length();
		return texto.substring(0, index);
	}
	public static String substringBeforeLast(String texto, String character) {
		int index = texto.toUpperCase().lastIndexOf(character.toUpperCase());
		if(index == -1)index = texto.length();
		return texto.substring(0, index);
	}

	public static String toNumberMask(String mask, String value) {
		if(value == null)return null;

		StringBuilder builder = new StringBuilder();
		for(int i = 0;i < mask.length()-value.length(); i++, builder.append("0"));
		builder.append(value);
		StringBuilder retorno = new StringBuilder();
		int iValue = builder.length()-1;
		for(int i = mask.length()-1;i >= 0; i--) {
			if(mask.charAt(i) == '#') {
				retorno.insert(0, builder.charAt(iValue));
				iValue--;
			}else {
				retorno.insert(0, mask.charAt(i));
			}
		}
		return retorno.toString();
	}
	/**
	 * Formata uma String a mascara deve ser criada por #, irá adicionar 0 onde não tiver digitos 
	 */
	public static String format(String mask, String value) {
		if(value == null)return null;
		
		StringBuilder builder = new StringBuilder();
		for(int i = 0;i < mask.length()-value.length(); i++, builder.append("0"));
		builder.append(value);
		StringBuilder retorno = new StringBuilder();
		int iValue = builder.length()-1;
		for(int i = mask.length()-1;i >= 0; i--) {
			if(mask.charAt(i) == '#') {
				retorno.insert(0, builder.charAt(iValue));
				iValue--;
			}else {
				retorno.insert(0, mask.charAt(i));
			}
		}
		return retorno.toString();
    }
	public static String formatByClass(Object value) {
		return formatByClass(value, false);
	}
	public static String formatByClass(Object value, boolean emptyOnNull) {
		if(value == null)return emptyOnNull ? "" : null;

		if(value instanceof LocalTime) {
			return ((LocalTime)value).format(DateTimeFormatter.ofPattern("HH:mm"));

		}else if(value instanceof LocalDateTime) {
			return ((LocalDateTime)value).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

		}else if(value instanceof LocalDate) {
			return ((LocalDate)value).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		}else if(value instanceof BigDecimal) {
			NumberFormat numberInstance = DecimalFormat.getNumberInstance(new Locale("pt", "BR"));
			numberInstance.setMinimumFractionDigits(2);
			numberInstance.setMaximumFractionDigits(2);
			numberInstance.setMinimumIntegerDigits(1);
			numberInstance.setGroupingUsed(true);
			return numberInstance.format((BigDecimal)value);

		}else if(value instanceof Integer || value instanceof Long) {
			NumberFormat fmt = NumberFormat.getIntegerInstance();
			fmt.setGroupingUsed(true);
			return fmt.format(value);

		}else if(value instanceof Boolean) {
			return ((Boolean)value) ? "Sim" : "Não";

		}

		return value.toString();
	}

	public static String crop(String value, int maxLength) {
		return crop(value, maxLength, null);
	}

	public static String crop(String value, int maxLength, String textOverflow){
		if(value == null)return null;
		if(textOverflow == null)textOverflow = "";

		if(value.length() <= maxLength)return value;

		return concat(value.substring(0, maxLength-textOverflow.length()), textOverflow);
	}

	public static String wrap(String text, int lineSize) {
		return wrap(text, lineSize, "\n");
	}
	
	public static String wrap(String text, int lineSize, String lineWrap) {
		if(Strings.isNullOrEmpty(text))return "";

		Iterable<String> words = Splitter.on(' ').trimResults().split(text);
		StringBuilder result = new StringBuilder();
		
		int qtd = 0;
		for(String word : words) {
			result.append(word);

			qtd += word.length()+1;
			if(qtd >= lineSize) {
				result.append("\n");
				qtd=0;
			}else {
				result.append(" ");
			}
		}
		
		return result.toString();
	}
	
	public static String ajustString(Object value, int tamanho){
		if(value instanceof Number){
			return ajustString(value, tamanho, '0', true);
		}else{
			return ajustString(value, tamanho, ' ', false);	
		}
	}

	public static String ajustString(Object value, int length, char character, boolean leftConcat){
		StringBuffer completar = new StringBuffer("");
		for(int i = 0; i < length; i++, completar.append(character));

		if(value == null)return completar.toString();
		String texto = value.toString();

		if(texto.length() < length){
			if(leftConcat){
				texto = completar.toString() + texto;
				return texto.substring((texto.length()-length), texto.length());
			}else{
				texto+= completar.toString();
				return texto.substring(0, length);
			}

		}else if(texto.length() > length){
			if(leftConcat){
				return texto.substring((texto.length()-length), texto.length());
			}else{
				return texto.substring(0, length);
			}
		}else{
			return texto;
		}
	}

	public static String string2ValidJavaName(String name) {
		StringBuilder normalized = new StringBuilder();
		for(int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			boolean valid = false;
			if(i == 0) {
				valid = Character.isJavaIdentifierStart(c);
			}else {
				valid = Character.isJavaIdentifierPart(c); 
			}
			if(valid)normalized.append(c);
		}
		
		return normalized.toString();
	}
	
	/**
	 * 
	 * @param pattern Mensagem a ser exibida com {} sendo utilizado para o template dos parametros
	 * @param params Parâmetros que irão substituir os {}  da mensagem em seu respectivo indice
	 * @return
	 */
	public static String message(String pattern, Object ... params) {
		return StringMessage.create(pattern).params(params).get();
	}


	/**
	 * Informe a String base com os parametros entre {}, exemplo replaceByParams("Bem vindo, {nome} {sobrenome}", "nome", "José", "sobrenome", "Da Silva")
	 * @param pattern
	 * @param params
	 * @return
	 */
	public static String messageByNames(String pattern, Object ... params) {
		if(params != null && params.length > 0) {
			Map<String, String> map = Utils.mapString(params);
			for(String k : map.keySet()) {
				pattern = pattern.replace("{" + k + "}", map.get(k));
			}
		}

		return pattern;
	}

	public static String unaccented(String text) {
		if(text == null)return "";

		text = text.replaceAll("[áàãâä]", "a");
		text = text.replaceAll("[éèêë]", "e");
		text = text.replaceAll("[íìîï]", "i");
		text = text.replaceAll("[óòõôö]", "o");
		text = text.replaceAll("[úùûü]", "u");
		text = text.replaceAll("[ÁÀÃÂÄ]", "A");
		text = text.replaceAll("[ÉÈÊË]", "E");
		text = text.replaceAll("[ÍÌÎÏ]", "I");
		text = text.replaceAll("[ÓÒÕÔÖ]", "O");
		text = text.replaceAll("[ÚÙÛÜ]", "U");

		return text;
	}
	
	public static String createSelectWithAlias(String classAlias, String fields) {
		return createSelectWithAlias(classAlias, fields, false);
	}
	
	public static String createSelectWithAlias(String classAlias, String fields, boolean classAliasOnField) {
		List<String> fieldList = Splitter.on(",").trimResults().splitToList(fields);
		return fieldList.stream().map(field -> {
			String fieldAlias = classAliasOnField ? concat(classAlias, "_", field) : field;
			return StringUtils.concat(classAlias, ".", field, " as ", fieldAlias);
		}).collect(Collectors.joining(", "));
	}
	
	public static String repeate(String val, int size) {
		if(val == null || val.length() == 0 || size == 0)return "";
		return new String(new char[size]).replace("\0", val);
	}
}
