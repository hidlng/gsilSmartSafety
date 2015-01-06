package com.smartsafety.smartsafetyalarm.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import com.smartsafety.smartsafetyalarm.model.Work;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


public class HttpClient {

	public static HttpClient _instance = null;
	
	public synchronized static HttpClient getInstance()
	{
		if ( _instance != null ) return _instance;
		return _instance = new HttpClient();
	}
	
	public Work login( final Context context, String userid, String userpw, String regId ) {
		Work work = null;
		
		String result = getHttpData( HttpUrl.getUrl( context, HttpUrl.SMARTSAFETY_LOGIN) + "?userid=" + userid + "&password=" + userpw + "&regId=" + regId );
		Log.i("SMARTSAFETY", result);
		if( result != null && !result.equals("") ) {
			
			try {
				JSONObject jsonObj = new JSONObject(result);
				
				CustomJsonObject item = new CustomJsonObject(jsonObj);
				if( item.getString("result").equals("true") ) {
					work = new Work();
					work.setUseridx(item.getString("useridx"));
					work.setSiteidx(item.getString("siteidx"));
				} else {
					work = null;
				}
				
			} catch(Exception e) {
				//e.printStackTrace();
				return null;
			}
			
		}
		
		return work;
	}
	
	public ArrayList<Work> worklist( final Context context, String searchdate, String siteidx ) throws UnsupportedEncodingException {
		ArrayList<Work> worklist = null;
		
		String result = getHttpData( HttpUrl.getUrl( context, HttpUrl.SMARTSAFETY_WORK_LIST) + "?searchdate=" + searchdate + "&siteidx=" + URLEncoder.encode(siteidx, "UTF-8") );
		Log.i("SMARTSAFETY_WORKLIST", result);
		if( result != null && !result.equals("") ) {
			
			try {
				JSONObject jsonObj = new JSONObject(result);
				
				CustomJsonObject item = new CustomJsonObject(jsonObj);
				worklist = new ArrayList<Work>();
				if( item.getString("result").equals("true") ) {
					JSONArray jArr = new JSONArray(item.getString("item"));
					
					for ( int i = 0; i < jArr.length(); i++ ){
						CustomJsonObject items = new CustomJsonObject(jArr.getJSONObject(i));
						Work work = new Work();
						work.setPicName(items.getString("picName"));
						work.setWorktitle(items.getString("worktitle"));
						work.setWorkidx(items.getString("workidx"));
						work.setCheckYn(items.getString("checkyn"));
						worklist.add(work);
					}
				} else {
					worklist = null;
				}
				
			} catch(Exception e) {
				
				e.printStackTrace();
				return null;
			}
			
		}
		
		return worklist;
	}

	
	public String  eventUpdate( final Context context, String workdate, String useridx, String checkyn, String workidx ) throws UnsupportedEncodingException {
		String resultString = "";
		
		String result = getHttpData( HttpUrl.getUrl( context, HttpUrl.SMARTSAFETY_UPDATE) + "?useridx=" + URLEncoder.encode(useridx, "UTF-8") + "&workdate=" + workdate + "&checkyn=" + checkyn + "&workidx=" + workidx );
		Log.i("SMARTSAFETY", result);
		if( result != null && !result.equals("") ) {
			
			try {
				JSONObject jsonObj = new JSONObject(result);
				
				CustomJsonObject item = new CustomJsonObject(jsonObj);
				if( item.getString("result").equals("true") ) {
					resultString  = "true";
				} else {
					resultString  = "";
				}
				
			} catch(Exception e) {
				//e.printStackTrace();
				return null;
			}
			
		}
		
		return resultString;
	}
	
	
	/**********************************************************************************************************/
	// Basic Setting
	/**********************************************************************************************************/
	public String patchDelete(String urlStr, ArrayList<NameValuePair> paramss) {
		try {
			URL url = new URL(urlStr);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("Content-Type",
		                "application/x-www-form-urlencoded");
			con.setRequestMethod("DELETE");

	        int statusCode = con.getResponseCode();
	        if (statusCode == 200) {
	        }
			
			return "";
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		
	}
	
	
	public String patchUpdate(String urlStr, ArrayList<NameValuePair> paramss) {
		
		BufferedReader reader = null;
		
		try {
			URL url = new URL(urlStr);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("PUT");
			con.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
			writer.write(getQuery(paramss));
			writer.flush();
			
			StringBuilder sb = new StringBuilder();
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			
			String line;
	        int statusCode = con.getResponseCode();
	        if (statusCode == 200) {
	        	while ((line = reader.readLine()) != null) {
	        		sb.append(line);
	        	}
	        }
			
			return sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		
	}
	
	@SuppressWarnings("unused")
	public String postHttpParameterData(String URL, ArrayList<NameValuePair> paramss ) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String result = "";
        HttpResponse response = null;
        
        try {
            HttpPost httppost = new HttpPost(URL);
            
			HttpParams params = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 100000);
			HttpConnectionParams.setSoTimeout(params, 100000);
            
            // Add parameters
            httppost.setEntity(new UrlEncodedFormEntity(paramss,"UTF-8"));
            response = httpclient.execute(httppost);

            if (response == null) {
                return result;
            } else {
                try {
                	int sc = response.getStatusLine().getStatusCode();
                    if( sc == 200 ) {
                    	result = inputStreamToString(response.getEntity().getContent());
                     } else {
                    	
                    	result = inputStreamToString(response.getEntity().getContent());
         				JSONObject jsonObj = new JSONObject(result);
        				
         				CustomJsonObject item = new CustomJsonObject(jsonObj);
                    	
         				if( item != null ) {
         					result = item.getString("message");
         				} else {
         					result = "Fail";
         				}
         				
         				
                     }
                    
                    
                } catch (IllegalStateException e) {
                    Log.e("KAODEE", e.getLocalizedMessage(), e);
 
                } catch (IOException e) {
                    Log.e("KAODEE", e.getLocalizedMessage(), e);
                }
            }
        } catch (Exception e) {
            Log.e("KAODEE", e.getLocalizedMessage(), e);
        }
		
		return result;
	}
	
