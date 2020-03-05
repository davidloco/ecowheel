package com.ecowheel.system.util;

import java.util.ResourceBundle;


/**
 * @author David Andres Betancourth Botero
 * 
 */
public class Contains {
	
	public static ResourceBundle bundle = ResourceBundle.getBundle("com.ecowheel.system.util.bundle");

	public static final String RUTA_CARPETA_TEMPORAL_SRV = System.getProperty("java.io.tmpdir");
	public static final String SEPARADOR_CARPETAS_SRV = System.getProperty("file.separator");

	public static final String PATH_DOCUMENTS = "/documentos/";
	public static final String VACIO = " ";

	public static final int FOTO = 1;
	public static final int DOCUMENTO = 2;

	public static final int OPERARIO = 1;
	public static final int ADMINISTRADOR = 2;

	public static final int PDF = 1;
	public static final int XLS = 2;
	public static final int DOC = 3;
	
	public static final String PDFTXT = "pdf";
	public static final String XLSTXT = "xlsx";
	public static final String DOCTXT = "doc";

	public static final String MISION = bundle.getString("mision");
	public static final String VISION = bundle.getString("vision");
	

}
