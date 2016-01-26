package initMall;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TryCodeString {
	public static void main(String[] args) throws UnsupportedEncodingException{
//		String s = "9dd4f19d8ea21bbafe171baa42ee1a7f01b60d35";
//		String s = "domoto02@yahoo.com.tw";
//		try {
//			MessageDigest m = MessageDigest.getInstance("MD5");
//            byte[] md5Bytes = m.digest(s.getBytes());  
//			System.out.println(bytes2Hex(md5Bytes));
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		SimpleDateFormat sDay = new SimpleDateFormat("yyyyMMddhhmm");
//		try {
//			System.out.println(sDay.parse("201405161530"));
//			if(new java.util.Date().getTime() < sDay.parse("201405161530").getTime())
//				System.out.println("true");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		System.out.println(new SimpleDateFormat("yyyyMMddkkmm").format(new java.util.Date()));
		
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		
		Iterator<String> i = list.iterator();
		while(i.hasNext()){
			System.out.println("===="+i.next());
		}
		i.remove();		
		
		for(String s:list)
			System.out.println("-----"+s);
		
	}
	 private static String bytes2Hex(byte[] byteArray)  
	    {  
	        StringBuffer strBuf = new StringBuffer();  
	        for (int i = 0; i < byteArray.length; i++)  
	        {  System.out.println(byteArray[i]);
	            if(byteArray[i] >= 0 && byteArray[i] < 16)  
	            {  
	                strBuf.append("0");  
	            }  
	            strBuf.append(Integer.toHexString(byteArray[i] & 0xFF));  
	        }  
	        return strBuf.toString();  
	    }  

}