	public String postHttpData(String URL) {
		DefaultHttpClient client = new DefaultHttpClient();
		try {
			HttpPost post = new HttpPost(URL);

			HttpParams params = client.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 100000);
			HttpConnectionParams.setSoTimeout(params, 100000);

			HttpResponse response = client.execute(post);
			BufferedReader bufreader = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent(),
							"UTF-8"));
			
			String line = null;
			String result = "";

			while ((line = bufreader.readLine()) != null) {
				result += line;
			}
			
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			client.getConnectionManager().shutdown();	
			return ""; 
		}
		
	}
	
	
	public String getHttpData( String URL) {
		DefaultHttpClient client = new DefaultHttpClient();
		try {
			HttpGet post = new HttpGet(URL);

			HttpParams params = client.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 100000);
			HttpConnectionParams.setSoTimeout(params, 100000);

			HttpResponse response = client.execute(post);
			BufferedReader bufreader = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent(),
							"utf-8"));
			String result = "";
			
			int sc = response.getStatusLine().getStatusCode();
            if( sc == 200 ) {
    			String line = null;
    			while ((line = bufreader.readLine()) != null) {
    				result += line;
    			}
             } else {
            	
     			String line = null;
     			while ((line = bufreader.readLine()) != null) {
     				result += line;
     			}
				
             	result = "";
             }
			

			
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			client.getConnectionManager().shutdown();	
			return ""; 
		}
		
	}
	
    private String inputStreamToString(InputStream is) {
    	 
        String line = "";
        StringBuilder total = new StringBuilder();

        // Wrap a BufferedReader around the InputStream
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        try {
            // Read response until the end
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
            Log.e("KAODEE", e.getLocalizedMessage(), e);
        }

        // Return full string
        return total.toString();
    }

    
    public String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    
    /******************************************************************************************************
     * upload file
     * */
    
    public String uploadFile(byte[] data, String fileName, String market_id) {
    	 String upLoadServerUri = "http://kaudee.com:3000/api/v1/image";  
    	 
    	 
         DefaultHttpClient httpclient = new DefaultHttpClient();
         String result = "";
         HttpResponse response = null;
         
         try {
            HttpPost httppost = new HttpPost(upLoadServerUri);
             
            ByteArrayBody bab = new ByteArrayBody(data, fileName);
            
         	MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
         	reqEntity.addPart("img", bab);
         	reqEntity.addPart("market_id", new StringBody(market_id));
         	httppost.setEntity(reqEntity);
            
         	
         	
         	response = httpclient.execute(httppost);

             if (response == null) {
                 return result;
             } else {
                 try {
                 	int sc = response.getStatusLine().getStatusCode();
                     if( sc == 200 ) {
                     	result = inputStreamToString(response.getEntity().getContent());
                     } else {
                     	result = "400";
                     }
                     
                 } catch (IllegalStateException e) {
                     Log.e("KAODEE", e.getLocalizedMessage(), e);
  
//                 } catch (IOException e) {
//                     Log.e("KAODEE", e.getLocalizedMessage(), e);
                 }
             }
         } catch (Exception e) {
             Log.e("KAODEE", e.getLocalizedMessage(), e);
         }
 		
 		return result;
         
    }
 
    
    public Bitmap getImage(String urls) {
    	
		DefaultHttpClient client = new DefaultHttpClient();
		try {
			HttpGet post = new HttpGet(urls);

			HttpParams params = client.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 1000000);
			HttpConnectionParams.setSoTimeout(params, 1000000);

			HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
            InputStream input = b_entity.getContent();

            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inSampleSize = 8;
            options.inPurgeable = true;
            
            Bitmap bitmap = BitmapFactory.decodeStream(input);

           return bitmap;

		} catch (Exception e) {
			e.printStackTrace();
			client.getConnectionManager().shutdown();	
			return null; 
		}

    }
    


}
