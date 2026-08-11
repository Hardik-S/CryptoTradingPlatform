package cryptoTrader.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DataFetcher {
	private static final int REQUEST_TIMEOUT_MILLIS = 10_000;

	private JsonObject getDataForCrypto(String id, String date) {

		String urlString = String.format(
				"https://api.coingecko.com/api/v3/coins/%s/history?date=%s", id, date);
		HttpURLConnection conn = null;
		try {
			URL url = new URL(urlString);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(REQUEST_TIMEOUT_MILLIS);
			conn.setReadTimeout(REQUEST_TIMEOUT_MILLIS);
			conn.connect();
			int responsecode = conn.getResponseCode();
			if (responsecode == HttpURLConnection.HTTP_OK) {
				StringBuilder inline = new StringBuilder();
				try (Scanner sc = new Scanner(conn.getInputStream(), "UTF-8")) {
					while (sc.hasNext()) {
						inline.append(sc.nextLine());
					}
				}
				JsonObject jsonObject = new JsonParser().parse(inline.toString()).getAsJsonObject();
				return jsonObject;
			}

		} catch (IOException e) {
			System.out.println("Something went wrong with the API call.");
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return null;
	}
	
	// pass 2 strings and get double 
	public double getPriceForCoin(String id, String date) {
		double price = 0.0;
		
		JsonObject jsonObject = getDataForCrypto(id, date);
		if (jsonObject != null) {
			JsonObject marketData = jsonObject.get("market_data").getAsJsonObject();
			JsonObject currentPrice = marketData.get("current_price").getAsJsonObject();
			price = currentPrice.get("cad").getAsDouble();
		}
		
		return price;
	}
	
	public double getMarketCapForCoin(String id, String date) {
		double marketCap = 0.0;
		
		JsonObject jsonObject = getDataForCrypto(id, date);
		if (jsonObject != null) {
			JsonObject marketData = jsonObject.get("market_data").getAsJsonObject();
			JsonObject currentPrice = marketData.get("market_cap").getAsJsonObject();
			marketCap = currentPrice.get("cad").getAsDouble();
		}
		
		return marketCap;
	}
	
	public double getVolumeForCoin(String id, String date) {
		double volume = 0.0;
		
		JsonObject jsonObject = getDataForCrypto(id, date);
		if (jsonObject != null) {
			JsonObject marketData = jsonObject.get("market_data").getAsJsonObject();
			JsonObject currentPrice = marketData.get("total_volume").getAsJsonObject();
			volume = currentPrice.get("cad").getAsDouble();
		}
		
		return volume;
	}
	
}
