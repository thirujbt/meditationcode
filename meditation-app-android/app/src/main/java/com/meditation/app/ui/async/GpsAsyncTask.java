package com.meditation.app.ui.async;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.meditation.app.ui.listener.OnWebServiceResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.util.List;


public class GpsAsyncTask extends AsyncTask<List<NameValuePair>, Void, String> {

	Context ctn;
	String xml,url;
    String RespInt;

    OnWebServiceResponse mResponseReceived;
	Activity activity;
	public GpsAsyncTask(Context context, String url, OnWebServiceResponse respAsyn){
		activity = (Activity) context;
		this.ctn = context;
		this.url = url;
		this.mResponseReceived = respAsyn;
		//this.RespInt = respInt;
	}
	
	protected void onPreExecute() {

	}

	@Override
	protected String doInBackground(List<NameValuePair>... params) {

		System.gc();
		try{
		HttpClient httpClient = new DefaultHttpClient();
		HttpParams httpParams = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
		HttpConnectionParams.setSoTimeout(httpParams, 30000);
		ConnManagerParams.setTimeout(httpParams, 30000);
		HttpPost httpPost = new HttpPost(url);
		UrlEncodedFormEntity urlEncode = new UrlEncodedFormEntity(params[0]);
		httpPost.setEntity(urlEncode);

		HttpResponse httpResponse = httpClient.execute(httpPost);


        HttpEntity htpEntity = httpResponse.getEntity();
		xml = EntityUtils.toString(htpEntity);

            System.out.println("Checking response.................."+xml);

		}
		catch(Exception e){
			System.gc();
		}
		return xml;
	}
	

	protected void onPostExecute(String result) {


		try{	

		if (android.os.Build.VERSION.SDK_INT >= 17) {
			if(activity!=null && !activity.isDestroyed())
                mResponseReceived.onResponseReceived(result , RespInt);
		}else{
			if(activity!=null)
                mResponseReceived.onResponseReceived(result , RespInt);
		}

		}
			catch(Exception ex){
				ex.printStackTrace();
			}

    }
}
