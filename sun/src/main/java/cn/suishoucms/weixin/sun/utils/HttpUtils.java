package cn.suishoucms.weixin.sun.utils;



import java.io.IOException;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

public class HttpUtils {
	private static final RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(150000).setConnectTimeout(150000).setSocketTimeout(150000).build();

	private static CloseableHttpClient httpclient;

	static {
		try {
			SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(new TrustStrategy() {
				@Override
				public boolean isTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();// 信用所有证书

			httpclient = HttpClients.custom().setMaxConnTotal(200).setMaxConnPerRoute(200).setSSLContext(sslContext).setDefaultRequestConfig(requestConfig).build();
		} catch (Exception e) {
			httpclient = HttpClients.custom().setMaxConnTotal(200).setMaxConnPerRoute(200).setDefaultRequestConfig(requestConfig).build();;
			e.printStackTrace();
		}

	}
	private static final ResponseHandler<StringResponse> responseHandler = new ResponseHandler<StringResponse>() {
		@Override
		public StringResponse handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
			int status = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			StringResponse stringResponse = new StringResponse();
			stringResponse.setStatusCode(status);
			ContentType ct = ContentType.get(entity);
			Charset charset = ct != null ? ct.getCharset() : null;
			if (charset == null) {
				charset = Charset.defaultCharset();
			}
			stringResponse.setCharset(charset);
			stringResponse.setResponseBody(entity != null ? EntityUtils.toString(entity, charset) : null);
			return stringResponse;

		}

	};

	/**
	 * 处理resepone返回字节类型的结果
	 */
	private static final ResponseHandler<ByteArrayResponse> binaryResponseHandler = new ResponseHandler<ByteArrayResponse>() {
		@Override
		public ByteArrayResponse handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
			int status = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			ByteArrayResponse myResponse = new ByteArrayResponse();
			myResponse.setFileName(getFileName(response));
			myResponse.setStatusCode(status);
			ContentType contentType = ContentType.get(entity);
			String mimeType = null;
			if (contentType != null) {
				mimeType = ContentType.get(entity).getMimeType();
			}

			myResponse.setMimeType(mimeType);
			byte[] bytes = entity != null ? EntityUtils.toByteArray(entity) : null;
			myResponse.setResponseBody(bytes);

			return myResponse;

		}

	};

	public static String getFileName(HttpResponse response) {
		Header contentHeader = response.getFirstHeader("Content-Disposition");
		String filename = null;
		if (contentHeader != null) {
			HeaderElement[] values = contentHeader.getElements();
			if (values.length == 1) {
				NameValuePair param = values[0].getParameterByName("filename");
				if (param != null) {
					filename = param.getValue();
				}
			}
		}
		return filename;
	}

	public static ByteArrayResponse getBinary(String url) throws ClientProtocolException, IOException {
		HttpGet get = new HttpGet(url);
		ByteArrayResponse myResponse = httpclient.execute(get, binaryResponseHandler);
		return myResponse;
	}

	public static StringResponse get(String url) throws Exception {

		HttpGet httpget = new HttpGet(url);
		return httpclient.execute(httpget, responseHandler);

	}

	public static StringResponse get(String url, RequestConfig requestConfig1) throws Exception {

		HttpGet httpget = new HttpGet(url);
		httpget.setConfig(requestConfig1);
		return httpclient.execute(httpget, responseHandler);

	}

	public static StringResponse post(String url) throws Exception {
		HttpPost httppost = new HttpPost(url);
		return httpclient.execute(httppost, responseHandler);
	}

	public static StringResponse post(String url, Map<String, String> params) throws Exception {

		HttpPost httppost = new HttpPost(url);
		List<NameValuePair> nps = map2NameValuePairs(params);
		UrlEncodedFormEntity en = new UrlEncodedFormEntity(nps, Consts.UTF_8);
		httppost.setEntity(en);
		en.setContentEncoding("UTF-8");
		return httpclient.execute(httppost, responseHandler);

	}

	public static StringResponse post(String url, Map<String, String> params, RequestConfig requestConfig1) throws Exception {

		HttpPost httppost = new HttpPost(url);
		httppost.setConfig(requestConfig1);
		List<NameValuePair> nps = map2NameValuePairs(params);
		UrlEncodedFormEntity en = new UrlEncodedFormEntity(nps, Consts.UTF_8);
		httppost.setEntity(en);
		en.setContentEncoding("UTF-8");
		return httpclient.execute(httppost, responseHandler);

	}

	public static StringResponse postJson(String url, String body) throws Exception {

		HttpPost method = new HttpPost(url);
		StringEntity stringEntity = new StringEntity(body, Consts.UTF_8);
		stringEntity.setContentEncoding("UTF-8");
		stringEntity.setContentType("application/json");
		method.setEntity(stringEntity);
		return httpclient.execute(method, responseHandler);

	}

	public static StringResponse postJson(String url, String body, RequestConfig requestConfig1) throws Exception {

		HttpPost method = new HttpPost(url);
		method.setConfig(requestConfig1);
		StringEntity stringEntity = new StringEntity(body, Consts.UTF_8);
		stringEntity.setContentEncoding("UTF-8");
		stringEntity.setContentType("application/json");
		method.setEntity(stringEntity);
		return httpclient.execute(method, responseHandler);

	}

	public static StringResponse doPost(String url, Map<String, String> header, Map<String, String> params, RequestConfig requestConfig1) throws Exception {

		HttpPost httppost = new HttpPost(url);
		httppost.setConfig(requestConfig1);
		List<NameValuePair> nps = map2NameValuePairs(params);
		UrlEncodedFormEntity en = new UrlEncodedFormEntity(nps, Consts.UTF_8);
		httppost.setEntity(en);
		for (Entry<String, String> entry : header.entrySet()) {
			httppost.addHeader(entry.getKey(), entry.getValue().toString());
		}
		en.setContentEncoding("UTF-8");
		return httpclient.execute(httppost, responseHandler);

	}

	public static StringResponse doPost(String url, Map<String, Object> header, String body) throws Exception {

		HttpPost method = new HttpPost(url);
		StringEntity stringEntity = new StringEntity(body, Consts.UTF_8);
		stringEntity.setContentEncoding("UTF-8");
		stringEntity.setContentType("application/json");
		method.setEntity(stringEntity);
		for (Entry<String, Object> entry : header.entrySet()) {
			method.addHeader(entry.getKey(), entry.getValue().toString());
		}
		return httpclient.execute(method, responseHandler);

	}

	public static List<NameValuePair> map2NameValuePairs(final Map<String, String> params) {
		List<NameValuePair> nameValuePairs = new ArrayList<>();
		if (params == null) {
			return nameValuePairs;
		}
		for (Entry<String, String> entry : params.entrySet()) {
			nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		return nameValuePairs;
	}

	

}
