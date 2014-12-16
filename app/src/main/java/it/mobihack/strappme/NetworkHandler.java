package it.mobihack.strappme;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class NetworkHandler {

	private HttpURLConnection uc;
	private final String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:24.0) Gecko/20100101 Firefox/24.0";

	/**
	 * getResponse permette di instaurare una connessione ad un server Web. E' possibile inviare dati tramite
	 * metodo "POST" tramite una stringa.
	 * @param url L'url del server web. 
	 * @param params Parametri dell'header 
	 * @return
	 */
	public String getResponse(String url, HashMap<String, String> headParams, String postParams) {
		String response = "";
		try {
			URL myUrl = new URL(url);
			uc = (HttpURLConnection)myUrl.openConnection();
			uc.setDoInput(true);
			uc.setDoOutput(true);
			uc.setConnectTimeout(0);
			uc.setReadTimeout(0);
			uc.setRequestProperty("User-Agent", userAgent);
			setHeaderParams(headParams);
			setPostParams(postParams);
			response = getHtml();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	private String getHtml() throws Exception {
		String html = "";
		String temp = "";
		BufferedReader bis = new BufferedReader(new InputStreamReader(uc.getInputStream()));
		while ((temp = bis.readLine()) != null) {
			html += temp;
		}
		bis.close();	
		return html;
	}

	private void setPostParams(String postParams) throws Exception {
		// Nel caso in cui voglia inviare dei dati tramite POST, procedo a generare un BufferedWriter
		if (postParams != null) {
			uc.setRequestMethod("POST");
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(uc.getOutputStream()));
			bw.write(postParams);
			bw.flush();
			bw.close();
		}		
	}

	private void setHeaderParams(HashMap<String, String> headParams) {
		if (headParams != null) {
			// Imposto l'header con i parametri contenuti nell'HashMap
			Iterator<Entry<String, String>> iter = headParams.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, String> entry = iter.next();
				uc.setRequestProperty(entry.getKey(), entry.getValue());
			}
		}
	}
}
